package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.*;

public class ChromeSettings implements ISettings {

    private boolean remote = false;
    private String testName = "Default Test";
    private String browserVersion = "latest"; // версия браузера для Selenoid

    public ChromeSettings() {}

    public ChromeSettings(boolean remote, String testName, String browserVersion) {
        this.remote = remote;
        this.testName = testName;
        this.browserVersion = browserVersion;
    }

    @Override
    public AbstractDriverOptions settings() {
        ChromeOptions chromeOptions = new ChromeOptions();

        // Общие настройки Chrome
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.addArguments("--disable-infobars");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        // Настройки для Selenoid
        if (remote) {
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("name", testName);        // имя теста
            selenoidOptions.put("enableVideo", true);     // запись видео
            selenoidOptions.put("env", Arrays.asList("TZ=UTC")); // таймзона
            chromeOptions.setCapability("selenoid:options", selenoidOptions);

            // Версия браузера для Selenoid
            chromeOptions.setCapability("browserVersion", browserVersion);
        }

        return chromeOptions;
    }
}