
import dev.zt64.ryd.gradle.apple
import dev.zt64.ryd.gradle.setupPublishing
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)

    // Maintenance
    alias(libs.plugins.compatibility)
    alias(libs.plugins.publish)
    alias(libs.plugins.ktlint)
}

kotlin {
    explicitApi()
    jvmToolchain(17)

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }

    js {
        browser()
        nodejs()
    }

    linuxX64()
    linuxArm64()

    mingwX64()

    apple()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.serialization.json)
                implementation(libs.datetime)
                implementation(libs.sha)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.coroutines.test)
            }
        }
    }
}

setupPublishing("core")