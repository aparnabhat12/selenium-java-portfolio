package com.qaportfolio.tests;

import com.qaportfolio.base.BaseTest;
import com.qaportfolio.pages.CheckoutPage;
import com.qaportfolio.pages.InventoryPage;
import com.qaportfolio.pages.LoginPage;
import com.qaportfolio.utils.ConfigReader;
import com.qaportfolio.utils.DriverManager;
import com.qaportfolio.utils.ExtentReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckoutTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    private void goToCheckoutPage() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));

        // Add item to cart
        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.addItemToCartByIndex(0);

        // Click cart icon
        inventoryPage.goToCart();

        // Wait for cart page and click checkout
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        DriverManager.getDriver().findElement(By.id("checkout")).click();

        // Wait for checkout form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
    }

    @Test(priority = 1, description = "Complete end-to-end checkout flow")
    public void TC01_EndToEndCheckout() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC01 - E2E Checkout Flow")
        );

        goToCheckoutPage();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillShippingInfo("John", "Doe", "560001");
        checkoutPage.clickFinish();

        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertTrue(confirmation.contains("Thank you"),
            "Order confirmation message should contain 'Thank you'");
    }

    @Test(priority = 2, description = "Checkout fails without first name")
    public void TC02_CheckoutWithoutFirstName() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC02 - Checkout Missing First Name")
        );

        goToCheckoutPage();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillShippingInfo("", "Doe", "560001");

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
    }

    @Test(priority = 3, description = "Checkout fails without postal code")
    public void TC03_CheckoutWithoutPostalCode() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC03 - Checkout Missing Postal Code")
        );

        goToCheckoutPage();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillShippingInfo("John", "Doe", "");

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
    }
}