package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    @Inject
    private CoursePage coursePage;


//    @FindBy(css = "a.sc-zzdkm7-0")
//    private List<WebElement> courses;

//    public String getCourseTitle(String title) {
//        return courses.get(title).getText();
//    }

    public WebElement getCourseTitle(String courseTitle) {
        return driver.findElement(By.cssSelector(String.format("section a[href^='/lessons/%s']", courseTitle)));
    }
}
