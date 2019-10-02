package com.screen.ui.test;

import com.screen.ui.configuration.Configuration;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class BaseTest {
  protected static WebDriver driver;
  protected static final String loginPageUrl;

  static {
    loginPageUrl = Configuration.getUrl();
  }

  @SneakyThrows
  public static WebDriver getDriver() {
    DesiredCapabilities capability = DesiredCapabilities.chrome();

    return new RemoteWebDriver(new URL(Configuration.getHubUrl()), capability);
  }
}
