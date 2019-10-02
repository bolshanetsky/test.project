package com.screen.ui.test;

import com.screen.ui.pageobjects.CartPage;
import com.screen.ui.testdata.TestData;
import com.screen.ui.pageobjects.HomePage;
import com.screen.ui.pageobjects.LoginPage;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductOrderTests extends BaseTest{

  private LoginPage loginPage;
  private HomePage homePage;

  @BeforeMethod
  public void beforeTest(){
    driver = getDriver();
    driver.get(loginPageUrl);
    loginPage = new LoginPage(driver);
    homePage = loginPage.doLogin(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
    loginPage.waitUntilLoginModalDisappear();
  }

  @AfterMethod
  public void afterTest(){
    driver.quit();
  }

  /**
   * Test: Verify products search by filters.
   * Steps:
   * 1. Navigate to Product search page.
   * 2. Search Monitor by producer, format and resolution
   * Expected result:
   * 1. Page with products is loaded
   * 2. Each search result matches search criteria.
   * */
  @Test
  public void testSearchFilters() {

    // search products by search terms
    homePage.openExtendedSearch()
            .selectProductCategoryByName(TestData.SearchTerms.PRODUCT_CATEGORY)
            .selectFilterCheckboxByName(TestData.SearchTerms.PRODUCER)
            .selectFilterCheckboxByName(TestData.SearchTerms.MONITOR_FORMAT)
            .selectFilterCheckboxByName(TestData.SearchTerms.MONITOR_RESOLUTION);

    // verify search results
    homePage.getDisplayedProductsNames().forEach( name -> {
      if (!StringUtils.containsIgnoreCase(name.getText(), TestData.SearchTerms.PRODUCER)) {
        System.out.println(name.getText() + "   " + TestData.SearchTerms.PRODUCER);
      }
      Assert.assertTrue(StringUtils.containsIgnoreCase(name.getText(), TestData.SearchTerms.PRODUCER), "Name doesn't contain chosen producer");
    });

    homePage.getDisplayedProductDescriptions().forEach( description -> {
      Assert.assertTrue(StringUtils.containsIgnoreCase(description.getText().replaceAll("\\.", ""),
                      TestData.SearchTerms.MONITOR_RESOLUTION), "Description doesn't contain chosen resolution");
    });
  }

  /**
   * Test: Verify products could be added to cart.
   * Steps:
   * 1. Navigate to Product search page.
   * 2. Search Monitor by producer, format and resolution
   * 3. Add two products to the cart
   * 4. Open cart
   * Expected result:
   * 1. Page with products is loaded
   * 2. Each search result matches search criteria.
   * 3. Added products are in the cart
   * */
  @Test
  public void testAddingToTheCart() {
    homePage.openExtendedSearch()
            .selectProductCategoryByName(TestData.SearchTerms.PRODUCT_CATEGORY)
            .selectFilterCheckboxByName(TestData.SearchTerms.PRODUCER);

    int currentCartSize = homePage.getCurrentCartSize();

    // add two search results to the cart
    homePage.addToTheCartByIndex(1);
    homePage.addToTheCartByIndex(3);

    CartPage cartPage = homePage.openCart();
    Assert.assertEquals(cartPage.getNumberOfProductsInCart(),
            currentCartSize + 2, "Cart product count doesn't match selected");
  }

  /**
   * Test: Verify products could be removed from the cart
   * Steps:
   * 1. Navigate to Product search page.
   * 2. Search Monitor by producer, format and resolution
   * 3. Add random products to the cart
   * 4. Open the cart
   * 5. Click remove for each product.
   * Expected result:
   * 1. Page with products is loaded
   * 2. Each search result matches search criteria.
   * 4. Selected products are in the cart
   * 5. Products are removed from the cart one by one
   * */
  @Test
  public void testRemovingProductsFromTheCart() {
    homePage.openExtendedSearch()
            .selectProductCategoryByName(TestData.SearchTerms.PRODUCT_CATEGORY)
            .selectFilterCheckboxByName(TestData.SearchTerms.PRODUCER);

    CartPage cartPage = homePage.openCart();
    cartPage.removeAllProducts();

    Assert.assertEquals(0, cartPage.getNumberOfProductsInCart(), "Cart is not empty");
  }

  /**
   * Test: Verify checkout procedure
   * Steps:
   * 1. Navigate to Product search page.
   * 2. Search Monitor by producer, format and resolution
   * 3. Add random products to the cart
   * 4. Open the cart
   * 5. Click 'Checkout' button
   * 6. Select 'Term of Service' checkbox
   * 7. Click 'Pay' button
   * Expected result:
   * 1. Page with products is loaded
   * 2. Each search result matches search criteria.
   * 4. Selected products are in the cart
   * 5. User is navigated to 'Checkout Summary' step
   * 5. 'Pay' button is disabled
   * 6. Order confirmation is shown
   * */
  @Test
  public void testEnd2EndCheckout() {
    // search products by search terms
    homePage.openExtendedSearch()
            .selectProductCategoryByName(TestData.SearchTerms.PRODUCT_CATEGORY)
            .selectFilterCheckboxByName(TestData.SearchTerms.PRODUCER);

    int currentCartSize = homePage.getCurrentCartSize();

    // add two search results to the cart
    homePage.addToTheCartByIndex(2);
    homePage.addToTheCartByIndex(4);

    CartPage cartPage = homePage.openCart();
    cartPage.checkout()
            .agreeTermsAndConditions()
            .continueToSummary();

    //Verify that summary of the order contains 2 selected products
    Assert.assertEquals(currentCartSize + 2, cartPage
            .getOrderSummaryCount(), "Checkout summary should сontain selected products");

    // Payment step is not implemented.
  }
}
