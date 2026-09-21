plugins {
    // Android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // Kotlin
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktfmt) apply false
}

tasks.register("clean", Delete::class) {
    description = "Delete all generated files"
    delete(layout.buildDirectory)
}
