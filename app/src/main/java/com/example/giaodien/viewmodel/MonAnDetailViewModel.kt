package com.example.giaodien.viewmodel // Gói của bạn

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giaodien.data.model.ThucDon // Import model ThucDon
import com.example.giaodien.data.repository.MonAnRepository // Import repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Trạng thái giao diện cho màn hình chi tiết món ăn.
 */
sealed interface MonAnDetailUiState {
    data object Loading : MonAnDetailUiState
    data class Success(val thucDon: ThucDon) : MonAnDetailUiState
    data class Error(val message: String) : MonAnDetailUiState
}

@HiltViewModel
class MonAnDetailViewModel @Inject constructor(
    // SỬA 1: Gộp các tham số vào cùng một dòng, ngăn cách bởi dấu phẩy
    private val monAnRepository: MonAnRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Chỉ cần một State để quản lý toàn bộ giao diện
    private val _uiState = MutableStateFlow<MonAnDetailUiState>(MonAnDetailUiState.Loading)
    // SỬA 2: Sử dụng asStateFlow() để tạo phiên bản chỉ đọc an toàn
    val uiState: StateFlow<MonAnDetailUiState> = _uiState.asStateFlow()

    init {
        // Lấy monAnId từ arguments của navigation
        savedStateHandle.get<Long>("monAnId")?.let { monAnId ->
            if (monAnId != 0L) {
                getMonAnDetail(monAnId)
            } else {
                _uiState.value = MonAnDetailUiState.Error("ID Món ăn không hợp lệ")
            }
        }
    }

    private fun getMonAnDetail(id: Long) {
        viewModelScope.launch {
            _uiState.value = MonAnDetailUiState.Loading // Báo cho UI biết là đang tải
            try {
                val monAnResult = monAnRepository.getMonAnById(id)

                if (monAnResult != null) {
                    _uiState.value = MonAnDetailUiState.Success(monAnResult)
                } else {
                    _uiState.value = MonAnDetailUiState.Error("Không tìm thấy món ăn.")
                }
            } catch (e: Exception) {
                _uiState.value = MonAnDetailUiState.Error(e.message ?: "Đã có lỗi xảy ra")
            }
        }
    }
}

// SỬA 3: Xóa bỏ hoàn toàn sealed class UiState không cần thiết này
// sealed class UiState { ... }
