plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    // Maintenance
    alias(libs.plugins.compatibility) apply false
    alias(libs.plugins.publish) apply false
    alias(libs.plugins.ktlint) apply false
}

allprojects {
    group = "dev.zt64.ryd-kt"
    version = "1.0.0"
}