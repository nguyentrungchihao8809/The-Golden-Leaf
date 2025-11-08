package com.example.giaodien.data.network

import com.example.giaodien.data.model.DatBan
import com.example.giaodien.data.model.ThucDon
import com.example.giaodien.data.model.BanSlot
// THÊM IMPORT MỚI
import com.example.giaodien.data.model.GioHangMonAn
import com.example.giaodien.data.network.model.TokenRequest
import com.example.giaodien.data.network.model.UserResponse
import retrofit2.http.*

interface ApiService {

    @GET("api/thucdon")
    suspend fun getThucDon(): List<ThucDon>

    @POST("api/auth/sync")
    suspend fun syncUser(@Body request: TokenRequest): UserResponse

    // ✅ Gửi thông tin đặt bàn vào bảng dat_ban
    // LƯU Ý: Hàm này phải trả về đối tượng DatBan (hoặc ID của DatBan) đã tạo trên server
    @POST("api/datban/save")
    suspend fun createDatBan(@Body datBan: DatBan): DatBan

    @GET("api/ban-slot")
    suspend fun getBanSlots(): List<BanSlot>

    // ✅ Hàm đặt giữ bàn (không liên quan bảng dat_ban)
    @POST("api/ban-slot/dat")
    suspend fun reserveBanSlot(
        @Query("ngay") ngay: String,
        @Query("khungGio") khungGio: String,
        @Query("soLuongKhach") soLuongKhach: Int
    ): BanSlot

    // 🆕 THÊM HÀM GỬI GIỎ HÀNG SAU KHI ĐẶT BÀN THÀNH CÔNG
    @POST("api/giohang/datmon")
    suspend fun postGioHang(
        @Body danhSachMon: List<GioHangMonAn>
    ): Unit
}