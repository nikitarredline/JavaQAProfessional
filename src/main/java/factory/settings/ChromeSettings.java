package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeSettings {

    private final boolean isRemote;
    private final String testName;
    private final String browserVersion;
    private final String deviceName;
    private final String windowSize;

    public ChromeSettings(boolean isRemote, String testName, String browserVersion, String deviceName, String windowSize) {
        this.isRemote = isRemote;
        this.testName = testName;
        this.browserVersion = browserVersion;
        this.deviceName = deviceName;
        this.windowSize = windowSize;
    }

    public ChromeOptions settings() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");

        if (deviceName != null && !deviceName.isBlank()) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", deviceName);
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        } else if (windowSize != null && !windowSize.isBlank()) {
            String[] dims = windowSize.split(",");
            if (dims.length == 2) {
                options.addArguments(String.format("--window-size=%s,%s", dims[0], dims[1]));
            }
        }

        if (isRemote) {
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVideo", true);
            selenoidOptions.put("name", testName);
            options.setCapability("selenoid:options", selenoidOptions); // <- именно так для RemoteWebDriver
        }

        if (browserVersion != null && !browserVersion.isBlank()) {
            options.setCapability("browserVersion", browserVersion);
        }

        return options;
    }
}
