plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("jacoco")
    id("maven-publish")
    id("com.jfrog.bintray")
    id("com.github.b3er.local.properties") version "1.1"
    defaults.`android-module`
}

android {
    defaultSettings()
}

kotlin {
    val libraryName = "unox-android-arch-core"
    setupMetadataTarget("$libraryName-metadata")
    setupAndroidTarget(rootProject, project, libraryName)

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val androidMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                compileOnly("javax.annotation:javax.annotation-api:1.3.2")

                api(Deps.coroutinesRx2)
                api(Deps.khronos)
                api(Deps.kotlinxSerialization)
                api(Deps.okHttp)
                api(Deps.okHttpLoggingInterceptor)
                api(Deps.retrofit)
                api(Deps.retrofitKotlinxSerializationConverter)
                api(Deps.rxRelay)

                api(AndroidDeps.bungee)
                api(AndroidDeps.constraintLayout)
                api(AndroidDeps.drawableToolbox)
                api(AndroidDeps.flashbar)
                api(AndroidDeps.fragmentKtx)
                api(AndroidDeps.googlePlayCore)
                api(AndroidDeps.koinAndroid)
                api(AndroidDeps.materialDesign)
                api(AndroidDeps.materialDialogs)
                api(AndroidDeps.materialSpinner)
                api(AndroidDeps.mvRx)
                api(AndroidDeps.picasso)
                api(AndroidDeps.pugNotification)
                api(AndroidDeps.quantum)
                api(AndroidDeps.quantumRx)
                api(AndroidDeps.recyclerView)
                api(AndroidDeps.roomKtx)
                api(AndroidDeps.roomRxJava2)
                api(AndroidDeps.spinKit)
                api(AndroidDeps.splittiesAppctx)
                api(AndroidDeps.splittiesMainhandler)
                api(AndroidDeps.splittiesPermissions)
                api(AndroidDeps.splittiesResources)
                api(AndroidDeps.splittiesSystemservices)
                api(AndroidDeps.splittiesViews)
                api(AndroidDeps.splittiesViewsAppcompat)
                api(AndroidDeps.spotsDialog)
                api(AndroidDeps.stateViews)
                api(AndroidDeps.stetho)
                api(AndroidDeps.stethoOkHttp)
                api(AndroidDeps.timber)
                api(AndroidDeps.unoxCoreAndroid)
                api(AndroidDeps.workManagerKtx)
            }
        }

        val androidTest by getting {
            dependsOn(androidMain)
            kotlin.srcDir("src/test/kotlin")
            dependencies {
                TestDeps.androidCore.forEach {
                    implementation(it) {
                        exclude(group = "org.apache.maven")
                    }
                }
            }
        }
    }
}

dependencies {
    AndroidKaptDeps.core.forEach { "kapt"(it) }
    AndroidKaptDeps.core.forEach { "kaptTest"(it) }
}

setupBintrayPublish(bintray, "metadata", "androidDebug")

setupJacoco {
    sourceDirectories.setFrom(files(
            android.sourceSets["main"].java.srcDirs,
            "src/main/kotlin"
    ))
}