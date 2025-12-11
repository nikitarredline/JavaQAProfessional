package commons;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.GuiceScoped;
import waiters.Waiter;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbsCommon {

    protected final WebDriver driver;
    protected Waiter waiter;

    @Inject
    public AbsCommon(GuiceScoped context, Waiter waiter) {
        this.driver = context.getDriver();
        this.waiter = waiter;
    }

    public WebElement $(By selector) {
        return driver.findElement(selector);
    }

    public BiConsumer<List<WebElement>, Predicate<WebElement>> clickElementByPredicate() {
        return (elements, elementPredicate) -> {
            WebElement el = elements.stream()
                    .filter(elementPredicate)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Element не найден по предикату"));

            boolean clickable = waiter.waitForCondition(driver -> {
                try {
                    return el.isDisplayed() && el.isEnabled();
                } catch (Exception e) {
                    return false;
                }
            });

            if (!clickable) {
                throw new RuntimeException("Элемент найден, но не кликабельный после ожидания");
            }

            scrollAndClick(el);
        };
    }

    public final WebElement scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});",
                element
        );

        boolean clickable = waiter.waitForCondition(d -> {
            try {
                return element.isDisplayed() && element.isEnabled();
            } catch (Exception e) {
                return false;
            }
        });

        if (!clickable) {
            throw new RuntimeException("Element найден, но не кликабельный после ожидания");
        }

        element.click();
        return element;
    }
}