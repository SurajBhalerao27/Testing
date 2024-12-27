package com.aepl.tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.ChangeMobilePage;
import com.aepl.pages.LoginPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class ChangeMobilePageTest extends TestBase {
	private LoginPage loginPage;
	private ExcelUtility excelUtility;
	private ChangeMobilePage changeMobile;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(driver);
		changeMobile = new ChangeMobilePage(driver);
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("Change_Mobile_Test");
	}

	@Test(priority = 1)
	public void testLogin() {
		String testCaseName = "Login Test In Change Mobile";
		try {
			logger.info("Executing Login: " + testCaseName);
			loginPage.enterUsername("suraj.bhalerao@accoladeelectronics.com").enterPassword("cqf9tnvl").clickLogin();
		}catch(Exception e) {
			e.printStackTrace();
			captureScreenshot(testCaseName);
		}
	}

	@Test(priority = 2)
	public void testClickNavBar() {
		String testCaseName = "Test Navbar Links";
		String expectedURL = ConfigProperties.getProperty("dashboard.url");
		String actualURL = "";
		String result;
		logger.info("Executing the testClickNavBar");
		try {
			logger.info("Trying to click on the nav bar links");
			changeMobile.clickNavBar();
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		} catch (Exception e) {
			logger.warn("Error");
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Successfully clicked on the Device Utility.");
	}

	@Test(priority = 3)
	public void testClickOnChangeMobile() {
		String testCaseName = "Test Change Mobile Link";
		String expectedURL = ConfigProperties.getProperty("change.mobile");
		String result = "";

		logger.info("Executing test case: {}", testCaseName);

		try {
			String actualURL = changeMobile.clickDropDownOption();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Test case '{}' completed successfully. Expected URL: {}, Actual URL: {}", testCaseName,
					expectedURL, actualURL);
		} catch (Exception e) {
			logger.error("Error encountered in test case '{}'.", testCaseName, e);
			captureScreenshot(testCaseName);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL,
					result.equals("PASS") ? expectedURL : "Error occurred", result);
		}
	}

	@Test(priority = 4)
	public void testSearchBoxFunction() {
		String testCaseName = "Testing the search box functionality";
		String iccidToSearch = "89916490634626389138"; // ICCID entered for the test case
		List<String> expectedHeaders = Arrays.asList("Lot Id", "ICCID", "Mobile 1", "Mobile 2", "Service Provider 1",
				"Service Provider 2", "Action");

		logger.info("Executing test case: {}", testCaseName);

		String result = "";
		try {
			boolean headersMatch = changeMobile.checkSearchBox(iccidToSearch, expectedHeaders);
			result = headersMatch ? "PASS" : "FAIL";
			logger.info("Search functionality executed. Headers match status: {}", headersMatch);
		} catch (Exception e) {
			logger.error("Error encountered in test case: {}", testCaseName, e);
			captureScreenshot(testCaseName);
			result = "FAIL";
		} finally {
			// Writing results to Excel
			excelUtility.writeTestDataToExcel(testCaseName, String.join(", ", expectedHeaders),
					result.equals("PASS") ? "Headers Match" : "Headers Mismatch", result);
		}
	}

	@Test(priority = 5)
	public void testActionButtons() {
			changeMobile.clickEyeActionButton();
	}

}