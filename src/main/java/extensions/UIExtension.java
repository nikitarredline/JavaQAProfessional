package extensions;

import com.google.inject.Guice;
import factory.WEBDriverFactory;
import modules.PagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        WebDriver driver = new WEBDriverFactory().create();

        //driver в pages module
        Guice.createInjector(new PagesModule()).injectMembers(context.getTestInstance().get());
    }
}
