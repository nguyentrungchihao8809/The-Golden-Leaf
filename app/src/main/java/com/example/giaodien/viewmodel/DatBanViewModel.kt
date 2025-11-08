package com.example.giaodien.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giaodien.data.model.DatBan
import com.example.giaodien.data.repository.DatBanRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatBanViewModel : ViewModel() {

    private val repository = DatBanRepository()

    // Dữ liệu DatBan tạm thời
    private val _datBan = MutableStateFlow<DatBan?>(null)
    val datBan: StateFlow<DatBan?> get() = _datBan

    // Cho phép truy cập trực tiếp DatBan hiện tại
    val currentDatBan: DatBan?
        get() = _datBan.value

    fun setDatBan(datBan: DatBan) {
        _datBan.value = datBan
    }

    fun datBan(
        datBan: DatBan,
        // Thay đổi kiểu của onSuccess để nó nhận DatBan đã được tạo
        onSuccess: (DatBan) -> Unit, // <--- ĐÃ SỬA
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Giả định repository.datBan() trả về đối tượng DatBan đã được server cập nhật ID
                val createdDatBan = repository.datBan(datBan) // SỬA: Hứng kết quả trả về

                _datBan.value = createdDatBan // Cập nhật DatBan đã có ID
                onSuccess(createdDatBan) // <--- TRUYỀN DATBAN CÓ ID VÀO CALLBACK
            } catch (e: Exception) {
                onError(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun getUserEmail(): String {
        return FirebaseAuth.getInstance().currentUser?.email ?: ""
    }
}
