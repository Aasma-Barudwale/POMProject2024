package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.constants.AppConstants;
import com.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	// 1. private By locators
	By emailId = By.id("input-email");
	By password = By.id("input-password");
	By loginbtn = By.xpath("//input[@value = \"Login\"]");
	By forgotPwdLink = By.linkText("Forgotten Password");
	By registerLink = By.linkText("Register");
	
	//2. page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	//3. page actions/methods
	@Step("getting title of page")
	public String getLoginPageTitle() {
		String title = elementUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login Page Title is : " + title);
		return title;
	}
	
	@Step("getting url of page")
	public String getLoginPageURL() {
		String url = elementUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login Page url is : " + url);
		return url;
	}
	
	@Step("checking forgot pwd link")
	public boolean isForwardPwdLinkExists() {
		return elementUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		elementUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(userName);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginbtn);
		return new AccountsPage(driver);
	}
	
	@Step("Navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
