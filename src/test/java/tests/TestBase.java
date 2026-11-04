package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
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
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
        Configuration.baseUrl = System.getProperty("baseUrl", "https://galamart.ru");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.browserVersion = System.getProperty("browserVersion", "");
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 30_000;
        Configuration.pageLoadTimeout = 60_000;
        String remoteUrl = System.getProperty("remoteUrl", "");
        Configuration.remote = remoteUrl.isBlank() ? null : remoteUrl;
        if (Configuration.remote != null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
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
