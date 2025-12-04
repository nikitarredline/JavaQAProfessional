package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.MainPage;
import support.GuiceScoped;

public class MainPageSteps {

    @Inject
    private MainPage mainPage;

    @Inject
    private GuiceScoped guiceScoped;

    @Given("Main page is opened")
    public void openCoursesPage() {
        mainPage.open();
    }

    @When("I click on random category")
    public void clickRandomCategory() {
        String title = mainPage.open().getRandomCategory();
        mainPage.clickCategoryByTitle(title);

        guiceScoped.store(title, "categoryTitle");
    }
}
