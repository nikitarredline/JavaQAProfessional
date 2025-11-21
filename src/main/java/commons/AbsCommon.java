package commons;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import waiters.Waiter;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbsCommon {

    @Inject
    protected WebDriver driver;

    @Inject
    protected Waiter waiter;

    public AbsCommon() {
    }

    public WebElement $(By selector) {
        return driver.findElement(selector);
    }

    public static final BiConsumer<List<WebElement>, Predicate<WebElement>> clickElementByPredicate =
            (elements, elementPredicate) ->
                    elements.stream()
                            .filter(elementPredicate)
                            .findFirst()
                            .get()
                            .click();
}
