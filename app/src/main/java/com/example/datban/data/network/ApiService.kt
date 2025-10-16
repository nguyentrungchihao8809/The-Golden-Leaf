package com.example.datban.data.network

import retrofit2.http.Body
import retrofit2.http.POST

data class UserRequest(
    val fullName: String,
    val phone: String
)

interface ApiService {
    @POST("api/user/add")
    suspend fun createUser(@Body user: UserRequest): String // 👈 BE trả về String
}
