buildscript {
    // common SDK versions
    val vCompileSdk by extra(31)
    val vMinSdk by extra(23)
    val vTargetSdk by extra(31)

    // common dependency versions
    val vAndroidX by extra("1.7.0")
    val vAppCompat by extra("1.4.0")
    val vConstraintLayout by extra("2.1.2")
    val vEspresso by extra("3.4.0")
    val vJunit by extra("4.13.2")
    val vJunitExt by extra("1.1.3")
    val vKoin by extra("3.1.4")
    val vKotlin by extra("1.6.21")
    val vLifecycle by extra("2.4.0")
    val vMaterial by extra("1.4.0")
    val vNavigation by extra("2.3.5")
    val vTimber by extra("5.0.1")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$vKotlin")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
