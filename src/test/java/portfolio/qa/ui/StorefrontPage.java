package portfolio.qa.ui;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

final class StorefrontPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    StorefrontPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    void open(String fixtureUrl) {
        driver.get(fixtureUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='product-grid']")));
    }

    void search(String text) {
        WebElement search = driver.findElement(By.cssSelector("[data-testid='product-search']"));
        search.clear();
        search.sendKeys(text);
    }

    List<WebElement> visibleProducts() {
        return driver.findElements(By.cssSelector(".product-card"));
    }

    void addToBag(String productId) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-testid='add-to-cart-" + productId + "']"))).click();
    }

    String cartCount() {
        return driver.findElement(By.cssSelector("[data-testid='cart-count']")).getText();
    }

    String cartTotal() {
        return driver.findElement(By.cssSelector("[data-testid='cart-total']")).getText();
    }

    String cartItem(String productId) {
        return driver.findElement(By.cssSelector("[data-testid='cart-item-" + productId + "']")).getText();
    }

    String notice() {
        return driver.findElement(By.id("notice")).getText();
    }

    void placeDemoOrder(String name, String email) {
        driver.findElement(By.id("customer-name")).sendKeys(name);
        driver.findElement(By.id("customer-email")).sendKeys(email);
        driver.findElement(By.id("checkout-button")).click();
    }

    String checkoutMessage() {
        return driver.findElement(By.id("checkout-message")).getText();
    }
}
