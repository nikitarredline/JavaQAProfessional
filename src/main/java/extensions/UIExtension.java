package extensions;

import com.google.inject.Guice;
import factory.WebDriverFactory;
import modules.PagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

public class UIExtension implements BeforeEachCallback, BeforeAllCallback, AfterEachCallback {
    private WebDriver driver;
    private WebDriverFactory webDriverFactory = new WebDriverFactory();

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        webDriverFactory.init();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        driver = webDriverFactory.create();

        WebDriverListener listener = new HighlightListener(driver);
        driver = new EventFiringDecorator(listener).decorate(driver);

        Guice.createInjector(new PagesModule(driver))
                .injectMembers(context.getTestInstance().get());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (driver != null) {
            driver.quit();
        }
    }
}
