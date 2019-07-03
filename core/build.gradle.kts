plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
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
    setupMetaInfoNameOnAll(rootProject, project)
    setupMetadataTarget(rootProject, project, "$libraryName-metadata")
    setupAndroidTarget(rootProject, project, libraryName)

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val androidMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                compileOnly("javax.annotation:javax.annotation-api:1.3.2")

                implementation(Deps.arrowCoreData)
                implementation(Deps.coroutinesRx2)
                implementation(Deps.khronos)
                implementation(Deps.rxRelay)

                implementation(AndroidDeps.bungee)
                implementation(AndroidDeps.disposer)
                implementation(AndroidDeps.drawableToolbox)
                implementation(AndroidDeps.flashbar)
                implementation(AndroidDeps.flexboxLayout)
                implementation(AndroidDeps.fragmentKtx)
                implementation(AndroidDeps.googlePlayCore)
                implementation(AndroidDeps.koinAndroid)
                implementation(AndroidDeps.materialComponents)
                implementation(AndroidDeps.materialDialogs)
                implementation(AndroidDeps.mvRx)
                implementation(AndroidDeps.picasso)
                implementation(AndroidDeps.pugNotification)
                implementation(AndroidDeps.quantum)
                implementation(AndroidDeps.quantumRx)
                implementation(AndroidDeps.recyclerView)
                implementation(AndroidDeps.roomKtx)
                implementation(AndroidDeps.roomRxJava2)
                implementation(AndroidDeps.spinKit)
                implementation(AndroidDeps.splittiesAppctx)
                implementation(AndroidDeps.splittiesMainhandler)
                implementation(AndroidDeps.splittiesPermissions)
                implementation(AndroidDeps.splittiesResources)
                implementation(AndroidDeps.splittiesSystemservices)
                implementation(AndroidDeps.splittiesViews)
                implementation(AndroidDeps.splittiesViewsAppcompat)
                implementation(AndroidDeps.spotsDialog)
                implementation(AndroidDeps.stateViews)
                implementation(AndroidDeps.timber)
                api(AndroidDeps.unoxCoreAndroid)
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