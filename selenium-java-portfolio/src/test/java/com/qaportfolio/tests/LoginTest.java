package com.qaportfolio.tests;

import com.qaportfolio.base.BaseTest;
import com.qaportfolio.pages.LoginPage;
import com.qaportfolio.utils.ConfigReader;
import com.qaportfolio.utils.DriverManager;
import com.qaportfolio.utils.ExcelReader;
import com.qaportfolio.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // ─── Data Driven Testing from Excel ───────────────────────────────────────
    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        ExcelReader excel = new ExcelReader(
            ConfigReader.getTestDataPath(), "LoginData"
        );
        return excel.getAllData();
    }

    // ─── Test Cases ───────────────────────────────────────────────────────────

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void TC01_ValidLogin() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC01 - Valid Login")
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"),
            "Should redirect to inventory after login");

        ExtentReportManager.getTest().info("Logged in successfully. URL: " + currentUrl);
    }

    @Test(priority = 2, description = "Verify login fails with wrong password")
    public void TC02_InvalidPassword() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC02 - Invalid Password")
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"),
            "Error should mention credentials mismatch");
    }

    @Test(priority = 3, description = "Verify login fails with empty username")
    public void TC03_EmptyUsername() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC03 - Empty Username")
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login("", "secret_sauce");

        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(priority = 4, description = "Verify locked out user cannot login")
    public void TC04_LockedOutUser() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC04 - Locked Out User")
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @Test(priority = 5, description = "Verify login fails with empty password")
    public void TC05_EmptyPassword() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC05 - Empty Password")
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login("standard_user", "");

        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }

    @Test(priority = 6, description = "Verify login button is visible on page load")
    public void TC06_LoginPageLoads() {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest("TC06 - Login Page Load")
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible");
    }

    @Test(priority = 7, dataProvider = "loginData",
          description = "Data Driven Test - login with multiple credentials from Excel")
    public void TC07_DDT_LoginWithExcelData(String username, String password, String expectedResult) {
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest(
                "TC07 - DDT Login [" + username + "]"
            )
        );

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("pass")) {
            Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("inventory"),
                "Expected successful login for: " + username);
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Expected login failure for: " + username);
        }
    }
}
