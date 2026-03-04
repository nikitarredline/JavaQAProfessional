package pages;

import annotations.Path;
import annotations.UrlTemplate;
import annotations.Urls;
import com.google.inject.Inject;
import commons.AbsCommon;
import exceptions.PathNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbsBasePage<T> extends AbsCommon {

    private String baseUrl = System.getProperty("base.url");

    @Inject
    public void init() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "div[value='true'] label")
    private List<WebElement> checkbox;

    private List<WebElement> getCheckbox() {
        waiter.waitForCondition(d -> !checkbox.isEmpty());
        return checkbox;
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
                template = template.replace(String.format("{%d}", i+1), data[i]);
            }

            return template;
        }

        return "";
    }

    public T open() {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalStateException("System property 'base.url' is not set!");
        }
        driver.get(baseUrl + getPath());
        return (T)this;
    }

    public T open(String name, String... data) {
        driver.get(baseUrl + getPathWithData(name, data));

        return (T)this;
    }

    public T pageHeaderShouldBeSameAs(String expectedHeader) {
        int attempts = 3;
        while (attempts > 0) {
            try {
                // проверяем текст текущего header
                String actualText = header.getText();
                assertThat(actualText)
                        .as("Error")
                        .isEqualTo(expectedHeader);

                return (T) this;
            } catch (StaleElementReferenceException e) {
                attempts--;
            }
        }

        throw new RuntimeException("Заголовок не совпал с ожидаемым: " + expectedHeader);
    }

    public T pageCheckboxTrueShouldBeSameAs(String expectedText) {
        int attempts = 3;
        while (attempts > 0) {
            try {
                WebElement target = getCheckbox().stream()
                        .filter(el -> {
                            try {
                                return el.getText().equals(expectedText);
                            } catch (StaleElementReferenceException e) {
                                return false;
                            }
                        })
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(
                                "Элемент с текстом '" + expectedText + "' не найден"));

                assertThat(target.getText())
                        .as("Error")
                        .isEqualTo(expectedText);

                return (T) this;

            } catch (StaleElementReferenceException e) {
                attempts--;
            }
        }

        throw new RuntimeException("Не удалось найти стабильный элемент с текстом: " + expectedText);
    }

    public static final DateTimeFormatter RUS_DATE =
            DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));

    public void clickElement(WebElement element) {
        int attempts = 2;
        while (attempts > 0) {
            try {
                // скролл к центру
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                element.click();
                return;
            } catch (ElementClickInterceptedException e) {
                // fallback через JS click
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);
                return;
            } catch (StaleElementReferenceException e) {
                attempts--;
                if (attempts == 0) throw e;
            }
        }
    }
}
