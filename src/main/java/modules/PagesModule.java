package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import pages.CoursePage;

public class PagesModule extends AbstractModule {

    @Provides
    @Singleton
    public CoursePage getCoursePage() {
        return new CoursePage();
    }
}
