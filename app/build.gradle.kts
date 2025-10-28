plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
<<<<<<< HEAD
    namespace = "com.example.datban"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.datban"
=======
    namespace = "com.example.goldenleafapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.goldenleafapp"
>>>>>>> c6c346151d64d2622d1a53aef04da78cb8c4b926
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
<<<<<<< HEAD
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
=======

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
>>>>>>> c6c346151d64d2622d1a53aef04da78cb8c4b926
    }
    buildFeatures {
        compose = true
    }
<<<<<<< HEAD
}

dependencies {

=======
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // ✅ Kotlin Coroutines (cho Flow, StateFlow, ViewModel)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // ✅ AndroidX + Compose core
>>>>>>> c6c346151d64d2622d1a53aef04da78cb8c4b926
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
<<<<<<< HEAD
=======
    implementation(libs.androidx.compose.foundation)

    // ✅ Thêm các thư viện bổ trợ thường dùng
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation(libs.generativeai)

    // ✅ Test dependencies
>>>>>>> c6c346151d64d2622d1a53aef04da78cb8c4b926
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
<<<<<<< HEAD
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // (Tùy chọn) Nếu dùng Kotlin Coroutines để gọi API
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // (Tùy chọn) Logging cho debug
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
}
=======
}
>>>>>>> c6c346151d64d2622d1a53aef04da78cb8c4b926
