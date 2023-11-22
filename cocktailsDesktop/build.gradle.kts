plugins {
    id("org.jetbrains.compose")
    kotlin("jvm")
}

dependencies {

    implementation(project(":shared"))

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.devmike.cocktailsDesktop.MainApplication"
    }
}
