package Capstone_flipkart_SC1;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipkartPageElements {
		
		WebDriver driver;
		
		WebDriverWait wait;
		
		@FindBy (xpath = "//span[@role='button']")
		private WebElement alertCrossButton;
		
		@FindBy (xpath = "//img[@title='Flipkart']")
		private WebElement flipkartLogo;
		
		@FindBy (xpath = "//input[@placeholder='Search for Products, Brands and More']")
		private WebElement searchBox;
		
		@FindBy (xpath = "//button[@title='Search for Products, Brands and More']")
		private WebElement searchButton;
		
		@FindBy (xpath = "(//div[@class='_2kHMtA'])[1]")
		private WebElement firstResultDisplayed;
		
		@FindBy (xpath ="//button[normalize-space()='Add to cart']")
		private WebElement addToCartButton;
		
		@FindBy (xpath = "//span[normalize-space()='Cart']")
		private WebElement cartButton;
		
		@FindBy (xpath = "//a[contains(text(),'APPLE 2022 MacBook AIR M2 - (8 GB/256 GB SSD/Mac O')]")
		private WebElement cartProduct;
		
		@FindBy (xpath = "//div[@class='_2sKwjB' and contains(text(), 'Product is now out of stock')]")
		private WebElement outofstockmessage;
		
		//Handling unexpected login window
		public void crossButton() {
			alertCrossButton.click();
		}
		
		//Flipkart Logo Verification
		public void logoVerification() {
			
			if(flipkartLogo.isDisplayed()) {
				System.out.println("Flipkart Logo is Displayed");
			}
			else {
				System.out.println("Flipkart Logo is not Displayed");
			}
				
		}
		
		//Searching the given product
		 public void SearchItem (String Searchtext) {
			 searchBox.sendKeys(Searchtext);
			 System.out.println("Searchtext entered");
			 searchButton.click();
			 System.out.println("clicked on search Button");
		 }
		 
		 //Clicking on first displayed product
		 public void clickFirstDisplayedResult() {
			 //System.out.println(firstResultDisplayed.getText()+"is the first displayed product");
			 firstResultDisplayed.click();
		 }
		 
		 //Handling windows and adding product to the cart
		 public void addtocart() throws InterruptedException {
			 System.out.println(driver.getTitle());
			 String currentwindowhandle = driver.getWindowHandle();
			 Set<String> windowhandles = driver.getWindowHandles();
			 for(String windowhandle : windowhandles) {
				 if (!windowhandle.equals(currentwindowhandle)) {
					 driver.switchTo().window(windowhandle);
					 break;
				 }
			 } 
			 System.out.println(driver.getTitle()); 
			 WebElement myElement = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(addToCartButton));
			 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", myElement);
			 Actions a=new Actions(driver);
			 System.out.println(addToCartButton.isEnabled());
			 a.moveToElement(myElement).click().build().perform();
			 System.out.println("Add to cart Button clicked");
			 Thread.sleep(3000);
			 //Refreshing the Browser
		     driver.navigate().refresh();
		    }
	  
		 public void handleoutofstock() {
		       // Handle the dynamic "Product is now out of stock" message
		        try {
		            WebElement outOfStockMessage = wait.until(
		                    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='_2sKwjB' and contains(text(), 'Product is now out of stock')]"))
		            );
		            assert outOfStockMessage.getText().contains("Product is now out of stock");
		        } catch (Exception e) {
		            // If the message doesn't appear, continue
		        }

		        driver.navigate().refresh();
		 }
		 
		 
		 //Clicking on the cart Button
		 public void clickcartButton() {
			cartButton.click();
			 System.out.println("cart Button clicked");
			 // Verify that the added product is visible in the cart
		        WebElement productInCart = wait.until(
		                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'APPLE 2022 MacBook AIR M2 - (8 GB/256 GB SSD/Mac O')]"))
		        );
		        System.out.println(productInCart.getText());
		        assert productInCart.getText().contains("APPLE 2022 MacBook AIR M2 - (8 GB/256 GB SSD/Mac OS Monterey) MLY33HN/A");
		        System.out.println("product has been added successfully to the cart");
		 }


      public FlipkartPageElements(WebDriver driver) {
    	  this.driver = driver;
    	  this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	  PageFactory.initElements(driver, this);
      }


}
