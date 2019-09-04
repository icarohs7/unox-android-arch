rootProject.name = "unoxandroidarch"

include(":benchmark")
include(":core")
include(":notification")
include(":scheduling")
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