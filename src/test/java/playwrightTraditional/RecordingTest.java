package playwrightTraditional;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RecordingTest {

    @Test
    void runRecordingWithTests()
    {
        try (Playwright playwright = Playwright.create()) {
          Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(false));
          BrowserContext context = browser.newContext(
            new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get("videos/"))
            .setViewportSize(1280, 720));
          Page page = context.newPage();
          page.navigate("https://depaul.bncollege.com/");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
          page.locator(".facet__list.js-facet-list.js-facet-top-values > li:nth-child(3) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
          page.locator("#facet-price > .facet__values > .facet__list > li:nth-child(2) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").click();
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
          page.locator("#facet-Color > .facet__values > .facet__list > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
          page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
          page.waitForTimeout(3000);
          assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless"))).isVisible();
          assertThat(page.getByText("668972707").nth(1)).isVisible();
          assertThat(page.getByText("$164.98")).isVisible();
          assertThat(page.getByText("Adaptive noise cancelling")).isVisible();
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
          page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
          page.waitForTimeout(3000);
          assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Your Shopping Cart(1 Item)"))).isVisible();
          assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless"))).isVisible();
          assertThat(page.getByText("qty:")).isVisible();
          assertThat(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Quantity, edit and press"))).isVisible();
          assertThat(page.getByText("(1 Item)").nth(1)).isVisible();
          assertThat(page.getByText("$").first()).isVisible();
          assertThat(page.getByText("Subtotal $")).isVisible();
          assertThat(page.locator(".js-cart-totals > div:nth-child(2)")).isVisible();
          assertThat(page.getByText("Taxes TBD")).isVisible();
          assertThat(page.getByText("Estimated Total $")).isVisible();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).press("CapsLock");
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
          page.waitForTimeout(3000);
          assertThat(page.getByText("The coupon code entered is")).isVisible();
          page.getByLabel("Proceed To Checkout").click();
          assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Create Account"))).isVisible();
          page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
          page.waitForTimeout(3000);
          assertThat(page.getByText("Order Subtotal $").nth(1)).isVisible();
          assertThat(page.locator(".bned-order-summary-container.bned-step-summary-inner-container > .bned-order-summary-order-totals > .subtotals > div:nth-child(2)")).isVisible();
          assertThat(page.getByText("Tax TBD").nth(1)).isVisible();
          assertThat(page.getByText("Total $167.98 167.98 $").nth(1)).isVisible();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).click();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).click();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Joseph");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).press("Tab");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Alemu");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).press("Tab");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("testing@gmail.com");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).press("Tab");
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).click();
          page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("7732021234");
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
          assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Contact Information"))).isVisible();
          assertThat(page.getByText("Full Name Joseph Alemu Email")).isVisible();
          assertThat(page.locator("#bnedPickupPersonForm").getByText("DePaul University Loop Campus")).isVisible();
          page.locator("div").filter(new Locator.FilterOptions().setHasText("Errors were found with the")).nth(3).click();
          assertThat(page.getByText("I'll pick them up")).isVisible();
          assertThat(page.locator(".checkmark").first()).isVisible();
          assertThat(page.getByText("Order Subtotal $").nth(1)).isVisible();
          assertThat(page.locator(".bned-order-summary-container.bned-step-summary-inner-container > .bned-order-summary-order-totals > .subtotals > div:nth-child(2)")).isVisible();
          assertThat(page.getByText("Tax TBD").nth(1)).isVisible();
          assertThat(page.getByText("Total $167.98 167.98 $").nth(1)).isVisible();
          assertThat(page.locator("div:nth-child(4) > .bned-order-summary-entries-wp > .bned-order-summary-entry > .bned-order-summary-entry-details-wp > .bned-name-qty-wp > .bned-order-summary-entry-name")).isVisible();
          assertThat(page.getByText("$164.98").nth(3)).isVisible();
          page.getByLabel("main").locator("div").filter(new Locator.FilterOptions().setHasText("BACK TO TOP")).click();
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
          assertThat(page.getByText("Order Subtotal $").nth(1)).isVisible();
          assertThat(page.locator(".bned-order-summary-container.bned-step-summary-inner-container > .bned-order-summary-order-totals > .subtotals > div:nth-child(2)")).isVisible();
          assertThat(page.getByText("Tax $").nth(1)).isVisible();
          assertThat(page.getByText("Total $185.20 185.2 $").nth(1)).isVisible();
          assertThat(page.locator("div:nth-child(4) > .bned-order-summary-entries-wp > .bned-order-summary-entry > .bned-order-summary-entry-details-wp > .bned-name-qty-wp > .bned-order-summary-entry-name")).isVisible();
          assertThat(page.getByText("$164.98").nth(3)).isVisible();
          page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
          assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Your cart is empty"))).isVisible();
        }
    }
}