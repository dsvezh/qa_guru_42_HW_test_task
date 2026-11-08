package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverConfig;
import config.WebDriverProvider;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class TestBase {
    @BeforeAll
    static void configureBrowser() {
        WebDriverConfig driverConfig = WebDriverConfig.load();
        boolean remote = "remote".equals(driverConfig.getRunType());
        String remoteUrl = remote ? driverConfig.getRemoteUrl() : null;
        if (remote && (remoteUrl == null || remoteUrl.isBlank())) {
            throw new IllegalArgumentException("Set SELENOID_REMOTE_URL or -Dremote.url for run.type=remote");
        }

        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
        Configuration.baseUrl = driverConfig.getBaseUrl();
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.browser = WebDriverProvider.class.getName();
        Configuration.browserVersion = driverConfig.getBrowserVersion();
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 30_000;
        Configuration.pageLoadTimeout = 60_000;
        Configuration.remote = remoteUrl;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (remote) {
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
        }
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void closeBrowser() {
        try {
            if (hasWebDriverStarted()) {
                addAttachment("Скриншот", () -> Attach.screenshotAs("Последний скриншот"));
                addAttachment("Исходный код страницы", () -> Attach.pageSource());
                addAttachment("Логи консоли браузера", () -> Attach.browserConsoleLogs());
                if (Configuration.remote != null) {
                    addAttachment("Видеозапись теста", () -> Attach.addVideo());
                }
            }
        } finally {
            closeWebDriver();
        }
    }

    private void addAttachment(String name, Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            Attach.attachAsText("Ошибка вложения: " + name, e.toString());
        }
    }

}
