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
    api(Deps.arrowCore)
    api(Deps.arrowEffects)
    api(Deps.coroutinesRx2)
    api(Deps.khronos)
    api(Deps.kotlinStdLib)
    api(Deps.kotlinxSerialization)
    api(Deps.okHttp)
    api(Deps.okHttpLoggingInterceptor)
    api(Deps.retrofit)
    api(Deps.retrofitKotlinxSerializationConverter)
    api(Deps.rxJava2)
    api(Deps.rxKotlin)
    api(Deps.rxRelay)

    api(AndroidDeps.ankoSdk25)
    api(AndroidDeps.appCompat)
    api(AndroidDeps.appUpdater)
    api(AndroidDeps.bungee)
    api(AndroidDeps.constraintLayout)
    api(AndroidDeps.coreKtx)
    api(AndroidDeps.coroutinesAndroid)
    api(AndroidDeps.disposer)
    api(AndroidDeps.drawableToolbox)
    api(AndroidDeps.flashbar)
    api(AndroidDeps.fragmentKtx)
    api(AndroidDeps.koinAndroidxViewmodel)
    api(AndroidDeps.lifecycleExtensions)
    api(AndroidDeps.lifecycleReactiveStreamsKtx)
    api(AndroidDeps.lives)
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
    api(AndroidDeps.rxAndroid)
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

jacoco {
    toolVersion = "0.8.3"
}

tasks {
    withType<Test> {
        extensions.getByType<JacocoTaskExtension>().setIncludeNoLocationClasses(true)
    }

    create<JacocoReport>("jacocoTestReport") {
        dependsOn("testDebugUnitTest", "createDebugCoverageReport")

        this.group = "Reporting"
        this.description = "Generate Jacoco coverage reports for Debug build"

        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }

        val excludes = listOf(
                "**/R.class",
                "**/R\$*.class",
                "**/*\$ViewInjector*.*",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*",
                "android/**/*.*"
        )

        classDirectories.setFrom(
                fileTree("$buildDir/intermediates/classes/debug") { exclude(excludes) },
                fileTree("$buildDir/tmp/kotlin-classes/debug") { exclude(excludes) }
        )
        sourceDirectories.setFrom(files(
                android.sourceSets["main"].java.srcDirs,
                "src/main/kotlin"
        ))
        executionData("$buildDir/jacoco/testDebugUnitTest.exec")
    }
}