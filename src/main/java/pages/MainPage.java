package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;
import waiters.Waiter;

import java.util.List;
import java.util.Random;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

    @Inject
    public MainPage(GuiceScoped guiceScoped, Waiter waiter) {
        super(guiceScoped, waiter);
    }

    @Inject
    private CoursesPage coursesPage;

    private static final Random RANDOM = new Random();

    @FindBy(css = "nav a[href*='/categories/']")
    private List<WebElement> categories;

    private List<WebElement> getCategories() {
        waiter.waitForCondition(d -> !categories.isEmpty());
        return categories;
    }

    public String getRandomCategory() {
        int randomIndex = RANDOM.nextInt(getCategories().size());
        return getCategories().get(randomIndex).getAttribute("textContent").split(" \\(")[0];
    }

    public CoursesPage clickCategoryByTitle(String categoryName) {
        WebElement training = driver.findElement(By.cssSelector("span[title='Обучение']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(training).perform();
        this.clickElementByPredicate.accept(getCategories(), (WebElement element) -> element.getAttribute("textContent").split(" \\(")[0].equals(categoryName));
        return coursesPage;
    }
}
