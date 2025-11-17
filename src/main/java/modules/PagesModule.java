package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
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
    public MainPage provideMainPage(WebDriver driver) {
        return new MainPage(driver);
    }

    @Provides
    @Singleton
    public CoursesPage provideCoursesPage(WebDriver driver) {
        return new CoursesPage(driver);
    }

    @Provides
    @Singleton
    public CoursePage provideCoursePage(WebDriver driver) {
        return new CoursePage(driver);
    }
}
