package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import waiters.Waiter;

import java.util.List;
import java.util.Random;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Inject
    private Waiter waiter;
    @Inject
    private CoursesPage coursesPage;

    @FindBy(css = "nav a[href*='/categories/']")
    private List<WebElement> categories;

    private List<WebElement> getÑategories() {
        waiter.waitForCondition(d -> !categories.isEmpty());
        return categories;
    }

    public String getRandomCategory() {
        Random random = new Random();
        int randomIndex = random.nextInt(getÑategories().size());
        return getÑategories().get(randomIndex).getAttribute("textContent").split(" \\(")[0];
    }

    public CoursesPage clickCategoryByName(String categoryName) {
        WebElement training = driver.findElement(By.cssSelector("span[title='Îáó÷åíèå']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(training).perform();
        this.clickElementByPredicate.accept(getÑategories(), (WebElement element) -> element.getAttribute("textContent").split(" \\(")[0].equals(categoryName));
        return coursesPage;
    }
}
