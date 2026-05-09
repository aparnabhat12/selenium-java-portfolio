package com.qaportfolio.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qaportfolio.utils.ConfigReader;
import com.qaportfolio.utils.DriverManager;
import com.qaportfolio.utils.ExtentReportManager;
import com.qaportfolio.utils.ScreenshotUtil;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {

    @BeforeSuite
    public void beforeSuite() {
        ExtentReportManager.getInstance();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        // Allow override from testng.xml parameter, else use config
        String browserToUse = (browser != null && !browser.isEmpty())
                ? browser
                : ConfigReader.getBrowser();

        DriverManager.initDriver(browserToUse);
        DriverManager.getDriver().get(ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot = ScreenshotUtil.capture(
                DriverManager.getDriver(),
                result.getName()
            );
            if (test != null) {
                test.fail(result.getThrowable());
                test.addScreenCaptureFromPath("../../" + screenshot, "Failure Screenshot");
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            if (test != null) test.log(Status.PASS, "Test Passed");
        } else {
            if (test != null) test.log(Status.SKIP, "Test Skipped");
        }

        DriverManager.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flush();
    }
}
