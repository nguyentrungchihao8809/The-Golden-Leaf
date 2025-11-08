package com.example.giaodien.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

// 💡 Imports cho Network và Repository
import com.example.giaodien.data.network.RetrofitInstance
import com.example.giaodien.data.repository.UserRepository
import com.example.giaodien.data.network.model.TokenRequest
import com.example.giaodien.data.network.model.UserResponse

// --- Định nghĩa UiState ---
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val userEmail: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(
    private val userRepository: UserRepository = UserRepository(RetrofitInstance.api)
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // ----------------------------------------------------
    // 2. Hàm login() (Email/Password)
    // ----------------------------------------------------
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Email hoặc mật khẩu không được để trống")
            return
        }

        _uiState.value = LoginUiState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userEmail = auth.currentUser?.email ?: email
                    Log.d("LoginViewModel", "Login successful, starting sync for $userEmail") // Added log
                    startUserSynchronization(userEmail)
                } else {
                    _uiState.value = LoginUiState.Error(task.exception?.localizedMessage ?: "Đăng nhập thất bại")
                }
            }
    }

    // ----------------------------------------------------
    // 3. Hàm Bắt đầu Đồng bộ (Main Orchestrator)
    // ----------------------------------------------------
    fun startUserSynchronization(userEmail: String) {
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            _uiState.value = LoginUiState.Error("Lỗi: Người dùng không xác thực Firebase.")
            return
        }

        // Lấy Firebase ID Token
        firebaseUser.getIdToken(true).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken = task.result?.token
                if (idToken != null) {
                    Log.d("LoginViewModel", "Successfully obtained ID Token. Launching coroutine for backend sync.") // Added log
                    viewModelScope.launch {
                        // Gọi Backend
                        synchronizeUserWithBackend(idToken, userEmail)
                    }
                } else {
                    _uiState.value = LoginUiState.Error("Không thể lấy ID Token từ Firebase.")
                }
            } else {
                Log.e("LoginViewModel", "Error fetching ID Token: ${task.exception?.localizedMessage}") // Added log
                _uiState.value = LoginUiState.Error("Lỗi lấy Firebase Token: ${task.exception?.localizedMessage}")
            }
        }
    }

    // ----------------------------------------------------
    // 4. Hàm gọi Backend (Sử dụng Repository)
    // ----------------------------------------------------
    private suspend fun synchronizeUserWithBackend(idToken: String, userEmail: String) {
        try {
            // 💡 CHỈNH SỬA: Gọi Repository thay vì ApiService trực tiếp
            val userProfile = userRepository.synchronizeUser(idToken)

            Log.i("SyncSuccess", "Đồng bộ thành công! UID: ${userProfile.uid}")
            _uiState.value = LoginUiState.Success(userEmail)

        } catch (e: Exception) {
            // Log lỗi chi tiết của network (ví dụ: No Internet, Timeout, 401)
            Log.e("SyncError", "Lỗi đồng bộ Backend: ${e.message}", e)
            _uiState.value = LoginUiState.Error("Lỗi đồng bộ hóa dữ liệu: ${e.message}")
        }
    }

    // ----------------------------------------------------
    // 5. Hàm xử lý Đăng nhập Google/External
    // ----------------------------------------------------
    fun handleExternalSignInSuccess(userEmail: String) {
        Log.d("LoginViewModel", "External sign-in successful. Starting sync.") // Added log
        _uiState.value = LoginUiState.Loading
        startUserSynchronization(userEmail)
    }
}