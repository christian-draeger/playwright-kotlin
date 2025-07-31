package codes.draeger.playwright.extensions.locator

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Locator.ClickOptions

public fun Locator.click(block: ClickOptions.() -> Unit = {}): Locator {
    val options = ClickOptions().apply(block)
    click(options)
    return this
}
