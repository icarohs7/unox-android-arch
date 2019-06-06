plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    defaults.`android-module`
}

android {
    defaultSettings()
    defaultConfig {
        applicationId = "com.github.icarohs7.app"
        versionCode = 1
        versionName = "1.00"
    }
}

dependencies {
    implementation(project(":location"))
    implementation(project(":scheduling"))

    implementation(AndroidDeps.splittiesLifecycleCoroutines)
    implementation(AndroidDeps.splittiesToast)
    implementation(AndroidDeps.materialDialogsInput)
    implementation(AndroidDeps.materialDialogsBottomSheets)

    AndroidKaptDeps.core.forEach(::kapt)
}
