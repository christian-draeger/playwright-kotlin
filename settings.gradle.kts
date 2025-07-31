rootProject.name = "playwright-kotlin"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":core")
include(":lib")
include(":locator")
project(":lib").projectDir = file("extensions/lib")
project(":locator").projectDir = file("extensions/locator")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.1"
}
kover {
    enableCoverage()
}
