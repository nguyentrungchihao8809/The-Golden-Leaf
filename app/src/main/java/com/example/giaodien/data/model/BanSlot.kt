package com.example.giaodien.data.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class BanSlot(
    val id: Long,
    val ngay: String,          // yyyy-MM-dd
    val khungGio: String,      // 07:00-11:00...
    val soBanBanDau: Int,
    val soBanConLai: Int,
    val soGheConLai: Int
)
