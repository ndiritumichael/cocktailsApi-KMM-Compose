plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm {

        sourceSets["commonMain"].resources.srcDirs("src/commonMain/resources")
    }
    sourceSets {
        named("commonMain") {
            resources.srcDirs("resources")
            // other parts
        }
    }

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.multiplatform.settings)
                implementation(libs.kamel.image)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

             //   implementation(libs.theme.material3)

                implementation(libs.voyager.navigator)

                api(libs.koin.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.client.json)
                implementation(libs.kotlinx.serialization.json)
                api(libs.logger.napier)

             //   implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutine)


                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.coroutines)

                implementation(libs.kmpalette.core)
                implementation(libs.kmpalette.extensions.network)

                implementation(libs.kotlinx.coroutines)
            }
        }
        val androidMain by getting {
            dependencies {

                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)

                implementation(libs.sqldelight.android.driver)
                api(libs.coil.compose)

                api(libs.koin.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqldelight.android.driver)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.sqldelight.native.driver)
                implementation(libs.ktor.client.darwin)
            }
        }

        val jvmMain by getting {


            dependencies {
                implementation(libs.sqldelight.jvm.driver)
implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(17)
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.devmike")
            srcDirs.setFrom("src/commonMain/sqldelight")
        }
    }
}


