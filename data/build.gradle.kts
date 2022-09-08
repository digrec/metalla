plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val vCompileSdk: Int by rootProject.extra
val vMinSdk: Int by rootProject.extra
val vTargetSdk: Int by rootProject.extra

android {
    compileSdk = vCompileSdk

    defaultConfig {
        minSdk = vMinSdk
        targetSdk = vTargetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    sourceSets {
        named("main") { java.srcDir("src/main/kotlin") }
        named("test") { java.srcDir("src/test/kotlin") }
        named("androidTest") { java.srcDir("src/androidTest/kotlin") }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

val vAndroidX: String by rootProject.extra
val vAppCompat: String by rootProject.extra
val vEspresso: String by rootProject.extra
val vJunit: String by rootProject.extra
val vJunitExt: String by rootProject.extra
val vTimber: String by rootProject.extra

dependencies {
    implementation(project(":domain"))

    // AndroidX
    implementation("androidx.core:core-ktx:$vAndroidX")
    implementation("androidx.appcompat:appcompat:$vAppCompat")

    // Timber
    implementation("com.jakewharton.timber:timber:$vTimber")

    // JUnit
    testImplementation("junit:junit:$vJunit")
    androidTestImplementation("androidx.test.ext:junit:$vJunitExt")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:$vEspresso")
}
