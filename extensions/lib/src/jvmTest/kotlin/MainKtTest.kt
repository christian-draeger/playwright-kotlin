import codes.draeger.playwright.core.playwright
import codes.draeger.playwright.extensions.locator.click
import com.microsoft.playwright.options.MouseButton
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.random.Random

class MainKtTest {

    @Test
    fun `playwright DSL exploration test`() {
        playwright {
            browserType = it.chromium()

            launchOptions = {
                headless = false
                slowMo = 50.0
            }

            launchPersistentContextOptions = {
                timeout = 1000.0
            }

            connectOptions = {
                headers = mapOf("X-Custom-Header" to "some-custom-value")
            }

            connectOverCDPOptions = {
                timeout = 1000.0
            }

            browser { // default browser is webkit, can be overwritten via env variable BROWSER
                page { page ->
                    // can access Page methods directly on the explicit receiver (it)
                    page.onLoad { p -> println("Page loaded: ${p.url()}") }
                    page.onPageError { err -> println("Page error: $err") }

                    // implicit receiver (this) is the PageDsl object
                    +"https://playwright.dev/" // shorthand for it.navigate("https://playwright.dev/")

                    // explicit receiver (it) is the Page object of Playwright
                    page.navigate("https://playwright.dev/")
                }
            }

            val browsers = listOf(it.chromium(), it.firefox(), it.webkit())

            launchOptions = {
                headless = true
            }

            browsers.forEach { browser ->
                browserType = browser
                launchOptions = {
                    slowMo = Random.Default.nextDouble(.0, 20.0).also { println("${browser.name()} SlowMo: $it") }
                }

                browser {
                    page {
                        +"https://playwright.dev/"

                        val randomLink =
                            "a" {}.all().map { a ->
                                a.getAttribute(
                                    "href"
                                )
                            }.filter { href -> href.startsWith("http") }.random()

                        +randomLink
                        val headline = "title" {}.all().first().textContent()
                        println(headline)
                    }
                }
            }
        }
    }

    @Test
    fun `playwright exploration test`() {
        val allTitles = playwright {
            browser {
                page {
                    +"https://playwright.dev/"

                    screenshot {
                        path = Path("screenshots/example.png")
                    }

                    ".footer a" { // can invoke css selector to be evaluated as locator
                        hasText = "Getting started" // can directly set locator options
                        // returns the locator to be able to chain or store locator in a variable
                    }.click {
                        button = MouseButton.LEFT // can directly set click options
                        // returns the clicked locator to be able to chain further
                    }

                    screenshot {
                        // can directly set screenshot options
                        path = Path("screenshots/getting-started.png")
                    }

                    // can use xpath to be evaluated as locator and return a list of locators
                    val allFooterLinkLocators =
                        "//footer//a" {}.all() // can use xpath to be evaluated as locator and return a list of locators

                    // return all footer links
                    allFooterLinkLocators.map { link -> link.textContent() to link.getAttribute("href") }
                }
            }
        }

        println(allTitles)
    }
}
