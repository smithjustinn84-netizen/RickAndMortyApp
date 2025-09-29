plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.github.smithjustinn84_netizen.rickandmortyapp.domain"
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
    // Paging types used in interfaces
    implementation(libs.androidx.paging.runtime)

    // javax.inject for @Inject annotations in use cases
    implementation(libs.javax.inject)
}
