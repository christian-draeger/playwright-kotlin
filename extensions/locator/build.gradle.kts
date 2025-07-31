kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
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
