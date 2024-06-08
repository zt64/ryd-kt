plugins {
    `kotlin-dsl`
}

fun DependencyHandler.compileOnly(dependency: Provider<PluginDependency>) {
    compileOnly(dependency.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" })
}

dependencies {
    compileOnly(libs.plugins.kotlin.multiplatform)
    compileOnly(libs.plugins.publish)
    compileOnly(libs.plugins.ktlint)
}

gradlePlugin {
    plugins.register("kmp-lib") {
        id = "kmp-lib"
        implementationClass = "dev.zt64.ryd.gradle.KmpLibPlugin"
    }
}
