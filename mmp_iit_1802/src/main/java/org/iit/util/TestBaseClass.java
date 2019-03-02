package org.iit.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
public class TestBaseClass {
	public static WebDriver driver=null;
	
	@BeforeClass
	public void invokeBrowser()
	{
 	    WebDriverManager.chromedriver().setup();
 		driver = new ChromeDriver();
 		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	@AfterClass
	public static void quitDriver() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
	}
	

}
