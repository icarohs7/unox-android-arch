plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("com.github.dcendents.android-maven") version "2.1"
    id("jacoco")
    defaults.`android-module`
}

android {
    defaultSettings()
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }
}

dependencies {
    api(Deps.khronos)
    api(Deps.kotlinxSerialization)
    api(Deps.okHttp)
    api(Deps.okHttpLoggingInterceptor)
    api(Deps.retrofit)
    api(Deps.retrofitKotlinxSerializationConverter)
    api(Deps.rxRelay)

    api(AndroidDeps.ankoSdk25)
    api(AndroidDeps.appUpdater)
    api(AndroidDeps.bungee)
    api(AndroidDeps.constraintLayout)
    api(AndroidDeps.drawableToolbox)
    api(AndroidDeps.flashbar)
    api(AndroidDeps.fragmentKtx)
    api(AndroidDeps.koinAndroid)
    api(AndroidDeps.lifecycleReactiveStreamsKtx)
    api(AndroidDeps.materialDesign)
    api(AndroidDeps.materialDialogs)
    api(AndroidDeps.materialSpinner)
    api(AndroidDeps.mvRx)
    api(AndroidDeps.picasso)
    api(AndroidDeps.pugNotification)
    api(AndroidDeps.quantum)
    api(AndroidDeps.quantumRx)
    api(AndroidDeps.reactiveNetwork)
    api(AndroidDeps.recyclerView)
    api(AndroidDeps.roomRuntime)
    api(AndroidDeps.roomRxJava2)
    api(AndroidDeps.rxPermission)
    api(AndroidDeps.smartScheduler)
    api(AndroidDeps.spinKit)
    api(AndroidDeps.splittiesAppctx)
    api(AndroidDeps.splittiesResources)
    api(AndroidDeps.spotsDialog)
    api(AndroidDeps.stateViews)
    api(AndroidDeps.stetho)
    api(AndroidDeps.stethoOkHttp)
    api(AndroidDeps.timber)
    api(AndroidDeps.unoxCoreAndroid)

    AndroidKaptDeps.core.forEach(::kapt)
    AndroidKaptDeps.core.forEach(::kaptTest)
}

setupJacoco {
    sourceDirectories.setFrom(files(
            android.sourceSets["main"].java.srcDirs,
            "src/main/kotlin"
    ))
}