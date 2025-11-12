package pages;

import annotations.UrlTemplate;
import org.openqa.selenium.WebDriver;

@UrlTemplate(
        value = "/lessons/{1}"
)
public class CoursePage extends AbsBasePage<CoursePage> {

    public CoursePage(WebDriver driver) {
        super(driver);
    }

}
