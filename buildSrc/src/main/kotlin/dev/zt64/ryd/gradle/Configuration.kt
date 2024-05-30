package dev.zt64.ryd.gradle

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.*

fun KotlinMultiplatformExtension.apple(configure: KotlinNativeTarget.() -> Unit = {}) {
    val isMacOs = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("mac")

    if (!isMacOs) return

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64(),
        tvosX64(),
        tvosArm64(),
        tvosSimulatorArm64(),
        watchosArm32(),
        watchosArm64(),
        watchosSimulatorArm64()
    ).forEach(configure)
}

fun Project.setupPublishing(libraryName: String) {
    apply(plugin = "com.vanniktech.maven.publish")

    configure<MavenPublishBaseExtension> {
        coordinates(group.toString(), libraryName, version.toString())
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
        signAllPublications()

        pom {
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
}