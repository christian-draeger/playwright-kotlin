# Playwright Kotlin [![CI](https://github.com/christian-draeger/playwright-kotlin/actions/workflows/build.yml/badge.svg)](https://github.com/christian-draeger/playwright-kotlin/actions/workflows/build.yml) ![Maven Central Version](https://img.shields.io/maven-central/v/codes.draeger/playwright-kotlin?logo=sonatype&label=Release)

A Kotlin DSL over Playwright Java to write seek browser automations.

## Installation
```kotlin
implementation("codes.draeger:playwright:tbd")
```

## Usage
```kotlin
@Test
    fun `playwright exploration test`() {
        val allTitles = playwright {
            browser {
                page {
                    +"https://playwright.dev/"

                    screenshot {
                        path = Path("screenshots/example.png")
                    }

                    ".footer a" {  // can invoke css selector to be evaluated as locator
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
                    allFooterLinkLocators.map { it.textContent() to it.getAttribute("href") }
                }
            }
        }

        println(allTitles)
    }
```

---

## 📄 License

This repository is licensed under the [MIT License](LICENSE).

---

With this template, you can focus on writing great code while the boilerplate tasks are taken care of. Happy coding! 🎉
