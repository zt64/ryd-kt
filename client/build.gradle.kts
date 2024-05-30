
import com.vanniktech.maven.publish.SonatypeHost
import dev.zt64.ryd.gradle.apple

plugins {
    alias(libs.plugins.kotlin.multiplatform)

    // Maintenance
    alias(libs.plugins.compatibility)
    alias(libs.plugins.publish)
    alias(libs.plugins.ktlint)
}

kotlin {
    explicitApi()
    jvmToolchain(17)

    jvm()

    // @OptIn(ExperimentalWasmDsl::class)
    // wasmJs {
    //     browser()
    //     nodejs()
    // }

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
                implementation(projects.core)

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

mavenPublishing {
    coordinates(group.toString(), "client", version.toString())
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    pom {
        name = "ryd-kt"
        description = "Kotlin Multiplatform library for the Return YouTube Dislikes API"
        inceptionYear = "2023"
        url = "https://github.com/zt64/ryd-kt"
        licenses {
            license {
                name = "GPL-3.0"
                url = "https://opensource.org/license/gpl-3-0"
            }
        }
        developers {
            developer {
                id = "zt64"
                name = "zt"
                url = "https://zt64.dev"
            }
        }
        scm {
            url = "https://github.com/zt64/ryd-kt"
            connection = "scm:git:github.com/zt64/ryd-kt.git"
            developerConnection = "scm:git:ssh://github.com/zt64/ryd-kt.git"
        }
    }
}