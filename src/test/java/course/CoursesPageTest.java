package course;

import com.google.inject.Inject;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursePage;
import pages.CoursesPage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(UIExtension.class)
public class CoursesPageTest {

    @Inject
    private CoursesPage coursesPage;

    @Inject
    private CoursePage coursePage;

    @Test
    public void checkClickCourseTitle() {
        String courseName = coursesPage.open()
                .getCourseName(1);
        coursesPage.clickCourseByName(courseName)
                .pageHeaderShouldBeSameAs(courseName);
    }

    @Test
    public void checkClickEarliestCourseTitleAndDates() {
        LocalDate date = coursesPage.open().getEarliestDate();
        String courseName = coursesPage.open().getCourseNameByDate(date);
        coursesPage.clickCourseByName(courseName)
                .pageHeaderShouldBeSameAs(courseName);
        assertTrue(coursePage.courseDateMatching(date));
    }

    @Test
    public void checkClickLatestCourseTitleAndDates() {
        LocalDate date = coursesPage.open().getLatestDate();
        String courseName = coursesPage.open().getCourseNameByDate(date);
        coursesPage.clickCourseByName(courseName)
                .pageHeaderShouldBeSameAs(courseName);
        assertTrue(coursePage.courseDateMatching(date));
    }
}
