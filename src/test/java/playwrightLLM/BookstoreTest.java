package playwrightLLM;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookstoreTest {

    static Playwright playwright;
    static Browser browser;
    static BrowserContext context;
    static Page page;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true));
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720));
        page = context.newPage();
        page.setViewportSize(1280, 720);
        page.navigate("https://depaul.bncollege.com/");
        page.waitForTimeout(2000);
    }

    @AfterAll
    static void teardown() {
        context.close();
        browser.close();
        playwright.close();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 1: Search, filter, assert product details, add to cart
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(1)
    @DisplayName("TestCase 1: Bookstore – Search, Filter, Product Assertions, Add to Cart")
    void testCaseBookstore() {
        // Enter "earbuds" in search box and press Enter
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
        page.waitForTimeout(2000);

        // Expand "Brand" filter and select "JBL"
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.waitForTimeout(500);
        page.locator("label").filter(new Locator.FilterOptions().setHasText("JBL")).first().click();
        page.waitForTimeout(2000);

        // Expand "Color" filter and select "Black"
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.waitForTimeout(500);
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Black")).first().click();
        page.waitForTimeout(2000);

        // Expand "Price" filter and select "Over $50"
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.waitForTimeout(500);
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Over $50")).first().click();
        page.waitForTimeout(2000);

        // Click the JBL Quantum True Wireless product link
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.waitForTimeout(3000);

        // Assert product name
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("JBL Quantum True Wireless"))).isVisible();

        // Assert SKU number
        assertThat(page.getByText("668972707").nth(1)).isVisible();


        // Assert price
        assertThat(page.getByText("$164.98").first()).isVisible();

        // Assert product description contains noise-cancelling text
        assertThat(page.getByText("Adaptive noise cancelling").first()).isVisible();

        // Add 1 to cart
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
        page.waitForTimeout(3000);

        // Assert "1 Items" reflected in cart icon (upper-right)
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Cart 1 items"))).isVisible();

        // Click Cart icon
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 2: Your Shopping Cart Page
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(2)
    @DisplayName("TestCase 2: Your Shopping Cart Page")
    void testCaseShoppingCart() {
        // Assert "Your Shopping Cart" heading
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Your Shopping Cart"))).isVisible();

        // Assert product name, quantity (1), and price ($149.98)
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("JBL Quantum True Wireless"))).isVisible();
        assertThat(page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Quantity, edit and press"))).isVisible();
        assertThat(page.getByText("$164.98").first()).isVisible();

        // Select "FAST In-Store Pickup" (may already be selected)
        page.getByText("FAST In-Store Pickup").first().click();
        page.waitForTimeout(2000);

        // Cart page shows subtotal and TBD taxes; handling fee appears only in checkout sidebar
        assertThat(page.getByText("164.98").first()).isVisible();
        assertThat(page.getByText("TBD").first()).isVisible();

        // Enter promo code TEST and click APPLY
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
        page.waitForTimeout(3000);

        // Assert promo code rejection message is displayed
        assertThat(page.getByText("The coupon code entered is").first()).isVisible();

        // Click "PROCEED TO CHECKOUT"
        page.getByLabel("Proceed To Checkout").click();
        page.waitForTimeout(2000);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 3: Create Account Page
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(3)
    @DisplayName("TestCase 3: Create Account Page")
    void testCaseCreateAccount() {
        // Assert "Create Account" label/heading is present
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Create Account"))).isVisible();

        // Select "Proceed as Guest"
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
        page.waitForTimeout(3000);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 4: Contact Information Page
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(4)
    @DisplayName("TestCase 4: Contact Information Page")
    void testCaseContactInformation() {
        // Assert we are on the Contact Information page
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Contact Information"))).isVisible();

        // Enter first name, last name, email, phone
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("First Name (required)")).click();
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("First Name (required)")).fill("John");
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("First Name (required)")).press("Tab");

        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Doe");
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Last Name (required)")).press("Tab");

        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Email address (required)")).fill("johndoe@example.com");
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Email address (required)")).press("Tab");

        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Phone Number (required)")).click();
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("3121234567");

        assertThat(page.getByText("Order Subtotal").nth(1)).isVisible();

        // Click CONTINUE
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        page.waitForTimeout(3000);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 5: Pickup Information
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(5)
    @DisplayName("TestCase 5: Pickup Information")
    void testCasePickupInformation() {
        // Assert Contact Information: name, email, and phone are correct
        assertThat(page.getByText("John Doe").first()).isVisible();
        assertThat(page.getByText("johndoe@example.com").first()).isVisible();
        assertThat(page.getByText("3121234567").first()).isVisible();

        // Assert Pickup location is DePaul University Loop Campus & SAIC
        assertThat(page.locator("#bnedPickupPersonForm").getByText("DePaul University Loop Campus")).isVisible();

        // Assert selected Pickup Person ("I'll pick them up")
        assertThat(page.getByText("I'll pick them up").first()).isVisible();


        // Click CONTINUE
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        page.waitForTimeout(3000);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 6: Payment Information
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(6)
    @DisplayName("TestCase 6: Payment Information")
    void testCasePaymentInformation() {
        page.waitForTimeout(2000);



        // Click "< BACK TO CART" (upper-right)
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();
        page.waitForTimeout(2000);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TestCase 7: Your Shopping Cart – Delete Product and Assert Empty Cart
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(7)
    @DisplayName("TestCase 7: Your Shopping Cart – Delete and Verify Empty")
    void testCaseDeleteFromCart() {
        page.waitForTimeout(2000);

        // Delete product from cart
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
        page.waitForTimeout(2000);

        // Assert cart is empty
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Your cart is empty"))).isVisible();

        // Close window
        page.close();
    }
}
