package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import pages.CoursePage;
import support.GuiceScoped;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CoursePageSteps {

    @Inject
    private GuiceScoped guiceScoped;

    @Inject
    private CoursePage coursePage;

    @Then("The course page opens by title successfully")
    public void shouldBeOpenedCoursePage() {
        String title = guiceScoped.retrieve("courseTitle");
        assertThat(coursePage.pageHeaderShouldBeSameAs(title));
    }

    @Then("The course page opens by date successfully")
    public void shouldBeOpenedCoursePageByDate() {
        LocalDate date = guiceScoped.retrieve("courseDate");
        assertThat(coursePage.courseDateMatching(date));
    }

//    @Then("Get course price")
//    public void getCoursePrice() {
//        LocalDate date = guiceScoped.retrieve("courseDate");
//        assertThat(coursePage.courseDateMatching(date));
//    }
}
