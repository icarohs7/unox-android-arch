rootProject.name = "unoxandroidarch"

include(":app")
include(":core")
include(":location")
include(":scheduling")
include(":benchmark")

pluginManagement {
    resolutionStrategy {
        repositories {
            google()
            jcenter()
            gradlePluginPortal()
        }
    }
}