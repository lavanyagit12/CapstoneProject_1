import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# Define the URL
url = "https://www.flipkart.com/"

# Define the search query
search_query = "Macbook air m2"

# Test function to implement the scenario
@pytest.mark.parametrize("product_name", [search_query])
def test_eCommerce_scenario(product_name):
    # Initialize the WebDriver
    driver = webdriver.Chrome()

    # Open the URL
    driver.get(url)
    driver.maximize_window()

    # Handle the login modal if it appears
    try:
        close_button = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//span[@role='button']"))
        )
        close_button.click()
    except:
        pass  # If the modal doesn't appear or the close button is not found, continue



    # Check if the "Flipkart" link is displayed
    flipkart_link = driver.find_element(By.XPATH, "//a[@title='Flipkart']")
    assert flipkart_link.is_displayed()

    # Find the search input element and enter the search query
    search_input = driver.find_element(By.NAME, "q")
    search_input.send_keys(product_name)
    search_input.submit()

    # Wait for the first displayed item and click on it
    first_item = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, "(//div[@class='_4rR01T'])[1]"))
    )
    first_item.click()
    

    
    # Switch to the new window
    original_window = driver.window_handles[0]  # Get the handle of the original window
    new_window = None

    # Wait for a new window to open and switch to it
    WebDriverWait(driver, 10).until(lambda driver: len(driver.window_handles) > 1)
    for window_handle in driver.window_handles:
        if window_handle != original_window:
            new_window = window_handle
            break

    driver.switch_to.window(new_window)  # Switch to the new window

    # Click on the "Add To Cart" button in the new window
    add_to_cart_button = WebDriverWait(driver, 20).until(
        EC.element_to_be_clickable((By.XPATH, "//button[normalize-space()='Add to cart']"))
    )
    driver.execute_script("arguments[0].click();", add_to_cart_button)
    #add_to_cart_button.click()

     # Handle the dynamic "Product is now out of stock" message
    try:
        out_of_stock_message = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//div[@class='_2sKwjB' and contains(text(), 'Product is now out of stock')]"))
        )
        assert "Product is now out of stock" in out_of_stock_message.text
    except:
        pass  # If the message doesn't appear, continue

    # Refresh the page
    driver.refresh()

        # Click on the cart icon to check if the product is visible
    cart_icon = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, "//a[@class='_3SkBxJ']"))
    )
    cart_icon.click()

    expected_product = 'APPLE 2022 MacBook AIR M2 - (8 GB/256 GB SSD/Mac OS Monterey) MLY...'

    # Verify that the added product is visible in the cart
    product_in_cart = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, "//a[contains(text(),'APPLE 2022 MacBook AIR M2 - (8 GB/256 GB SSD/Mac O')]"))
    )
    assert expected_product in product_in_cart.text

    # Close the WebDriver
    driver.quit()