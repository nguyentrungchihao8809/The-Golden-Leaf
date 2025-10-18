package com.example.goldenleafapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.goldenleafapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Dish(
    val id: String,
    val name: String,
    val price: String,
    val rating: Double,
    val imageResId: Int
)

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val imageResId: Int
)

class HomeViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(
        listOf("Special", "Seasonal", "Appetizers", "Main Courses", "Desserts")
    )
    val categories: StateFlow<List<String>> = _categories

    private val _specialDishes = MutableStateFlow<List<Dish>>(
        listOf(
            Dish("1", "Súp Bào Ngư", "₫ 590.000", 4.8, R.drawable.sup_bao_ngu),
            Dish("2", "Súp Đuôi Bò", "₫ 400.000", 4.2, R.drawable.sup_duoi_bo)
        )
    )
    val specialDishes: StateFlow<List<Dish>> = _specialDishes

    // ✅ PHẦN NÀY ĐANG BỊ THIẾU TRONG FILE CŨ CỦA ANH
    private val _upcomingEvents = MutableStateFlow<List<Event>>(
        listOf(
            Event(
                id = "1",
                title = "Game Night",
                description = "Là sự kiện mang đến không gian giải trí sôi động...",
                tags = listOf("2 Seats", "5-10-2025, 7:00 PM"),
                imageResId = R.drawable.game_night
            )
        )
    )
    val upcomingEvents: StateFlow<List<Event>> = _upcomingEvents
}