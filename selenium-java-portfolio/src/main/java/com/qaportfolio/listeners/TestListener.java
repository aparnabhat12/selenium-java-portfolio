package com.qaportfolio.listeners;

import com.qaportfolio.utils.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) testName = result.getName();

        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest(testName)
        );
        ExtentReportManager.getTest().info("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass("Test PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().fail("Test FAILED: " + result.getName());
        ExtentReportManager.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test SKIPPED: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
    }
}
