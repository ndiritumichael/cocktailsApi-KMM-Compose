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
        mainClass = "com.devmike.cocktailsdesktop.MainApplicationKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Rpm,

                )
            packageName = "Cocktails"
            packageName = "1.0.0"
        }
    }
}
