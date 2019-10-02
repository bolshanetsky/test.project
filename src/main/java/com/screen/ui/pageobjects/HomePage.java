package com.screen.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.logging.Logger;

public class HomePage extends BasePage {

  private static final Logger logger = Logger.getLogger(HomePage.class.getName());

  public HomePage(WebDriver driver) {
    super(driver);
    logger.info("Creating Home Page.");
  }

  public void addToTheCartByIndex(int index) {
    String locator = String.format(Locators.ADD_TO_CART_BY_INDEX, index);
    findElement(By.xpath(locator)).click();
    findElement(Locators.CLOSE_ADD_BUTTON).click();
  }

  public int getCurrentCartSize() {
    if (driver.findElements(Locators.CART_COUNTER).size() == 0) return 0;
    String strValue = findElement(Locators.CART_COUNTER).getText();
    if (strValue.isEmpty()) return 0;
    else return Integer.parseInt(strValue);
  }

  public CartPage openCart() {
    findElement(Locators.CART_BUTTON).click();
    return new CartPage(driver);
  }

  public HomePage openExtendedSearch() {
    findElement(Locators.EXTENDED_SEARCH_BUTTON).click();
    Wait().until(ExpectedConditions.visibilityOfElementLocated(Locators.FILTERS_PANE));
    return this;
  }

  public HomePage selectProductCategoryByName(String categoryName) {
    By categoryTab = By.xpath(String.format(Locators.CATEGORY_TAB_BY_NAME, categoryName));
    findElement(categoryTab, ExpectedConditions::visibilityOfElementLocated).click();
    Wait().until(ExpectedConditions.visibilityOfElementLocated(Locators.FILTERS_PANE));

    return this;
  }

  public HomePage selectFilterCheckboxByName(String name) {
    By filterCheckbox = By.xpath(String.format(Locators.FILTER_CHECKBOX, name));
    findElement(filterCheckbox, ExpectedConditions::visibilityOfElementLocated).click();
    By checkedBox = By.xpath(String.format(Locators.FILTER_CHECKBOX_CHECKED, name));
    Wait().until(ExpectedConditions.presenceOfElementLocated(checkedBox));
    Wait().until(ExpectedConditions.invisibilityOfElementLocated(Locators.WAIT_SPINNER));
    return this;
  }

  public List<WebElement> getDisplayedProductsNames() {
    return driver.findElements(Locators.PRODUCT_NAMES);
  }

  public List<WebElement> getDisplayedProductDescriptions() {
    return driver.findElements(Locators.PRODUCT_DESCRIPTIONS);
  }

  public static class Locators {

    public static final By EXTENDED_SEARCH_BUTTON = By.cssSelector("#searchex a");
    public static final String CATEGORY_TAB_BY_NAME = "//ul[@class='nav-tabs']//a[text()='%s']";
    public static final String FILTER_CHECKBOX = "//span[@title='%s']/../span[@class='checkBox']";
    public static final String FILTER_CHECKBOX_CHECKED = "//span[@title='%s']/..//input[@checked='checked']";
    public static final By PRODUCT_NAMES = By.cssSelector("div.product_name");
    public static final By PRODUCT_DESCRIPTIONS = By.cssSelector("span.short_description");
    public static final By FILTERS_PANE = By.cssSelector("#filterAreaNumber_1");
    public static final By WAIT_SPINNER = By.cssSelector("#wait3");
    public static final String ADD_TO_CART_BY_INDEX = "(//button[contains(@class,'add2cart_Btn')])[%s]";
    public static final By CLOSE_ADD_BUTTON = By.cssSelector("a[data-wt=Schließen]");
    public static final By CART_BUTTON = By.cssSelector("a.shoppingcart");
    public static final By CART_COUNTER = By.cssSelector("#cart-count");
  }
}
