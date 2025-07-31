import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("version-management-convention")
    id("kotlin-library-convention")
}

subprojects {
    apply(plugin = "kotlin-library-convention")
}

tasks {
    // detektAll is a task that runs detekt on all src sets
    val detektAll by registering {
        allprojects {
            this@registering.dependsOn(tasks.withType<Detekt>())
        }
    }
    build {
        dependsOn(detektAll)
        finalizedBy(koverXmlReport, koverHtmlReport)
    }
}
