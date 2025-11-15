package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import pages.ArticlePage;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;

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
    public MainPage provideMainPage(WebDriver driver) {
        return new MainPage(driver);
    }

    @Provides
    @Singleton
    public ArticlePage provideArticlePage(WebDriver driver) {
        return new ArticlePage(driver);
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
