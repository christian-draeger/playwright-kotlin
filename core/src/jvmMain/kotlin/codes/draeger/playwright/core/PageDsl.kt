package codes.draeger.playwright.core

import codes.draeger.playwright.PlaywrightDsl
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.LocatorOptions

@PlaywrightDsl
public class PageDsl(private val page: Page) {

    public operator fun String.unaryPlus() {
        page.navigate(this)
    }

    public fun screenshot(block: Page.ScreenshotOptions.() -> Unit = {}) {
        val options = Page.ScreenshotOptions().apply(block)
        page.screenshot(options)
    }

    public fun locator(selector: String, block: LocatorOptions.() -> Unit = {}): Locator {
        val options = LocatorOptions().apply(block)
        return page.locator(selector, options)
    }

    public operator fun String.invoke(block: LocatorOptions.() -> Unit = {}): Locator = locator(this, block)
}
