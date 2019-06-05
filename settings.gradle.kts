rootProject.name = "unoxandroidarch"

include(":app")
include(":core")
include(":location")
include(":scheduling")

pluginManagement {
    resolutionStrategy {
        repositories {
            google()
            jcenter()
            gradlePluginPortal()
        }
    }
}