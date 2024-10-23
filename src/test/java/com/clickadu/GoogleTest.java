package com.clickadu;

import com.clickadu.config.SelenideConfiguration;
import com.clickadu.pageobject.GoogleSearchPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.webdriver.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;

@ExtendWith({
    AllureJunit5.class
})
@TestInstance(Lifecycle.PER_CLASS)
@Log4j2
@SpringBootTest(classes = SpringBootTestApplication.class, webEnvironment = WebEnvironment.NONE)
@Lazy
public class GoogleTest {

  @Autowired
  protected SelenideConfiguration selenideConfiguration;

  @Autowired
  protected ApplicationContext applicationContext;

  protected SelenideConfig selenideConfig;
  protected MutableCapabilities mutableCapabilities;

  @BeforeEach
  @SneakyThrows
  public void setupConfig() {
    String buildName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm"));

    selenideConfig = selenideConfiguration.selenideConfig();
    mutableCapabilities = selenideConfiguration.getRemoteBrowserSettings(buildName);

    URL remoteWebDriverUrl = new URL(String.format("http://%s:%s@hub.browserstack.com/wd/hub",
        "username", "password"));

    WebDriver driver = new RemoteWebDriver(remoteWebDriverUrl, mutableCapabilities);
    WebDriverRunner.setWebDriver(driver);
  }

  @Test
  public void openGoogle() {
    GoogleSearchPage googleSearchPage = applicationContext.getBean(GoogleSearchPage.class).openGooglePage();
  }
}
