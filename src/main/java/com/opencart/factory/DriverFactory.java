package com.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.opencart.exception.FrameworkException;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static String highlight;
	
	public WebDriver initDriver(Properties prop) {
					
		optionsManager = new OptionsManager(prop);
		highlight =  prop.getProperty("highlight").trim();
		
		String browserName = prop.getProperty("browser").toLowerCase().trim();
		System.out.println("Browser Name is :" + browserName);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				//Remote execution
				init_remoteDriver("chrome");
			}else {
				//Local Execution
				//driver = new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			}else if(browserName.equalsIgnoreCase("edge")) {
				if(Boolean.parseBoolean(prop.getProperty("remote")))
				{
					init_remoteDriver("edge");
				}else {
					tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				}
		}/*
				 * else if(browserName.equalsIgnoreCase("firefox")) {
				 * if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				 * init_remoteDriver("firefox"); }else { tlDriver.set(new
				 * FirefoxDriver(optionsManager.getFirefoxOptions())); }
				 * 
				 * }
				 */else {
			System.out.println("Please pass right browser name..." + browserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}

	
	private void init_remoteDriver(String browser) {
		
		System.out.println("Running test on grid server::::: " + browser);
		try {
		switch (browser) {
		case "chrome":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));		
			break;
		case "firefox":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			break;
		case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Please pass right browser for remot execution " +browser);
			throw new FrameworkException("NOREMOTEBROWSEREXCEPTION");
		}
		}
		 catch (MalformedURLException e) {
			e.printStackTrace();
		}	
	}

	/*
	 * get local thread copy of the driver
	 */
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		//mvn clean install -Denv="stage"
				String envName =  System.getProperty("env");
				System.out.println("Running testcases on : " + envName);
			
				try {
				if(envName ==  null) {
					System.out.println("No env passed...running testcases on QA env");
					 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				}else {
					switch (envName.toLowerCase().trim()) {
					case "qa":
						 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
						break;
					case "dev":
						 ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
						break;
					case "stage":
						 ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
						break;
					case "prod":
						 ip = new FileInputStream("./src/test/resources/config/config.properties");
						break;
					default:
						System.out.println("Wrong env passed...No need to run testcases");
						throw new FrameworkException("WRONG ENV PASSED...");
						//break;
					}
				}
				
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}

	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		Path path = Paths.get(System.getProperty("user.dir")+ "/screenshot/") ;
		String fileName = System.getProperty("user.dir")+ "/screenshot/" + System.currentTimeMillis()+ ".png";
		File destFile = new File(fileName);
		
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// fail to create directory
				e.printStackTrace();
			}
		}
		try {
			FileUtil.copyDir(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileName;
	}
}
