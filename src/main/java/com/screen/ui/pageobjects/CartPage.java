package com.screen.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.logging.Logger;

public class CartPage extends BasePage {
    private static final Logger logger = Logger.getLogger(CartPage.class.getName());

    public CartPage(WebDriver driver) {
        super(driver);
        logger.info("Creating Cart Page.");
    }

    public int getNumberOfProductsInCart() {
        return driver.findElements(Locators.PRODUCTS_CONTAINER).size();
    }

    public int getOrderSummaryCount() {
        return driver.findElements(Locators.ORDER_SUMMARY_PRODUCTS).size();
    }

    public void removeAllProducts() {
        int productCount = getNumberOfProductsInCart();
        while (productCount > 0) {
            findElement(Locators.DELETE_BUTTON, ExpectedConditions::elementToBeClickable).click();
            productCount--;
        }
    }

    public CheckoutPage checkout() {
        findElement(Locators.CHECKOUT_BUTTON).click();
        return new CheckoutPage(driver);
    }

    public static class Locators {

        public static final By PRODUCTS_CONTAINER = By.cssSelector("div.listing_main > div.mouseover");
        public static final By DELETE_BUTTON = By.cssSelector("button.js-shoppingCart-deleteCta");
        public static final By CHECKOUT_BUTTON = By.cssSelector("button.go_to_checkout_FB");
        public static final By ORDER_SUMMARY_PRODUCTS = By.cssSelector(".summaryRow .articleRow");
    }
}
