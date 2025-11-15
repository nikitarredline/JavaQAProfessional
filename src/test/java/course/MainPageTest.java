package course;

import com.google.inject.Inject;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class MainPageTest {

    @Inject
    private MainPage mainPage;

    @Test
    public void checkClickArticleTitle() {
        String title = mainPage.open().getPhotoTitleByIndex(1);
        mainPage.clickArticleByTitle(title)
                .pageHeaderShouldBeSameAs(title);
    }
}
