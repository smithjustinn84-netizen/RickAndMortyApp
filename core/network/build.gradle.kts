plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "io.github.smithjustinn84_netizen.rickandmortyapp.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
        targetSdk = 36
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Ktor
    implementation(libs.bundles.ktor)

    implementation(libs.javax.inject)

    // Hilt runtime (brings in Dagger and required runtime types)
    implementation(libs.hilt.android.core)

    // Hilt compiler to generate factories for @Inject constructors and modules in this module
    ksp(libs.hilt.compiler)
}