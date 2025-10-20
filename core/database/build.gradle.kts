plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "io.github.smithjustinn84_netizen.rickandmortyapp.database"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
        targetSdk = 36
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    // Room - expose to consumers because AppDatabase extends RoomDatabase
    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    // Paging runtime is required for PagingSource usage in DAO types
    implementation(libs.androidx.paging.runtime)

    // javax.inject (optional but common in core modules)
    implementation(libs.javax.inject)

    // Hilt runtime and compiler for DI modules in this library
    implementation(libs.hilt.android.core)
    ksp(libs.hilt.compiler)

    // Instrumented testing dependencies for this library module
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation("androidx.room:room-testing:${libs.versions.roomRuntime.get()}")
}
