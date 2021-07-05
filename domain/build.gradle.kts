plugins {
    id("kotlin")
}

sourceSets {
    main { java.srcDir("src/main/kotlin") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

val vKotlin: String by rootProject.extra

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$vKotlin")
}
