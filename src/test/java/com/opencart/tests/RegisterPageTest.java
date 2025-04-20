package com.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;
import com.opencart.constants.AppConstants;
import com.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{

	@BeforeClass
	public void regPageSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmail() {
		String email = "automation" + System.currentTimeMillis() + "@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegTestData() {
		Object [][] regData = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	@Test(dataProvider = "getRegTestData")
	public void userRegTest(String firstName, String lastName, String email, 
			String telephone, String password, String subscribe) {
		
		Assert.assertTrue(registerPage.registerUser(firstName, lastName, 
				getRandomEmail(), telephone, password, subscribe));
		
	}
}
