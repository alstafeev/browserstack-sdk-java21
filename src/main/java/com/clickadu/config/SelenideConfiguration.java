package com.clickadu.config;

import com.codeborne.selenide.SelenideConfig;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.openqa.selenium.MutableCapabilities;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelenideConfiguration {

  protected ChromeOptionsUtils chromeOptionsUtils;

  protected SelenideConfiguration(ChromeOptionsUtils chromeOptionsUtils) {
    this.chromeOptionsUtils = chromeOptionsUtils;
  }

  public SelenideConfig selenideConfig() {
    SelenideConfig config = new SelenideConfig();
    return getCoreSelenideConfig(config);
  }

  @SneakyThrows
  public SelenideConfig getCoreSelenideConfig(SelenideConfig selenideConfig) {
    MutableCapabilities mutableCapabilities = chromeOptionsUtils.getSelenideCapabilities();
    selenideConfig.browserCapabilities(mutableCapabilities);
    return selenideConfig;
  }

  public MutableCapabilities getRemoteBrowserSettings(String buildName) {
    var mutableCapabilities = new MutableCapabilities();

    mutableCapabilities.setCapability("browserName", "chrome");
    mutableCapabilities.setCapability("browserVersion", "latest");

    var browserstackOptions = getBrowserStackOptions(buildName, "at-autotests");

    mutableCapabilities.setCapability("bstack:options", browserstackOptions);
    return mutableCapabilities;
  }

  protected Map<String, Serializable> getBrowserStackOptions(String buildName, String projectName) {
    var browserstackOptions = new HashMap<String, Serializable>(Map.of(
        "browserName", "chrome",
        "browserVersion", "latest",
        "deviceName", "windows",
        "realMobile", "true",
        "os", "windows",
        "osVersion", "11",
        "projectName", "at-autotests",
        "buildName", buildName,
        "buildTag", projectName,
        "local", true
    ));

    browserstackOptions.put("debug", "true");
    browserstackOptions.put("networkLogs", "true");
    browserstackOptions.put("consoleLogs", "verbose");
    browserstackOptions.put("appiumLogs", "true");
    browserstackOptions.put("seleniumLogs", "true");
    browserstackOptions.put("disableCorsRestrictions", "true");

    return browserstackOptions;
  }
}
