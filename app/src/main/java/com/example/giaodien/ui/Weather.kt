package com.example.giaodien.thotiet

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.giaodien.data.model.WeatherResponse

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}
