package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import waiters.Waiter;

public class PagesModule extends AbstractModule {

    private WebDriver driver;

    public PagesModule(WebDriver driver) {
        this.driver = driver;
    }

    @Provides
    @Singleton
    public WebDriver provideWebDriver() {
        return driver;
    }

    @Provides
    @Singleton
    public Waiter provideWaiter() {
        return new Waiter(driver);
    }

    @Provides
    @Singleton
    public Actions provideActions() {
        return new Actions(driver);
    }
}

