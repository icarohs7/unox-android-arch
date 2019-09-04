rootProject.name = "unoxandroidarch"

include(":app")
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