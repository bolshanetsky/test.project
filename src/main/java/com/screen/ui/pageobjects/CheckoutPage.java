package com.screen.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class CheckoutPage extends BasePage {

    private static final Logger logger = Logger.getLogger(HomePage.class.getName());

    public CheckoutPage(WebDriver driver) {
        super(driver);
        logger.info("Creating Checkout Page.");
    }

    public CheckoutPage agreeTermsAndConditions() {
        findElement(Locators.TERMS_CHECKBOX).click();
        return this;
    }

    public CheckoutPage continueToSummary() {
        findElement(Locators.CONTINUE_BUTTON);
        return this;
    }

    public static class Locators {

        public static final By TERMS_CHECKBOX = By.cssSelector("input#conditions");
        public static final By CONTINUE_BUTTON = By.cssSelector("button.nbx-btn-disabled");
    }
}
