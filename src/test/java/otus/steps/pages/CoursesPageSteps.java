package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CoursesPage;
import support.GuiceScoped;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CoursesPageSteps {

    @Inject
    private CoursesPage coursesPage;

    @Inject
    private GuiceScoped guiceScoped;

    @Given("Courses catalog page is opened")
    public void openCoursesPage() {
        coursesPage.open();
    }

    @When("I click on a random course")
    public void clickRandomCourse() {
        String title = coursesPage.getRandomCourseTitle();
        coursesPage.clickCourseByTitle(title);

        guiceScoped.store(title, "courseTitle");
    }

    @When("I click on a earliest course")
    public void clickEarliestCourse() {
        LocalDate date = coursesPage.open().getEarliestDate();
        String title = coursesPage.open().getCourseNameByDate(date);
        coursesPage.clickCourseByTitle(title);

        guiceScoped.store(date, "courseDate");
    }

    @When("I click on a latest course")
    public void clickLatestCourse() {
        LocalDate date = coursesPage.open().getLatestDate();
        String title = coursesPage.open().getCourseNameByDate(date);
        coursesPage.clickCourseByTitle(title);

        guiceScoped.store(date, "courseDate");
    }

    @Then("The category page checkbox successfully")
    public void shouldBeOpenedCoursePageByDate() {
        String title = guiceScoped.retrieve("categoryTitle");
        assertThat(coursesPage.pageCheckboxTrueShouldBeSameAs(title));
    }
}
