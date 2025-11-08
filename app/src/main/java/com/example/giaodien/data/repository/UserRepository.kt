package com.example.giaodien.data.repository

import com.example.giaodien.data.network.ApiService
import com.example.giaodien.data.network.model.TokenRequest
import com.example.giaodien.data.network.model.UserResponse

/**
 * UserRepository chịu trách nhiệm xử lý logic dữ liệu người dùng,
 * bao gồm việc đồng bộ hóa thông tin người dùng với Backend.
 * Nó tách biệt logic gọi mạng khỏi ViewModel.
 */
class UserRepository(private val apiService: ApiService) {

    /**
     * Gửi Firebase ID Token lên Backend để xác thực và đồng bộ dữ liệu.
     * @param idToken Firebase ID Token của người dùng đã đăng nhập.
     * @return UserResponse chứa thông tin người dùng được Backend trả về.
     */
    suspend fun synchronizeUser(idToken: String): UserResponse {
        val request = TokenRequest(token = idToken) // ❗️ đổi idToken → token
        return apiService.syncUser(request)
    }

    // Các hàm khác như lấy dữ liệu người dùng từ cơ sở dữ liệu cục bộ
    // hoặc cập nhật hồ sơ sẽ được thêm vào đây sau.
}