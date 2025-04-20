package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.constants.AppConstants;
import com.opencart.utils.ElementUtil;

public class SearchPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By searchProductResults = By.cssSelector("div#content div.product-layout");

	public SearchPage(WebDriver driver) {
		super();
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	public int getSearchProductsCount() {
		int prodCount =  elementUtil.waitForElementsVisible(searchProductResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Product Count is : " + prodCount);
		return prodCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		By productLocator = By.linkText(productName);
		
		elementUtil.waitForElementVisible(productLocator, AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();
		return new ProductInfoPage(driver);
	}

}
