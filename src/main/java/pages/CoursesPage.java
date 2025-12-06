package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(CoursesPage.class);

    @Inject
    public CoursesPage(GuiceScoped guiceScoped, Waiter waiter) {
        super(guiceScoped, waiter);
    }

    @Inject
    private CoursePage coursePage;

    @FindBy(css = "section a[href^='/lessons/'] h6 div")
    private List<WebElement> courses;

    @FindBy(css = "section a > div:last-of-type div div")
    private List<WebElement> courseDates;

    private List<WebElement> getCourses() {
        waiter.waitForCondition(d -> !courses.isEmpty());
        return courses;
    }

    private List<WebElement> getCourseDates() {
        waiter.waitForCondition(d -> !courseDates.isEmpty());
        return courseDates;
    }

    public String getRandomCourseTitle() {
        int index = (int)(Math.random() * courses.size());
        return getCourses().get(index).getText();
    }

    public CoursePage clickCourseByTitle(String courseTitle) {
        this.clickElementByPredicate.accept(getCourses(), (WebElement element) -> element.getText().equals(courseTitle));
        return coursePage;
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
            throw new RuntimeException("Курсы с датой " + date + " не найдены");
        }

        return matchedCourses;
    }

    public String clickRandomCourseByDate(LocalDate date) {
        List<String> courses = getCourseTitlesByDate(date);
        String randomCourse = courses.get(new Random().nextInt(courses.size()));
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

        log.info("=== КУРСЫ, НАЧИНАЮЩИЕСЯ С {} И ПОЗЖЕ ===", date);

        courses.entrySet().stream()
                .filter(e -> !e.getValue().isBefore(date))
                .forEach(e -> log.info("Курс: {} | Дата старта: {}", e.getKey(), e.getValue()));

        log.info("============================================");
    }
}
