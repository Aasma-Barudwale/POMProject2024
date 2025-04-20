package com.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;
import com.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Epic("EPIC - 100: Design login for open cart app")
@Story("US Login: 101: design login page feature for open cart")
public class LoginPageTest extends BaseTest{
	
	@Severity(SeverityLevel.TRIVIAL)
	@Description("Getting title of the page")
	@Test
	public void loginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}

	@Severity(SeverityLevel.NORMAL)
	@Description("Getting Url of the page")
	@Test
	public void loginPageUrlTest() {
		String url = loginPage.getLoginPageURL();
		Assert.assertTrue(url.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Clicking on forgot password link")
	@Test
	public void forgotPwdLinkExistsTest() {
		Assert.assertTrue(loginPage.isForwardPwdLinkExists());
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("Checking if user is able to login with correct credentials")
	@Test(priority = 1)
	public void doLoginTest() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accountsPage.isLogoutLinkExists());
	}
}
