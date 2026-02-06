package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class WebDriverFactory {

    private String browser = System.getProperty("browser.name", "chrome").toLowerCase(Locale.ROOT);
    private String remoteUrl = System.getProperty("remote.url");       // URL Selenoid
    private String browserVersion = System.getProperty("browser.version", "latest"); // версия браузера

    public WebDriver create() {
        switch (browser) {
            case "chrome": {
                boolean remote = remoteUrl != null && !remoteUrl.isEmpty();
                if (!remote) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = (ChromeOptions) new ChromeSettings().settings();
                    return new ChromeDriver(options);
                } else {
                    try {
                        ChromeOptions options = (ChromeOptions) new ChromeSettings(true, "Remote Test", browserVersion).settings();
                        return new RemoteWebDriver(new URL(remoteUrl), options);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
                    }
                }
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
