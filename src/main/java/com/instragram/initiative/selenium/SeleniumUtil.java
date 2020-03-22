package com.instragram.initiative.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtil {
	
	private WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}

	public SeleniumUtil(String driverName,String driverLocation)
	{   System.setProperty(driverName, driverLocation);
		this.driver=new ChromeDriver();
		
	}
	
	private WebElement waitForElement(String xpath ,int timeInseconds) {
		WebDriverWait wait=new WebDriverWait(driver, timeInseconds);
		WebElement returnElement=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		return returnElement;
	}
	public void openUrl(String url)
	{	
		driver.get(url);
		
	}
	
	public boolean readTest(String xpath,String expectedOutput) {
		 
		  WebElement fName=driver.findElement(By.xpath(xpath));
		  return fName.getText().equalsIgnoreCase(expectedOutput);
		 
	}
	public void clickTest(String xpath) {
		  WebElement fName=waitForElement(xpath,10);
		  fName.click();
		  
	}
	
	public String getElementAttribute(String xpath,String string) {
		  WebElement fName=waitForElement(xpath,10);
		  return fName.getAttribute("aria-label");
		  
		  
	}
	
	
	public void writeTest(String xpath,String string) {
		  WebElement fName=waitForElement(xpath,10);
		  fName.sendKeys(string);
	}
	
	
	
	
	
}