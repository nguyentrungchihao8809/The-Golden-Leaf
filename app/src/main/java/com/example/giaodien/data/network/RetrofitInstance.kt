package com.example.giaodien.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

// Firebase Auth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor

object RetrofitInstance {

    // ⭐ Dùng cho Emulator. Nếu chạy trên điện thoại thật → đổi sang IP máy: "http://192.168.x.x:8080/"
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    // Ghi log toàn bộ request / response
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // ✅ Interceptor lấy Firebase ID Token và gắn vào Header Authorization
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        // Lấy user đang đăng nhập
        val user = FirebaseAuth.getInstance().currentUser

        // Đợi token (suspend → dùng runBlocking)
        val token = runBlocking {
            user?.getIdToken(false)?.await()?.token
        }

        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else originalRequest

        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}
