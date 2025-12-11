package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;
import waiters.Waiter;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {

    private final Random random = new Random();

    private static final Logger log = LogManager.getLogger(CoursesPage.class);

    @Inject
    public CoursesPage(GuiceScoped guiceScoped, Waiter waiter) {
        super(guiceScoped, waiter);
    }

    @Inject
    private CoursePage coursePage;

    @FindBy(css = "section a[href^='/lessons/'] h6")
    private List<WebElement> courses;

    @FindBy(css = "section a > div:last-of-type div div")
    private List<WebElement> courseDates;

    @FindBy(css = "section a[href^='/online/'] h6")
    private List<WebElement> onlineCourses;

    private List<WebElement> getCourses() {
        waiter.waitForCondition(d -> !courses.isEmpty());
        return courses;
    }

    private List<WebElement> getCourseDates() {
        waiter.waitForCondition(d -> !courseDates.isEmpty());
        return courseDates;
    }

    public List<WebElement> getOnlineCourses() {
        int before = onlineCourses.size();
        Optional<WebElement> showAllButton = driver
                .findElements(By.cssSelector("main section:nth-child(2) div button"))
                .stream()
                .findFirst();
        showAllButton.ifPresent(this::scrollAndClick);
        waiter.waitForCondition(d -> onlineCourses.size() > before);
        return new ArrayList<>(onlineCourses);
    }

    public String getRandomCourseTitle() {
        int index = (int)(Math.random() * courses.size());
        return getCourses().get(index).getText();
    }

    public void clickCourseByTitle(String courseTitle) {
        this.clickElementByPredicate().accept(getCourses(), (WebElement element) -> element.getText().equals(courseTitle));
    }

    public List<LocalDate> getAllCourseDates() {
        Pattern datePattern = Pattern.compile("\\d{1,2} \\p{IsCyrillic}+, \\d{4}");

        return getCourseDates().stream()
                .map(WebElement::getText)
                .map(text -> {
                    Matcher m = datePattern.matcher(text);
                    if (!m.find()) return null;
                    return m.group();
                })
                .filter(Objects::nonNull)
                .map(t -> LocalDate.parse(t, RUS_DATE))
                .collect(Collectors.toList());
    }


    public LocalDate getEarliestDate() {
        return getAllCourseDates().stream()
                .reduce((d1, d2) -> d1.isBefore(d2) ? d1 : d2)
                .orElseThrow();
    }

    public LocalDate getLatestDate() {
        return getAllCourseDates().stream()
                .reduce((d1, d2) -> d1.isAfter(d2) ? d1 : d2)
                .orElseThrow();
    }

    public List<String> getCourseTitlesByDate(LocalDate date) {
        Pattern datePattern = Pattern.compile("\\d{1,2} \\p{IsCyrillic}+, \\d{4}");
        List<String> matchedCourses = new ArrayList<>();

        for (int i = 0; i < getCourseDates().size(); i++) {
            String rawText = getCourseDates().get(i).getText();
            Matcher m = datePattern.matcher(rawText);
            if (!m.find()) {
                continue;
            }

            String dateText = m.group();
            LocalDate parsedDate = LocalDate.parse(dateText, RUS_DATE);

            if (parsedDate.equals(date)) {
                matchedCourses.add(getCourses().get(i).getText());
            }
        }

        if (matchedCourses.isEmpty()) {
            throw new RuntimeException("Êóðñû ñ äàòîé " + date + " íå íàéäåíû");
        }

        return matchedCourses;
    }

    public String clickRandomCourseByDate(LocalDate date) {
        List<String> courses = getCourseTitlesByDate(date);
        String randomCourse = courses.get(random.nextInt(courses.size()));
        clickCourseByTitle(randomCourse);
        return randomCourse;
    }

    public Map<String, LocalDate> getAllCoursesWithDates() {
        Pattern datePattern = Pattern.compile("\\d{1,2} \\p{IsCyrillic}+, \\d{4}");
        Map<String, LocalDate> map = new LinkedHashMap<>();

        List<WebElement> titles = getCourses();
        List<WebElement> dates = getCourseDates();

        for (int i = 0; i < dates.size(); i++) {
            String rawText = dates.get(i).getText();
            Matcher m = datePattern.matcher(rawText);

            if (!m.find()) {
                continue;
            }

            String dateText = m.group();
            LocalDate parsedDate = LocalDate.parse(dateText, RUS_DATE);
            String title = titles.get(i).getText();

            map.put(title, parsedDate);
        }

        return map;
    }

    public void printCoursesFromEarliestDate(LocalDate date) {
        Map<String, LocalDate> courses = getAllCoursesWithDates();

        log.info("=== ÊÓÐÑÛ, ÍÀ×ÈÍÀÞÙÈÅÑß Ñ {} È ÏÎÇÆÅ ===", date);

        courses.entrySet().stream()
                .filter(e -> !e.getValue().isBefore(date))
                .forEach(e -> log.info("Êóðñ: {} | Äàòà ñòàðòà: {}", e.getKey(), e.getValue()));

        log.info("============================================");
    }

    public Map<String, Integer> getCoursesPrice() {
        Map<String, Integer> coursePrices = new HashMap<>();

        List<WebElement> onlineCourses = getOnlineCourses();
        List<String> courseTitles = onlineCourses.stream()
                .map(WebElement::getText)
                .toList();

        for (String title : courseTitles) {
            WebElement courseElement = onlineCourses.stream()
                    .filter(e -> e.getText().equals(title))
                    .findFirst()
                    .orElseGet(() -> getOnlineCourses().stream()
                            .filter(e -> e.getText().equals(title))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Êóðñ íå íàéäåí: " + title)));

            scrollAndClick(courseElement);

            String priceText = waiter.waitForAnyText(
                    By.cssSelector("div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2)"),
                    By.xpath("//a[text()='Çàïèñàòüñÿ íà êóðñ']/parent::div/preceding-sibling::div[2]//div")
            );

            int price = Integer.parseInt(priceText.replaceAll("\\D", ""));
            coursePrices.put(title, price);

            driver.navigate().back();
            onlineCourses = getOnlineCourses();
        }

        return coursePrices;
    }

    public void printCoursesPrice(Map<String, Integer> prices) {
        log.info("=== ÑÀÌÛÅ ÄÅØÅÂÛÅ È ÑÀÌÛÅ ÄÎÐÎÃÈÅ ÊÓÐÑÛ ===");

        int minPrice = prices.values().stream()
                .min(Integer::compareTo)
                .orElseThrow();

        int maxPrice = prices.values().stream()
                .max(Integer::compareTo)
                .orElseThrow();

        prices.entrySet().stream()
                .filter(e -> e.getValue() == minPrice || e.getValue() == maxPrice)
                .forEach(e ->
                        log.info("Êóðñ: {} | Öåíà: {}", e.getKey(), e.getValue())
                );

        log.info("=========================================");
    }

}
