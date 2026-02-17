package extensions;

import com.google.inject.Guice;
import factory.WebDriverFactory;
import modules.PagesModule;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {

    private WebDriver driver;
    private WebDriverFactory webDriverFactory;

    @Override
    public void beforeEach(ExtensionContext context) {

        webDriverFactory = new WebDriverFactory();

        driver = webDriverFactory.create();

        WebDriverListener listener = new HighlightListener(driver);
        driver = new EventFiringDecorator(listener).decorate(driver);

        Guice.createInjector(new PagesModule(driver))
                .injectMembers(context.getTestInstance().orElseThrow());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (webDriverFactory != null) {
            webDriverFactory.quit();
        }
    }
}
