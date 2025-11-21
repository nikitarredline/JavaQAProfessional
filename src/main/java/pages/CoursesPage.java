package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {

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

    public String getCourseName(int index) {
        return getCourses().get(index - 1).getText();
    }

    public CoursePage clickCourseByName(String courseName) {
        this.clickElementByPredicate.accept(getCourses(), (WebElement element) -> element.getText().equals(courseName));
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

    public String getCourseNameByDate(LocalDate date) {
        Pattern datePattern = Pattern.compile("\\d{1,2} \\p{IsCyrillic}+, \\d{4}");

        for (int i = 0; i < getCourseDates().size(); i++) {
            String rawText = getCourseDates().get(i).getText();

            Matcher m = datePattern.matcher(rawText);
            if (!m.find()) {
                continue;
            }

            String dateText = m.group();
            LocalDate parsedDate = LocalDate.parse(dateText, RUS_DATE);

            if (parsedDate.equals(date)) {
                return getCourses().get(i).getText();
            }
        }

        throw new RuntimeException("Курс с датой " + date + " не найден");
    }
}
