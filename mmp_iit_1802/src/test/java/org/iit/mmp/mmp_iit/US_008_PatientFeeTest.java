package org.iit.mmp.mmp_iit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.iit.util.TestBaseClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
 * 8. Generate a fee for the patient is an input Data(login to admin module and fill the data).
   Execute the testcases to pay the fees.
 * 
 */

import io.github.bonigarcia.wdm.WebDriverManager;

public class US_008_PatientFeeTest extends TestBaseClass{
	
	
//	public static String adminUrl="http://96.84.175.78/MMP-Release2-Admin-Build.2.1.000/index.php";
//	public static String patientUrl="http://96.84.175.78/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";
	public static LocalDate serviceDate;
	public static String serviceType="Xray";
	public static String dateString;
	
	
	@BeforeTest
	public void beforeTest() {
		
		DateTimeFormatter shortDateTime = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
		serviceDate =LocalDate.of(2018, 1, 23);
		dateString=shortDateTime.format(serviceDate);
		System.out.println("Date to be chosen= "+dateString);
	}
	
	@Parameters({"adminUrl","adminUsername","adminPassword"})
	@Test(priority=1)
	public void adminFeeTest(String adminUrl,String adminUsername,String adminPassword) {
				System.out.println(adminUrl+adminUsername+adminPassword);
		Assert.assertEquals(createFee(adminUrl, adminUsername, adminPassword), "Fee Successfully Entered. ");
		
	}
	
	@Parameters({"url","username","password"})
	@Test(priority=2)
	public void patientFeeTest(String url,String username ,String password) {
			
		Assert.assertTrue(isFeeRecorded(url, username ,password));
	}
	
	
	@AfterTest
	public void afterTest() throws InterruptedException {
		quitDriver();
	}
	
	
	public static void quitDriver() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
	}
	public static String createFee(String adminUrl,String adminUsername,String adminPassword) {
		
		driver.get(adminUrl); 
		driver.findElement(By.xpath("//input[@placeholder='username']")).sendKeys(adminUsername);
		 driver.findElement(By.xpath("//input[@id='password']")).sendKeys(adminPassword);
		 driver.findElement(By.xpath("//input[@value='Sign In']")).click();
		 driver.findElement(By.xpath("//span[contains(text(),'Patients')]")).click();
		 driver.findElement(By.xpath("//input[@id='search']")).sendKeys("510888555");
		 driver.findElement(By.xpath("//input[@value='search']")).click();
		 driver.findElement(By.xpath("//a[contains(text(),'ria')]")).click();
		 driver.findElement(By.xpath("//input[@value='Create Fee']")).click();
		 Select select=new Select(driver.findElement(By.xpath("//select[@id='app_date']")));
		 select.selectByVisibleText(dateString);
		 
		 select=new Select(driver.findElement(By.xpath("//select[@id='service']")));
		 select.selectByVisibleText(serviceType);
		 driver.findElement(By.xpath("//input[@value='submit']")).click();
		 String returnText=driver.switchTo().alert().getText();
		 driver.switchTo().alert().dismiss();
		return returnText;
		
	}
	public static boolean isFeeRecorded(String url,String username ,String password) {
		driver.get(url);
		driver.findElement(By.xpath("//input[@placeholder='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Sign In']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Fees')]")).click();
		
		List<WebElement> listItems = driver.findElements(By.xpath("//li[@style=' margin-left:30px;']"));
		
		for (WebElement list:listItems) {
			if(list.getText().contains(serviceType)&&list.getText().contains(dateString)) {
				return true;
			}
			
		}
		
		return false;
		
	}
	

}
