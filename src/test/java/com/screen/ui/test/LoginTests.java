package com.screen.ui.test;

import com.screen.ui.testdata.TestData;
import com.screen.ui.pageobjects.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class LoginTests extends BaseTest{

  @BeforeMethod
  public void beforeTest(){
    driver = getDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    // Navigate to login page
    driver.get(loginPageUrl);
    loginPage = new LoginPage(driver);
  }

  @AfterMethod
  public void afterTest(){
    driver.quit();
  }

  private LoginPage loginPage;

  /**
   * Test: Validate successful login with valid credentials.
   * Steps:
   * 1. Navigate to login page.
   * 2. Enter valid login and password.
   * 3. Click Login button
   * Expected result:
   * 3. Home page is loaded
   */
  @Test
  public void validLoginTest(){

    // login with valid credentials
    loginPage.doLogin(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);

    // Assert that home page was loaded.
    Assert.assertTrue(loginPage.isUserLoggedIn(), "User wasn't logged in");
  }

  /**
   * Test: Validate proper message after unsuccessful login.
   * Steps:
   * 1. Navigate to login page
   * 2. Enter valid login
   * 3. Enter invalid password
   * 4. Click login button
   * Expected result:
   * 4. Error message is shown.
   */
  @Test
  public void invalidLoginTest() {
    loginPage.doLogin(TestData.VALID_LOGIN, TestData.INVALID_PASSWORD);
    String errorMessageText = loginPage.getErrorMessageText();

    // Assert that proper message is shown.
    Assert.assertEquals(TestData.ERROR_MESSAGE, errorMessageText, "Error message isn't shown or invalid");
  }
}
