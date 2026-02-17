package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class WebDriverFactory {

    private final String browser =
            System.getProperty("browser.name", "chrome").toLowerCase(Locale.ROOT);

    private final String remoteUrl =
            System.getProperty("remote.url");

    private final String browserVersion =
            System.getProperty("browser.version", "latest");

    private final String deviceNameRaw =
            System.getProperty("device.name");

    private final String deviceName =
            (deviceNameRaw != null && !deviceNameRaw.isBlank())
                    ? deviceNameRaw
                    : null;

    private final String windowSizeRaw =
            System.getProperty("window.size");

    private static final int RETRY_COUNT = 3;

    private WebDriver driver;

    public WebDriver create() {

        if (driver != null) {
            return driver;
        }

        if (!"chrome".equals(browser)) {
            throw new BrowserNotSupportedException(browser);
        }

        boolean isRemote = remoteUrl != null && !remoteUrl.isBlank();

        if (isRemote) {
            driver = createRemoteChromeWithRetry();
        } else {
            driver = createLocalChrome();
        }

        return driver;
    }

    private WebDriver createLocalChrome() {

        WebDriverManager.chromedriver().setup();

        ChromeSettings settings = new ChromeSettings(
                false,
                "Local Test",
                browserVersion,
                deviceName,
                windowSizeRaw
        );

        return new ChromeDriver(settings.settings());
    }

    private WebDriver createRemoteChromeWithRetry() {

        for (int attempt = 1; attempt <= RETRY_COUNT; attempt++) {
            try {
                ChromeSettings settings = new ChromeSettings(
                        true,
                        "Remote Test",
                        browserVersion,
                        deviceName,
                        windowSizeRaw
                );

                return new RemoteWebDriver(
                        new URL(remoteUrl),
                        settings.settings()
                );

            } catch (MalformedURLException e) {
                throw new RuntimeException(
                        "Неверный remote.url: " + remoteUrl, e
                );

            } catch (RuntimeException e) {

                System.out.println(
                        "Попытка " + attempt +
                                " подключения к удалённому WebDriver не удалась: " +
                                e.getMessage()
                );

                if (attempt == RETRY_COUNT) {
                    throw new RuntimeException(
                            "Не удалось создать RemoteWebDriver после "
                                    + RETRY_COUNT + " попыток",
                            e
                    );
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }

        throw new RuntimeException("Не удалось создать RemoteWebDriver");
    }

    public void quit() {
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driver = null;
            }
        }
    }
}
