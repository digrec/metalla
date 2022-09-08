buildscript {
    // common SDK versions
    val vCompileSdk by extra(32)
    val vMinSdk by extra(23)
    val vTargetSdk by extra(32)

    // common dependency versions
    val vAndroidX by extra("1.8.0")
    val vAppCompat by extra("1.5.0")
    val vConstraintLayout by extra("2.1.4")
    val vEspresso by extra("3.4.0")
    val vJunit by extra("4.13.2")
    val vJunitExt by extra("1.1.3")
    val vKoin by extra("3.2.0")
    val vLifecycle by extra("2.5.1")
    val vMaterial by extra("1.6.1")
    val vNavigation by extra("2.5.2")
    val vTimber by extra("5.0.1")
}

plugins {
    // Android
    id("com.android.library") version "7.2.2" apply false
    id("com.android.application") version "7.2.2" apply false

    // Kotlin
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
