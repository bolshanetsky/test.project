package com.screen.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BasePage {
    public static final int DEFAULT_TIMEOUT = 20000;
    public static final int DEFAULT_TIMEOUT_SEC = DEFAULT_TIMEOUT/1000;
    public final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Finds WebElement on page.
     */
    protected WebElement findElement(By locator) {
        return findElement(locator, ExpectedConditions::presenceOfElementLocated);
    }

    /**
     * Finds WebElement on page
     *
     * @param locator - By locator.
     * @param condition- ExpectedCondition
     * @return WebElement
     */
    protected WebElement findElement(By locator, final Function<By, ExpectedCondition<WebElement>> condition) {
        WebDriverWait wait = new WebDriverWait(this.driver, DEFAULT_TIMEOUT/1000);
        return wait.withTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                   .pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(WebDriverException.class)
                   .until(condition.apply(locator));
    }

    public WebDriverWait Wait() {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT_SEC);
    }
}
