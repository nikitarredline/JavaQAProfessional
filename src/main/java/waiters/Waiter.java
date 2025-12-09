package waiters;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.GuiceScoped;

import java.time.Duration;

public class Waiter {

    private final WebDriver driver;

    @Inject
    public Waiter(GuiceScoped guiceScoped) {
        this.driver = guiceScoped.getDriver();
    }

    public boolean waitForCondition(ExpectedCondition condition) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(condition);
            return true;
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public WebElement waitForElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

}
