package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Locale;

public class WebDriverFactory {

    private String browser;


    private String getBrowser() {
        if (browser == null) {
            String property = System.getProperty("browser.name");
            if (property == null || property.isEmpty()) {
                throw new BrowserNotSupportedException("Browser not specified in feature file!");
            }
            browser = property.toLowerCase(Locale.ROOT);
        }
        return browser;
    }

    public WebDriver create() {
        switch (getBrowser()) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = (ChromeOptions) new ChromeSettings().settings();
                return new ChromeDriver(options);
            }
            default:
                throw new BrowserNotSupportedException(getBrowser());
        }
    }
}
