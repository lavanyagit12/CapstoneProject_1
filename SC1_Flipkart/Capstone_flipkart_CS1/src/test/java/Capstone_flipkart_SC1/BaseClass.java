package Capstone_flipkart_SC1;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class BaseClass {

	
	WebDriver driver;
	WebDriverWait wait;
    String baseurl;
    FlipkartPageElements FlipkartPageElements;
    
    @BeforeSuite 
    @Parameters ("baseurl")
    public void SetUp(String baseurl) {
    	//Initialize webdriver
    	System.setProperty("WebDriver.chrome.driver", "C:\\Users\\Administrator\\Downloads\\chromedriver-win64\\chromedriver.exe");
    	driver = new ChromeDriver();
    	System.out.println("chrome Browser opened");
    	wait =  new WebDriverWait(driver, Duration.ofSeconds(60));
    	driver.manage().window().maximize();
    	System.out.println("Browser maximized");
    	
    	//set the base url
    	this.baseurl = baseurl;
    	FlipkartPageElements = new FlipkartPageElements(driver);
    	driver.get(baseurl);
    	System.out.println("url opened");
    	}

    //tear down method   
    @AfterSuite
        public void tearDown() {
    	   if (driver!= null ){
    		   driver.quit();
    		   System.out.println("Browser closed");
    		   
    	   }
     	   
       }
       
}
