package com.screen.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;

public class LoginPage extends BasePage {

  private static final Logger logger = Logger.getLogger(LoginPage.class.getName());


  public LoginPage(WebDriver driver){
    super(driver);
  }

  public HomePage doLogin(String login, String password) {
    logger.info(String.format("Do login for Login: %s", login));
    getLoginField().sendKeys(login);
    getPasswordField().sendKeys(password);
    getLoginButton().click();
    return new HomePage(driver);
  }

  public WebElement getLoginField() {
    return findElement(Locators.LOGIN_FIELD);
  }

  public WebElement getPasswordField() {
    return findElement(Locators.PASSWORD_FIELD);
  }

  public WebElement getLoginButton() {
    return findElement(Locators.LOGIN_BUTTON);
  }

  public String getErrorMessageText() {
    return getErrorMessage().getText();
  }

  public WebElement getErrorMessage() {
    return findElement(Locators.ERROR_MESSAGE, ExpectedConditions::visibilityOfElementLocated);
  }

  public boolean isUserLoggedIn() {
    return findElement(Locators.MY_ACCOUNT_BUTTON).getText().toLowerCase().trim().equals("kundenkonto");
  }

  public void waitUntilLoginModalDisappear() {
    new WebDriverWait(driver, DEFAULT_TIMEOUT)
            .until(ExpectedConditions.invisibilityOfElementLocated(Locators.LOGIN_MODAL));
  }

  public static class Locators {

    public static final By LOGIN_FIELD = By.cssSelector("input#layerEmailAddress");
    public static final By PASSWORD_FIELD =  By.cssSelector("input#layerPassword");
    public static final By LOGIN_BUTTON = By.cssSelector("button#layerLoginButton");
    public static final By ERROR_MESSAGE = By.cssSelector("div#loginLayerValidation");
    public static final By MY_ACCOUNT_BUTTON = By.cssSelector("div#haccount a");
    public static final By LOGIN_MODAL = By.cssSelector("div#login_layer");
  }
}
