# unox-android-arch

[![Kotlin](https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin-logo.svg/240px-Kotlin-logo.svg.png)](
https://kotlinlang.org)

[![Build for](https://forthebadge.com/images/badges/built-for-android.svg)](
https://forthebadge.com/)
[![Uses badges](https://forthebadge.com/images/badges/uses-badges.svg)](
https://forthebadge.com/)

[![Bintray Version](https://api.bintray.com/packages/icarohs7/libraries/unox-android-arch/images/download.svg)](
https://bintray.com/icarohs7/libraries/unox-android-arch/_latestVersion)
[![Build Status](https://travis-ci.org/icarohs7/unox-android-arch.svg?branch=master)](
https://travis-ci.org/icarohs7/unox-android-arch)
[![GitHub top language](https://img.shields.io/github/languages/top/icarohs7/unox-android-arch.svg)](
https://github.com/icarohs7/unox-android-arch/search?l=kotlin)
[![HitCount](http://hits.dwyl.io/icarohs7/unox-android-arch.svg)](
http://hits.dwyl.io/icarohs7/unox-android-arch)
[![GitHub license](https://img.shields.io/github/license/icarohs7/unox-android-arch.svg)](
https://github.com/icarohs7/unox-android-arch/blob/master/LICENSE)
[![codecov](https://codecov.io/gh/icarohs7/unox-android-arch/branch/master/graph/badge.svg)](
https://codecov.io/gh/icarohs7/unox-android-arch)

[![GitHub commit activity](https://img.shields.io/github/commit-activity/w/icarohs7/unox-android-arch.svg)](
https://github.com/icarohs7/unox-android-arch/commits/master)
[![GitHub issues](https://img.shields.io/github/issues/icarohs7/unox-android-arch.svg)](
https://github.com/icarohs7/unox-android-arch/issues)
[![GitHub tag](https://img.shields.io/github/tag/icarohs7/unox-android-arch.svg)](
https://github.com/icarohs7/unox-android-arch/releases)
[![GitHub forks](https://img.shields.io/github/forks/icarohs7/unox-android-arch.svg?style=social&label=Fork)](
https://github.com/icarohs7/unox-android-arch/fork)
[![GitHub stars](https://img.shields.io/github/stars/icarohs7/unox-android-arch.svg?style=social&label=Stars)](
https://github.com/icarohs7/unox-android-arch)
[![GitHub watchers](https://img.shields.io/github/watchers/icarohs7/unox-android-arch.svg?style=social&label=Watch)](
https://github.com/icarohs7/unox-android-arch/subscription)

[![GitHub commits](https://img.shields.io/github/commits-since/icarohs7/unox-android-arch/v0.1.svg)](
https://github.com/icarohs7/unox-android-arch/releases/v0.1)
[![Awesome Badges](https://img.shields.io/badge/badges-awesome-green.svg)](
https://github.com/Naereen/badges)
[![Open Source Love svg2](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](
https://github.com/ellerbrock/open-source-badges/)
[![GitHub last commit](https://img.shields.io/github/last-commit/icarohs7/unox-android-arch.svg)](
https://github.com/icarohs7/unox-android-arch/commits/master)
[![BADGINATOR](https://badginator.herokuapp.com/icarohs7/unox-android-arch.svg)](
https://github.com/defunctzombie/badginator)

## Adding to the project

```kotlin
repositories {
    maven("https://dl.bintray.com/icarohs7/libraries")
}
dependencies {
    //Core features
    implementation("com.github.icarohs7:unox-android-arch-core:$unoxandroidarch_version")
    
    //Execution time helpers
    implementation("com.github.icarohs7:unox-android-arch-benchmark:$unoxandroidarch_version")
    
    //Image loading helpers, extensions of Picasso(https://github.com/square/picasso)
    implementation("com.github.icarohs7:unox-android-arch-imageloading:$unoxandroidarch_version")
    
    //Location helpers
    implementation("com.github.icarohs7:unox-android-arch-location:$unoxandroidarch_version")
    
    //Push notification helpers, wrapping the library Pug Notification(https://github.com/halysongoncalves/Pugnotification)
    implementation("com.github.icarohs7:unox-android-arch-notification:$unoxandroidarch_version")
    
    //RxJava helpers and extensions
    implementation("com.github.icarohs7:unox-android-arch-rx:$unoxandroidarch_version")
    
    //Task scheduling helpers
    implementation("com.github.icarohs7:unox-android-arch-scheduling:$unoxandroidarch_version")
        
    //Helpers and extensions around Material Spinner(https://github.com/jaredrummler/MaterialSpinner/)
    implementation("com.github.icarohs7:unox-android-arch-spinner:$unoxandroidarch_version")
}
```
