package com.example.giaodien.data.repository

import com.example.giaodien.data.model.ThucDon
import com.example.giaodien.data.network.ApiService // Giả định đây là interface API của anh
import javax.inject.Inject

/**
 * Đây là lớp THỰC THI "bản hợp đồng"
 * Nó sẽ làm theo mọi thứ trong MonAnRepository
 */
class MonAnRepositoryImpl @Inject constructor(
    private val api: ApiService // <-- Hilt sẽ "tiêm" ApiService vào đây
) : MonAnRepository { // <-- Báo cho Kotlin biết nó đang thực thi interface

    /**
     * Đây là code của anh
     */
    override suspend fun getAllThucDon(): List<ThucDon> {
        // Chúng ta không tự tạo "api" nữa, mà dùng "api" đã được tiêm
        return api.getThucDon()
    }

    /**
     * Đây là hàm mà MonAnDetailViewModel cần
     */
    override suspend fun getMonAnById(id: Long): ThucDon? {
        // Giả định ApiService của anh có hàm này
        return api.getThucDonById(id)
    }
}
