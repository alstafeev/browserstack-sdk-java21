package com.clickadu.pageobject;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Configurable
@Lazy
public class GoogleSearchPage extends AbstractBasePage<GoogleSearchPage> {

  protected GoogleSearchPage(ApplicationContext applicationContext) {
    super(applicationContext);
  }

  public GoogleSearchPage openGooglePage() {
    return openUrl("https://google.com", GoogleSearchPage.class);
  }
}
