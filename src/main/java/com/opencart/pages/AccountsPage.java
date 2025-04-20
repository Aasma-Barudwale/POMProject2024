package com.opencart.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.constants.AppConstants;
import com.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By accHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");
	
	public AccountsPage(WebDriver driver) {
		super();
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		String title = elementUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
		System.out.println("Account Page Title is : " + title);
		return title;
	}
	
	public String getAccountPageURL() {
		String url = elementUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
		System.out.println("Account Page url is : " + url);
		return url;
	}
	
	public boolean isLogoutLinkExists() {
		return elementUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public boolean isSearchExists() {
		return elementUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountPageHeadersList() {
		List<WebElement> accHeadersList = elementUtil.waitForElementsVisible(accHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> accHeadersValList = new ArrayList<String>();
		for (WebElement value : accHeadersList) {
			String text = value.getText();
			accHeadersValList.add(text);
			}
		return accHeadersValList;
	}

	public SearchPage performSearch(String searchKey) {
		if(isSearchExists()) {
			elementUtil.doSendKeys(search, searchKey);
			elementUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}else {
			System.out.println("Search field not present on the page...");
			return null;
		}
	}
}
