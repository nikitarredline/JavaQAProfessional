package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Locale;

public class WEBDriverFactory {

    private String browser = System.getProperty("browser.name").toLowerCase(Locale.ROOT);

    public WebDriver create() {
        switch (browser) {
            case "chrome": {
                return new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
            }
        }

        throw new BrowserNotSupportedException(browser);
    }
}
