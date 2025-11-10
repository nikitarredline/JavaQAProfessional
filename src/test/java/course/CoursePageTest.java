package course;

import com.google.inject.Inject;
import extensions.UIExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursePage;

@ExtendWith(UIExtension.class)
public class CoursePageTest {

    @Inject
    private CoursePage coursePage;
}
