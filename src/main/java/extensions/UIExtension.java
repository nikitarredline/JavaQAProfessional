package extensions;

import com.google.inject.Guice;
import factory.WebDriverFactory;
import modules.PagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        WebDriver driver = new WebDriverFactory().create();

        Guice.createInjector(new PagesModule(driver)).injectMembers(context.getTestInstance().get());
    }
}
