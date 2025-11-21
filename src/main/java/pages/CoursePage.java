package pages;

import annotations.UrlTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDate;

@UrlTemplate(
        value = "/lessons/{1}"
)
public class CoursePage extends AbsBasePage<CoursePage> {

    public boolean courseDateMatching(LocalDate expectedDate) {
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements dateElements = doc.select("main section:first-child > div div:first-child > p");
        String dateText = dateElements.last().text().trim();
        LocalDate elementDate = LocalDate.parse(dateText + ", " + expectedDate.getYear(), RUS_DATE);
        return elementDate.equals(expectedDate);
    }
}
