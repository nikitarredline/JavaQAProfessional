package extensions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlightListener implements WebDriverListener {

    private final WebDriver driver;

    public HighlightListener(WebDriver driver) {
        this.driver = driver;
    }

    private void highlight(WebElement element) {
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript(
                    "arguments[0].style.border='3px solid #008000';" +
                            "arguments[0].style.backgroundColor='#90ee90';",
                    element
            );
        }
    }



    @Override
    public void beforeClick(WebElement element) {
        highlight(element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        highlight(element);
    }

    @Override
    public void beforeSubmit(WebElement element) {
        highlight(element);
    }
}
