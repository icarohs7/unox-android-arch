rootProject.name = "unoxandroidarch"

include(":app")
include(":core")
include(":location")

pluginManagement {
    resolutionStrategy {
        repositories {
            google()
            jcenter()
            gradlePluginPortal()
        }
    }
}