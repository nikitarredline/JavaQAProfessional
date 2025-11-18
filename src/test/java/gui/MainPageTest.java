package gui;

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
    public void checkCourseCategories() {
        String categoryName = mainPage.open().getRandomCategory();
        mainPage.clickCategoryByName(categoryName)
                .pageCheckboxTrueShouldBeSameAs(categoryName);
    }
}
