package com.screen.ui.configuration;

public class Configuration {

  private static String DEFAULT_SELENIUM_URL = "http://localhost:4444/wd/hub";
  private static String url = "https://www.notebooksbilliger.de/index.php/action/login";

  public static String getHubUrl() {
    String envVar = System.getProperty("selenium.hub");
    return envVar == null || envVar.isEmpty() ? DEFAULT_SELENIUM_URL : envVar;
  }

  public static String getUrl() {
    return url;
  }
}
