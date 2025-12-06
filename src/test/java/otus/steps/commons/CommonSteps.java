package otus.steps.commons;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import support.GuiceScoped;

public class CommonSteps {

    @Inject
    private GuiceScoped guiceScoped;

    @Given("{string} browser is open")
    public void openBrowser(String browserName) {
        System.setProperty("browser.name", browserName.toLowerCase());

        String baseUrl = System.getProperty("base.url");
        guiceScoped.getDriver().get(baseUrl);
    }
}
