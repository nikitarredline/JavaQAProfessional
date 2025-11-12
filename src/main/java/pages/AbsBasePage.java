package pages;

import annotations.Path;
import annotations.UrlTemplate;
import annotations.Urls;
import commons.AbsCommon;
import exceptions.PathNotFoundException;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public abstract class AbsBasePage<T> extends AbsCommon {

    private String baseUrl = System.getProperty("base.url");

    public AbsBasePage(WebDriver driver) {
        super(driver);
    }

    public String getPath() {
        Class<T> clazz = (Class<T>) this.getClass();

        if (clazz.isAnnotationPresent(Path.class)) {
            Path pathObj = clazz.getDeclaredAnnotation(Path.class);
            return pathObj.value();
        }

        throw new PathNotFoundException();
    }

    public String getPathWithData(String name, String data[]) {
        Class<T> clazz = (Class<T>) this.getClass();

        UrlTemplate urlTemplate = null;
        if (clazz.isAnnotationPresent(Urls.class)) {
            Urls urls = clazz.getDeclaredAnnotation(Urls.class);
            UrlTemplate[] urlTemplates = urls.urlTemplate();
            urlTemplate = Arrays.stream(urlTemplates).filter((UrlTemplate urlTemplateFilter) -> urlTemplateFilter.name().equals(name)).findFirst().get();
        }

        if (clazz.isAnnotationPresent(UrlTemplate.class)) {
            urlTemplate = clazz.getDeclaredAnnotation(UrlTemplate.class);
        }

        if (urlTemplate != null) {
            String template = urlTemplate.value();

            for(int i=0; i<data.length; i++) {
                template.replace(String.format("{%d}", i+1), data[i]);
            }

            return template;
        }

        return "";
    }

    public T open() {
        driver.get(baseUrl + getPath());

        return (T)this;
    }

    public T open(String name, String... data) {
        driver.get(baseUrl + getPathWithData(name, data));

        return (T)this;
    }
}
