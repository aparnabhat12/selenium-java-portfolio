package com.qaportfolio.tests;

import com.qaportfolio.base.BaseTest;
import com.qaportfolio.pages.InventoryPage;
import com.qaportfolio.pages.LoginPage;
import com.qaportfolio.utils.ConfigReader;
import com.qaportfolio.utils.DriverManager;
import com.qaportfolio.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class InventoryTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(priority = 1, description = "Verify 6 products are displayed on inventory page")
    public void TC01_ProductCountIs6() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC01 - Product Count")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded");
        Assert.assertEquals(inventoryPage.getProductCount(), 6, "Should display 6 products");
    }

    @Test(priority = 2, description = "Verify products can be sorted A to Z")
    public void TC02_SortAtoZ() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC02 - Sort A to Z")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.sortBy("az");
        List<String> names = inventoryPage.getProductNames();
        List<String> sorted = names.stream().sorted().toList();
        Assert.assertEquals(names, sorted, "Products should be sorted A-Z");
    }

    @Test(priority = 3, description = "Verify products can be sorted Z to A")
    public void TC03_SortZtoA() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC03 - Sort Z to A")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.sortBy("za");
        List<String> names = inventoryPage.getProductNames();
        List<String> sorted = names.stream().sorted((a, b) -> b.compareTo(a)).toList();
        Assert.assertEquals(names, sorted, "Products should be sorted Z-A");
    }

    @Test(priority = 4, description = "Verify products sorted low to high price")
    public void TC04_SortPriceLowToHigh() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC04 - Sort Price Low to High")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.sortBy("lohi");
        List<Double> prices = inventoryPage.getProductPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                "Prices should increase: " + prices.get(i) + " > " + prices.get(i + 1));
        }
    }

    @Test(priority = 5, description = "Verify adding item updates cart badge")
    public void TC05_AddItemToCart() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC05 - Add Item to Cart")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.addItemToCartByIndex(0);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1",
            "Cart badge should show 1 after adding one item");
    }

    @Test(priority = 6, description = "Verify adding multiple items updates cart badge correctly")
    public void TC06_AddMultipleItemsToCart() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC06 - Add Multiple Items")
        );

        InventoryPage inventoryPage = new InventoryPage(DriverManager.getDriver());
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "2",
            "Cart badge should show 2 after adding two items");
    }
}
