package com.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.constants.AppConstants;
import com.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private ElementUtil elementUtil;
	
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData =  By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricingData =  By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity"); 
	private By addToCartBtn = By.id("button-cart"); 
	private By cartSuccessMsg = By.cssSelector("div.alert.alert-success");
	
	private Map<String, String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		super();
		elementUtil = new ElementUtil(driver);
	}
	
	public String getProductHeaderValue() {
		String prodHeaderValue = elementUtil.doElementGetText(productHeader);
		System.out.println("Product header is : " + prodHeaderValue);
		return prodHeaderValue;
	}
	
	public int getProductImagesCount() {
		int imagesCount = elementUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Product Images count is : " + imagesCount);
		return imagesCount;
	}
	
	public Map<String, String> getProductInfo() {
		productInfoMap = new LinkedHashMap<String, String>();
		
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		
		return productInfoMap;
		}
	
	private void getProductMetaData() {
		List<WebElement> metaList = elementUtil.getElements(productMetaData);
		for (WebElement webElement : metaList) {
			String meta = webElement.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
	
	private void getProductPriceData() {
		List<WebElement> priceList = elementUtil.getElements(productPricingData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();
		
		productInfoMap.put("productprice", price);
		productInfoMap.put("extax", exTaxVal);
	}
	
	public void enterQuantity(int qty) {
		elementUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	
	public String addProductToCart() {
		elementUtil.doClick(addToCartBtn);
		String successMsg = elementUtil.waitForElementVisible(cartSuccessMsg, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		System.out.println("Cart sucess msg is : " + successMsg);
		return successMsg.substring(0, successMsg.length()-1).trim();
	}
}
 