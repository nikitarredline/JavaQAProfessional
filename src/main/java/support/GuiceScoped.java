package support;

import extensions.HighlightListener;
import factory.WebDriverFactory;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.HashMap;
import java.util.Map;

@ScenarioScoped
public class GuiceScoped {
    private WebDriver driver;

    public GuiceScoped() {
        driver = new WebDriverFactory().create();
        driver = new EventFiringDecorator<>(new HighlightListener(driver)).decorate(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    private Map<String, Object> storeObject = new HashMap<>();

    public <T> void store(T object, String key) {
        storeObject.put(key, object);
    }

    public <T> T retrieve(String key) {
        return (T)storeObject.get(key);
    }
}
