plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val vCompileSdk: Int by rootProject.extra
val vMinSdk: Int by rootProject.extra
val vTargetSdk: Int by rootProject.extra

android {
    compileSdk = vCompileSdk

    defaultConfig {
        applicationId = "com.digrec.metalla"

        minSdk = vMinSdk
        targetSdk = vTargetSdk

        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
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
val vConstraintLayout: String by rootProject.extra
val vEspresso: String by rootProject.extra
val vJunit: String by rootProject.extra
val vJunitExt: String by rootProject.extra
val vKoin: String by rootProject.extra
val vLifecycle: String by rootProject.extra
val vMaterial: String by rootProject.extra
val vNavigation: String by rootProject.extra
val vTimber: String by rootProject.extra

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // AndroidX
    implementation("androidx.core:core-ktx:$vAndroidX")
    implementation("androidx.appcompat:appcompat:$vAppCompat")
    implementation("androidx.constraintlayout:constraintlayout:$vConstraintLayout")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$vLifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$vLifecycle")
    implementation("androidx.navigation:navigation-fragment-ktx:$vNavigation")
    implementation("androidx.navigation:navigation-ui-ktx:$vNavigation")
    implementation("androidx.navigation:navigation-fragment-ktx:$vNavigation")
    implementation("androidx.navigation:navigation-ui-ktx:$vNavigation")
    implementation("com.google.android.material:material:$vMaterial")

    // Koin DI
    implementation("io.insert-koin:koin-android:$vKoin")

    // Timber
    implementation("com.jakewharton.timber:timber:$vTimber")

    // JUnit
    testImplementation("junit:junit:$vJunit")
    androidTestImplementation("androidx.test.ext:junit:$vJunitExt")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:$vEspresso")
}
