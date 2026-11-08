package config;

import com.codeborne.selenide.Config;
import com.codeborne.selenide.webdriver.ChromeDriverFactory;
import com.codeborne.selenide.webdriver.DriverFactory;
import com.codeborne.selenide.webdriver.FirefoxDriverFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Locale;

public class WebDriverProvider implements DriverFactory {
    private final Browser browser = WebDriverConfig.load().getBrowser();
    private final DriverFactory factory = switch (browser) {
        case CHROME -> new ChromeDriverFactory();
        case FIREFOX -> new FirefoxDriverFactory();
    };

    @Override
    public WebDriver create(Config config, com.codeborne.selenide.Browser selenideBrowser,
                            Proxy proxy, File downloadsFolder) {
        return factory.create(config, selectedBrowser(selenideBrowser), proxy, downloadsFolder);
    }

    @Override
    public MutableCapabilities createCapabilities(Config config, com.codeborne.selenide.Browser selenideBrowser,
                                                 Proxy proxy, File downloadsFolder) {
        return factory.createCapabilities(config, selectedBrowser(selenideBrowser), proxy, downloadsFolder);
    }

    private com.codeborne.selenide.Browser selectedBrowser(com.codeborne.selenide.Browser selenideBrowser) {
        return new com.codeborne.selenide.Browser(browser.name().toLowerCase(Locale.ROOT), selenideBrowser.headless);
    }
}
