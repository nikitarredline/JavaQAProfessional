package waiters;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.GuiceScoped;

import java.time.Duration;
import java.util.function.Function;

public class Waiter {

    private final WebDriver driver;

    @Inject
    public Waiter(GuiceScoped guiceScoped) {
        this.driver = guiceScoped.getDriver();
    }

    public <T> T waitForCondition(Function<WebDriver, T> condition) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(condition);
        } catch (TimeoutException e) {
            return null;
        }
    }


    public String waitForAnyText(By... locators) {
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            for (By locator : locators) {
                try {
                    WebElement el = d.findElement(locator);
                    if (el.isDisplayed() && !el.getText().isBlank()) {
                        return el.getText();
                    }
                } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException ignored) {
                }
            }
            return null;
        });
    }
}
