package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Inject
    private ArticlePage articlePage;

    @FindBy(css = "a.photo__title")
    private List<WebElement> articles;

    public String getPhotoTitleByIndex(int index) {
        return articles.get(index - 1).getText();
    }

    public ArticlePage clickArticleByTitle(String title) {
        this.clickElementByPredicate.accept(articles, (WebElement element) -> element.getText().equals(title));
        return articlePage;
    }
}
