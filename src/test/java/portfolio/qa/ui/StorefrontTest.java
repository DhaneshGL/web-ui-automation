package portfolio.qa.ui;

import java.nio.file.Path;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StorefrontTest {
    private WebDriver driver;
    private StorefrontPage storefront;

    @BeforeMethod
    public void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000");
        if (Boolean.parseBoolean(System.getProperty("ui.headless", "true"))) {
            options.addArguments("--headless=new");
        }
        driver = new ChromeDriver(options);
        storefront = new StorefrontPage(driver);
        Path fixture = Path.of("src", "test", "resources", "storefront", "index.html").toAbsolutePath();
        storefront.open(fixture.toUri().toString());
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "UI-001: catalog displays all seeded products and prices")
    public void catalogShowsSeededProducts() {
        Assert.assertEquals(storefront.visibleProducts().size(), 4, "Catalog should show four dummy products");
        Assert.assertTrue(driver.getPageSource().contains("Everyday Bottle"));
        Assert.assertTrue(driver.getPageSource().contains("$28.00"));
    }

    @Test(description = "UI-002: search filters the catalog and empty results are clear")
    public void searchFiltersCatalog() {
        storefront.search("bottle");
        Assert.assertEquals(storefront.visibleProducts().size(), 1);
        Assert.assertTrue(storefront.visibleProducts().get(0).getText().contains("Everyday Bottle"));

        storefront.search("not-a-product");
        Assert.assertEquals(storefront.visibleProducts().size(), 0);
        Assert.assertEquals(storefront.notice(), "No products match your search.");
    }

    @Test(description = "UI-003: adding the same product twice updates quantity and total")
    public void addingProductUpdatesCartQuantityAndTotal() {
        storefront.addToBag("bottle");
        storefront.addToBag("bottle");

        Assert.assertEquals(storefront.cartCount(), "2");
        Assert.assertEquals(storefront.cartTotal(), "$56.00");
        Assert.assertTrue(storefront.cartItem("bottle").contains("Everyday Bottle × 2"));
    }

    @Test(description = "UI-004: cart total sums different products correctly")
    public void cartSumsDifferentProducts() {
        storefront.addToBag("bottle");
        storefront.addToBag("soap");

        Assert.assertEquals(storefront.cartCount(), "2");
        Assert.assertEquals(storefront.cartTotal(), "$40.00");
    }

    @Test(description = "UI-005: successful checkout confirms the demo order and empties the cart")
    public void checkoutConfirmsAndClearsCart() {
        storefront.addToBag("tote");
        storefront.placeDemoOrder("Jordan Lee", "jordan@example.test");

        Assert.assertEquals(storefront.checkoutMessage(), "Thanks, Jordan Lee! Your demo order is confirmed.");
        Assert.assertEquals(storefront.cartCount(), "0");
        Assert.assertEquals(storefront.cartTotal(), "$0.00");
    }

    @Test(description = "UI-006: browser-native required-field validation blocks empty checkout")
    public void checkoutRequiresCustomerDetails() {
        storefront.addToBag("mug");
        driver.findElement(org.openqa.selenium.By.id("checkout-button")).click();

        Assert.assertTrue(driver.findElement(org.openqa.selenium.By.id("customer-name")).getAttribute("required") != null);
        Assert.assertEquals(storefront.checkoutMessage(), "");
        List<?> cartItems = driver.findElements(org.openqa.selenium.By.cssSelector("[data-testid^='cart-item-']"));
        Assert.assertEquals(cartItems.size(), 1, "Invalid checkout must retain the cart contents");
    }
}
