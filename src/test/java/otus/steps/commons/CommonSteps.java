package otus.steps.commons;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import support.GuiceScoped;

public class CommonSteps {

    @Inject
    private GuiceScoped guiceScoped;

    private String baseUrl = System.getProperty("base.url");

    @Given("Chrome browser is open")
    public void openBrowser() {
        guiceScoped.getDriver().get(baseUrl);
    }
}
