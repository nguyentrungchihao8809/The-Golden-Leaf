plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.example.giaodien"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.giaodien"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    

    // Compose BOM (khuyến nghị dùng BOM để tránh lỗi version)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    // Navigation compose
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Firebase Auth + Google Sign-in
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // ---------------------------------------------
    // 💡 CÁC THƯ VIỆN NETWORK ĐÃ CẬP NHẬT
    // ---------------------------------------------

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // OkHttp (CẦN THIẾT)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Logging Interceptor (CẦN THIẾT cho HttpLoggingInterceptor)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Kotlin Serialization
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // ---------------------------------------------
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.4")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

