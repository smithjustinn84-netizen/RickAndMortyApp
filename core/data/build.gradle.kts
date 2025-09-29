plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "io.github.smithjustinn84_netizen.rickandmortyapp.data"
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
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:domain"))

    // Paging APIs
    implementation(libs.androidx.paging.runtime)

    // javax.inject for @Inject
    implementation(libs.javax.inject)

    // Hilt runtime (brings in Dagger and required runtime types)
    implementation(libs.hilt.android.core)

    // Hilt compiler to generate factories for @Inject constructors in this module
    ksp(libs.hilt.compiler)
}