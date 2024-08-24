plugins {
    id("kmp-lib")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.serialization)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.coroutines.test)
                implementation(libs.ktor.test)
            }
        }
    }
}