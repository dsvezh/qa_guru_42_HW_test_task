package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class Attach {
    @Attachment(value = "{attachName}", type = "image/png", fileExtension = ".png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Исходный код страницы", type = "text/plain", fileExtension = ".txt")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain", fileExtension = ".txt")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    public static void browserConsoleLogs() {
        attachAsText("Логи консоли браузера", String.join("\n", Selenide.getWebDriverLogs(BROWSER)));
    }

    @Attachment(value = "Видеозапись теста", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        String videoUrl = getVideoUrl()
                .replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
        return "<html><head><meta charset=\"UTF-8\"></head><body>"
                + "<video width=\"100%\" controls autoplay>"
                + "<source src=\"" + videoUrl + "\" type=\"video/mp4\">"
                + "</video></body></html>";
    }

    public static String getVideoUrl() {
        String videoBaseUrl = System.getProperty("videoUrl", "https://selenoid.qa.guru/video/");
        return videoBaseUrl + (videoBaseUrl.endsWith("/") ? "" : "/") + sessionId() + ".mp4";
    }
}
