package codes.draeger.playwright.core

import codes.draeger.playwright.PlaywrightDsl
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright

public class KPlaywright(private val playwright: Playwright) {
    public var browserType: BrowserType? = null
    private val _launchOptions = BrowserType.LaunchOptions()
    private val _launchPersistentContextOptions = BrowserType.LaunchPersistentContextOptions()
    private val _contextOptions = BrowserType.ConnectOptions()
    private val _connectOverCDPOptions = BrowserType.ConnectOverCDPOptions()

    public var launchOptions: BrowserType.LaunchOptions.() -> Unit = {}
        set(value) {
            _launchOptions.apply(value)
            field = value
        }

    public var launchPersistentContextOptions: BrowserType.LaunchPersistentContextOptions.() -> Unit = {}
        set(value) {
            _launchPersistentContextOptions.apply(value)
            field = value
        }

    public var connectOptions: BrowserType.ConnectOptions.() -> Unit = {}
        set(value) {
            _contextOptions.apply(value)
            field = value
        }

    public var connectOverCDPOptions: BrowserType.ConnectOverCDPOptions.() -> Unit = {}
        set(value) {
            _connectOverCDPOptions.apply(value)
            field = value
        }

    @Suppress("UnsafeCallOnNullableType")
    public fun <T> browser(block: BrowserDsl.(Browser) -> T): T {
        if (browserType == null) {
            browserType = playwright.webkit()
        }

        val browser = browserType!!.launch(_launchOptions)
        try {
            val browserDsl = BrowserDsl(browser)
            return block(browserDsl, browser)
        } finally {
            browser.close()
        }
    }
}

@PlaywrightDsl
public fun <T> playwright(
    createOptions: Playwright.CreateOptions = Playwright.CreateOptions(),
    block: KPlaywright.(Playwright) -> T
): T {
    return Playwright.create(createOptions).use { playwright ->
        val dsl = KPlaywright(playwright)
        block(dsl, playwright)
    }
}
