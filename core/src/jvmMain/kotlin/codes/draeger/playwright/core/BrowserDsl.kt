package codes.draeger.playwright.core

import codes.draeger.playwright.PlaywrightDsl
import com.microsoft.playwright.Browser
import com.microsoft.playwright.Page

@PlaywrightDsl
public class BrowserDsl(private val browser: Browser) {

    public fun <T> page(block: PageDsl.(Page) -> T): T {
        return browser.newPage().use { page ->
            val pageDsl = PageDsl(page)
            block(pageDsl, page)
        }
    }
}
