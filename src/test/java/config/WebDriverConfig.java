package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Factory;

import java.util.Map;

@Config.Sources("classpath:${run.type}.properties")
public interface WebDriverConfig extends Config {
    @Key("browser.name")
    Browser getBrowser();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("run.type")
    String getRunType();

    @Key("base.url")
    @DefaultValue("https://galamart.ru")
    String getBaseUrl();

    @Key("remote.url")
    String getRemoteUrl();

    @Key("video.url")
    String getVideoUrl();

    static WebDriverConfig load() {
        String runType = System.getProperty("run.type", "local");
        if (!runType.equals("local") && !runType.equals("remote")) {
            throw new IllegalArgumentException("run.type must be local or remote: " + runType);
        }
        Factory factory = ConfigFactory.newInstance();
        factory.setProperty("run.type", runType);
        return factory.create(WebDriverConfig.class, System.getProperties(),
                Map.of("SELENOID_REMOTE_URL", System.getenv().getOrDefault("SELENOID_REMOTE_URL", "")));
    }
}
