package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CoursesPage;
import support.GuiceScoped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @When("I click on a course by title {string}")
    public void clickCourseByTitle(String title) {
        coursesPage.clickCourseByTitle(title);

        guiceScoped.store(title, "courseTitle");
    }

    @When("I click on a random course")
    public void clickRandomCourse() {
        String title = coursesPage.getRandomCourseTitle();
        coursesPage.clickCourseByTitle(title);

        guiceScoped.store(title, "courseTitle");
    }

    @When("I click on a earliest random course")
    public void clickEarliestRandomCourse() {
        LocalDate date = coursesPage.open().getEarliestDate();
        String title = coursesPage.clickRandomCourseByDate(date);

        guiceScoped.store(title, "courseTitle");
        guiceScoped.store(date, "courseDate");
    }

    @When("I click on a latest random course")
    public void clickLatestRandomCourse() {
        LocalDate date = coursesPage.open().getLatestDate();
        String title = coursesPage.clickRandomCourseByDate(date);

        guiceScoped.store(title, "courseTitle");
        guiceScoped.store(date, "courseDate");
    }

    @Then("The category page checkbox successfully")
    public void shouldBeOpenedCoursePageByDate() {
        String title = guiceScoped.retrieve("categoryTitle");
        assertThat(coursesPage.pageCheckboxTrueShouldBeSameAs(title));
    }

    @Then("Print courses starting from earliest date {string}")
    public void printCourses(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        coursesPage.printCoursesFromEarliestDate(date);
    }
}
