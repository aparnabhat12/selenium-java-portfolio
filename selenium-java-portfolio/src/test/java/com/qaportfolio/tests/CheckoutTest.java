package com.qaportfolio.tests;

import com.qaportfolio.base.BaseTest;
import com.qaportfolio.pages.CheckoutPage;
import com.qaportfolio.pages.InventoryPage;
import com.qaportfolio.pages.LoginPage;
import com.qaportfolio.utils.ConfigReader;
import com.qaportfolio.utils.DriverManager;
import com.qaportfolio.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(priority = 1, description = "Complete end-to-end checkout flow")
    public void TC01_EndToEndCheckout() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC01 - E2E Checkout Flow")
        );

        // Add items
        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        inventoryPage.goToCart();

        // Checkout
        DriverManager.getDriver().findElement(
            org.openqa.selenium.By.id("checkout")
        ).click();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillShippingInfo("John", "Doe", "560001");
        checkoutPage.clickFinish();

        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertTrue(confirmation.contains("Thank you"),
            "Order confirmation message should contain 'Thank you'");

        ExtentReportManager.getTest().info("Order placed successfully: " + confirmation);
    }

    @Test(priority = 2, description = "Checkout fails without first name")
    public void TC02_CheckoutWithoutFirstName() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC02 - Checkout Missing First Name")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.goToCart();

        DriverManager.getDriver().findElement(
            org.openqa.selenium.By.id("checkout")
        ).click();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillShippingInfo("", "Doe", "560001");

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
    }

    @Test(priority = 3, description = "Checkout fails without postal code")
    public void TC03_CheckoutWithoutPostalCode() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC03 - Checkout Missing Postal Code")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.goToCart();

        DriverManager.getDriver().findElement(
            org.openqa.selenium.By.id("checkout")
        ).click();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillShippingInfo("John", "Doe", "");

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
    }
}
