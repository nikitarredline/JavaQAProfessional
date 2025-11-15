package course;

import com.google.inject.Inject;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursesPage;

@ExtendWith(UIExtension.class)
public class CoursesPageTest {

    @Inject
    private CoursesPage coursesPage;

    @Test
    public void checkClickArticleTitle() {
        String courseName = coursesPage.open()
                .getCourseName(1);
        coursesPage.clickCourseByName(courseName)
                .pageHeaderShouldBeSameAs(courseName);
    }
}
