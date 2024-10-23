package com.clickadu.pageobject;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.Selenide.webdriver;
import static io.qameta.allure.Allure.step;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.net.URIBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Configurable
@Lazy
public abstract class AbstractBasePage<T> {

  protected static final String OPEN_URL_LOG = "open url %s";
  protected final ApplicationContext applicationContext;

  protected AbstractBasePage(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @PostConstruct
  private void init() {
    Selenide.page(this);
  }

  @SneakyThrows
  @Step("Открыть URL")
  public <V extends AbstractBasePage<?>> V openUrl(String url, Class<V> pageClass) {
    log.info(String.format(OPEN_URL_LOG, url));
    Selenide.open(url);
    return applicationContext.getBean(pageClass);
  }
}
