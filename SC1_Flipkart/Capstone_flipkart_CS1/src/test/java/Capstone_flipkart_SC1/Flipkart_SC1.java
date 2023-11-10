package Capstone_flipkart_SC1;

import org.testng.annotations.Test;

public class Flipkart_SC1 extends BaseClass {

   // Flipkart Logo Verification
	@Test(priority =1)	
	public void flipkartLogoVerification() {
		
		FlipkartPageElements.crossButton();
		FlipkartPageElements.logoVerification();
	}
	
	//Searching for the product and clicking on the first dispayed product
	@Test(priority =2)
	public void searchForTheProduct() {
		
		FlipkartPageElements.SearchItem("Macbook air m2");
		FlipkartPageElements.clickFirstDisplayedResult();
	}
	
	//Adding product to cart and Verifying the product in the cart
	@Test(priority =3)
	public void addingProductToCart() throws InterruptedException {
		
		FlipkartPageElements.addtocart();
		FlipkartPageElements.handleoutofstock();
		FlipkartPageElements.clickcartButton();
	}
	
}
