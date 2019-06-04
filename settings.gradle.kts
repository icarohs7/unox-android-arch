rootProject.name = "unoxandroidarch"

include(":core")
include(":app")

pluginManagement {
    resolutionStrategy {
        repositories {
            google()
            jcenter()
            gradlePluginPortal()
        }
    }
}