package com.qaportfolio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        return pageTitle.getText().equals("Products");
    }

    public int getProductCount() {
        return inventoryItems.size();
    }

    public void sortBy(String value) {
        Select select = new Select(sortDropdown);
        select.selectByValue(value);
    }

    public List<String> getProductNames() {
        return driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return driver.findElements(By.className("inventory_item_price"))
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public void addItemToCartByIndex(int index) {
        List<WebElement> addButtons = driver.findElements(
            By.cssSelector("button[data-test^='add-to-cart']")
        );
        addButtons.get(index).click();
    }

    public String getCartBadgeCount() {
        wait.until(ExpectedConditions.visibilityOf(cartBadge));
        return cartBadge.getText();
    }

    public void goToCart() {
        cartIcon.click();
    }
}
