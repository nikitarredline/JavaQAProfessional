package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    @Inject
    private CoursePage coursePage;

    @FindBy(css = "section a[href^='/lessons/'] h6 div")
    private List<WebElement> courses;

    public String getCourseName(int index) {
        return courses.get(index - 1).getText();
    }

    public CoursePage clickCourseByName(String courseName) {
        this.clickElementByPredicate.accept(courses, (WebElement element) -> element.getText().equals(courseName));
        return coursePage;
    }
}
