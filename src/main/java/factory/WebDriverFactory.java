package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Locale;

public class WebDriverFactory {

    private String browser = System.getProperty("browser.name").toLowerCase(Locale.ROOT);

    public WebDriver create() {
        switch (browser) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();

                return new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
            }
        }

        throw new BrowserNotSupportedException(browser);
    }

    public void init() {
        switch (browser) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();

                return;
            }
        }

        throw new BrowserNotSupportedException(browser);
    }
}
