plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
    id("jacoco")
    id("maven-publish")
    id("com.jfrog.bintray")
    id("com.github.b3er.local.properties")
    defaults.`android-module`
}

compileKotlin {
    useExperimentalFeatures()
}

android {
    defaultSettings(project)
}

kotlin {
    val libraryName = "unox-android-arch-benchmark"
    setupMetaInfoNameOnAll(rootProject, project)
    setupMetadataTarget(rootProject, project, "$libraryName-metadata")
    setupAndroidTarget(rootProject, project, libraryName)

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val androidMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                compileOnly("javax.annotation:javax.annotation-api:1.3.2")
                api(project(":core"))

                implementation(Deps.arrowCoreData)
                implementation(Deps.coroutinesCore)

                implementation(AndroidDeps.timber)
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

setupBintrayPublish(bintray, "metadata", "androidDebug")

setupJacoco {
    sourceDirectories.setFrom(files(
            android.sourceSets["main"].java.srcDirs,
            "src/main/kotlin"
    ))
}