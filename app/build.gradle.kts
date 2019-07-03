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

    implementation(Deps.arrowCoreData)
    implementation(Deps.khronos)

    implementation(AndroidDeps.disposer)
    implementation(AndroidDeps.koinAndroid)
    implementation(AndroidDeps.materialComponents)
    implementation(AndroidDeps.materialDialogs)
    implementation(AndroidDeps.materialDialogsBottomSheets)
    implementation(AndroidDeps.materialDialogsInput)
    implementation(AndroidDeps.mvRx)
    implementation(AndroidDeps.roomKtx)
    implementation(AndroidDeps.roomRxJava2)
    implementation(AndroidDeps.splittiesLifecycleCoroutines)
    implementation(AndroidDeps.splittiesToast)
    implementation(AndroidDeps.workManagerKtx)

    AndroidKaptDeps.core.forEach(::kapt)
}
