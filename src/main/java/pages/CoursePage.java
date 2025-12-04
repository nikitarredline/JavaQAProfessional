package pages;

import annotations.UrlTemplate;
import com.google.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import support.GuiceScoped;
import waiters.Waiter;

import java.time.LocalDate;

@UrlTemplate(
        value = "/lessons/{1}"
)
public class CoursePage extends AbsBasePage<CoursePage> {

    @Inject
    public CoursePage(GuiceScoped guiceScoped, Waiter waiter) {
        super(guiceScoped, waiter);
    }

    public boolean courseDateMatching(LocalDate expectedDate) {
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements dateElements = doc.select("main section:first-child > div div:first-child > p");
        String dateText = dateElements.last().text().trim();
        LocalDate elementDate = LocalDate.parse(dateText + ", " + expectedDate.getYear(), RUS_DATE);
        return elementDate.equals(expectedDate);
    }
}
