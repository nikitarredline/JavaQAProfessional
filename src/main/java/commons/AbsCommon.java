package commons;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.GuiceScoped;
import waiters.Waiter;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbsCommon {

    protected final WebDriver driver;
    protected final Waiter waiter;

    @Inject
    public AbsCommon(GuiceScoped context, Waiter waiter) {
        this.driver = context.getDriver();
        this.waiter = waiter;
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
