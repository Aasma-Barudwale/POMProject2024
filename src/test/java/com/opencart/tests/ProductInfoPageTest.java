package com.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{

	@BeforeClass
	public void ProdInfoPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData(){
		return new Object[][] {
				/*
				 * {"Macbook", "MacBook Pro", 4}, {"iMac", "iMac", 3}, {"Apple",
				 * "Apple Cinema 30\"", 6},
				 */
			{"Samsung", "Samsung SyncMaster 941BW", 1},
		};
	}
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	@DataProvider
	public Object[][] getProductInfoTestData(){
		return new Object[][] {
			{"Macbook", "MacBook Pro", "Apple", "Product 18", "800", "Out Of Stock", "$2,000.00"},
				/*
				 * {"Macbook", "MacBook Air", "Apple", "Product 17", "700", "Out Of Stock",
				 * "$1,202.00"},
				 * 
				 * {"Macbook", "MacBook", "Apple", "Product 16", "600", "In Stock", "$602.00"},
				 * {"iMac", "iMac", "Apple", "Product 14", null, "Out Of Stock", "$122.00"},
				 * {"Samsung", "Samsung SyncMaster 941BW", null, "Product 6", null, "2-3 Days",
				 * "$242.00"},
				 * 
				 * {"Samsung", "Samsung Galaxy Tab 10.1", null, "SAM1", "1000", "Pre-Order",
				 * "$241.99"},
				 */
		};
	}
	
	@Test(dataProvider = "getProductInfoTestData")
	public void productInfoTest(String searchKey, String productName, String brand, 
			String productCode, String rewardPoints, String availability, String productPrice) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		System.out.println("Actual ProductInfoMap is : " + actProductInfoMap);
		softAssert.assertEquals(actProductInfoMap.get("Brand"), brand);
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), productCode);
		softAssert.assertEquals(actProductInfoMap.get("Reward Points"), rewardPoints);
		softAssert.assertEquals(actProductInfoMap.get("Availability"), availability);
		softAssert.assertEquals(actProductInfoMap.get("productname"), productName);	
		softAssert.assertEquals(actProductInfoMap.get("productprice"), productPrice);
		 			
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] getCartTestData(){
		return new Object[][] {
			{"Macbook", "MacBook Pro", 2},
				/*
				 * {"Macbook", "MacBook Air", 3}, {"Macbook", "MacBook", 1}, {"iMac", "iMac",
				 * 2}, {"Samsung", "Samsung SyncMaster 941BW", 3}, {"Samsung",
				 * "Samsung Galaxy Tab 10.1", 2},
				 */
		};
	}
	
	@Test(dataProvider = "getCartTestData")
	public void addToCartTest(String searchKey, String productName, int qty) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(qty);
		String actSuccessMsg = productInfoPage.addProductToCart();

		softAssert.assertTrue(actSuccessMsg.contains("Success"));
		softAssert.assertTrue(actSuccessMsg.contains(productName));
		softAssert.assertEquals(actSuccessMsg, "Success: You have added " + productName + " to your shopping cart!");
		softAssert.assertAll();
	}
}
