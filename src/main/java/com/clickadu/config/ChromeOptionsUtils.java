package com.clickadu.config;

import com.codeborne.selenide.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Log4j2
@Lazy
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChromeOptionsUtils {

  public MutableCapabilities getSelenideCapabilities() {
    ChromeOptions chromeOptions = new ChromeOptions();

    Map<String, Object> prefs = Map.of(
        "download.prompt_for_download", false,
        "download.directory_upgrade", true
    );

    chromeOptions.setExperimentalOption("prefs", prefs);
    chromeOptions.addArguments(getArguments());
    Configuration.browserCapabilities = chromeOptions;

    MutableCapabilities mutableCapabilities = getRemoteBrowserSettings();
    mutableCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    mutableCapabilities.setCapability("goog:loggingPrefs", getLoggingPreferences());

    return mutableCapabilities;
  }

  public List<String> getArguments() {
    List<String> arguments = new ArrayList<>();
    arguments.add("--disable-gpu");
    arguments.add("--disable-dev-shm-usage");
    arguments.add("--no-sandbox");
    arguments.add("--ignore-certificate-errors");
    arguments.add("--start-maximized");

    return arguments;
  }

  protected MutableCapabilities getRemoteBrowserSettings() {
    MutableCapabilities mutableCapabilities = new MutableCapabilities();
    mutableCapabilities.setCapability("browserName", Configuration.browser);
    mutableCapabilities.setCapability("browserVersion", Configuration.browserVersion);

    return mutableCapabilities;
  }

  public LoggingPreferences getLoggingPreferences() {
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
    logPrefs.enable(LogType.BROWSER, Level.ALL);
    logPrefs.enable(LogType.SERVER, Level.ALL);
    logPrefs.enable(LogType.CLIENT, Level.ALL);
    logPrefs.enable(LogType.DRIVER, Level.ALL);
    logPrefs.enable(LogType.PROFILER, Level.ALL);
    return logPrefs;
  }
}
