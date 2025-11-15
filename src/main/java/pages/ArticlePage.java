package pages;

import annotations.UrlTemplate;
import org.openqa.selenium.WebDriver;

@UrlTemplate(
        value = "/article/{1}"
)
public class ArticlePage extends AbsBasePage<ArticlePage> {

    public ArticlePage(WebDriver driver) {
        super(driver);
    }
}
