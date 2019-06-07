rootProject.name = "unoxandroidarch"

include(":app")
include(":core")
include(":location")
include(":scheduling")
include(":benchmark")
include(":spinner")

pluginManagement {
    resolutionStrategy {
        repositories {
            google()
            jcenter()
            gradlePluginPortal()
        }
    }
}