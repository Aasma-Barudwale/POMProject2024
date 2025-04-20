package com.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;
import com.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest{

	@BeforeClass
	public void accPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@Test
	public void accPageTitleTest() {
		String title = accountsPage.getAccPageTitle();
		Assert.assertEquals(title, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}
	
	@Test
	public void accPageUrlTest() {
		String actUrl = accountsPage.getAccountPageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test
	public void isLogoutExistsTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExists());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccPageHeaderList = accountsPage.getAccountPageHeadersList();
		Assert.assertEquals(actualAccPageHeaderList.size(), AppConstants.ACCOUNT_PAGE_HEADERS_COUNT);
	}

	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccPageHeaderList = accountsPage.getAccountPageHeadersList();
		System.out.println("Actual Header List :" + actualAccPageHeaderList 
							+ "Expected Header List :" + AppConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccPageHeaderList, AppConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"},
		};
	}
	
	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accountsPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductsCount()>0);
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"Macbook", "MacBook Pro"},
			{"Macbook", "MacBook Air"},
			{"Macbook", "MacBook"},
			{"iMac", "iMac"},
			{"Apple", "Apple Cinema 30\""},
			{"Samsung", "Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"},
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void searchTest(String searchKey, String productName) {
		searchPage = accountsPage.performSearch(searchKey);
		if(searchPage.getSearchProductsCount()>0) {
		productInfoPage = searchPage.selectProduct(productName);
		String actProductHeader = productInfoPage.getProductHeaderValue();
		Assert.assertEquals(actProductHeader, productName);
		}
	}
}
