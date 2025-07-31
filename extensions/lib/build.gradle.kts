// playwright-kotlin-lib

// TODO: change publish name

kotlin {
    sourceSets {
        // This module aggregates dependents to an all-in-one library.
        commonMain.dependencies {
            api(projects.core)
            api(projects.locator)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        // TODO: understand why its not coming as transitive dependency of :core jvm source set
        jvmMain.dependencies {
            implementation(libs.playwright)
        }
    }
}

tasks.jvmTest {
    useJUnitPlatform()
}
