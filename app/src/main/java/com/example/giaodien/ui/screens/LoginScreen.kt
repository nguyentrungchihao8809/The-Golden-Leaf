package com.example.giaodien.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.giaodien.R // Đảm bảo bạn đã import R để truy cập tài nguyên
import com.example.giaodien.viewmodel.LoginViewModel
import com.example.giaodien.viewmodel.LoginUiState

// Màu đỏ đô (Maroon)
val MaroonColor = Color(0xFF800000)

// *** CHÚ Ý: GoogleSignInButton KHÔNG được thay đổi để đảm bảo chức năng ***
// Nó sẽ giữ lại giao diện gốc của nó, nhưng các nút khác sẽ theo phong cách mới.

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Log.d("NavTest", "LoginScreen is displayed")

    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 1. Hình nền (bgcm.jpg) mờ
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //
        Image(
            painter = painterResource(id = R.drawable.bgcm),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            alpha = 0.6f // Hiệu ứng mờ
        )

        // Nội dung màn hình
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 2. Chữ "THE GOLDEN LEAF" (To, In đậm, Đỏ đô)
            Text(
                "THE GOLDEN LEAF",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaroonColor
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Form Đăng Nhập (Ô bao màu trắng)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)) // Bo góc
                    .background(Color.White.copy(alpha = 0.9f)) // Nền form màu trắng
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nhập Tài khoản (Email) - Nền xám
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
                        disabledContainerColor = Color.LightGray.copy(alpha = 0.3f),
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Nhập Mật khẩu - Nền xám
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mật khẩu") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
                        disabledContainerColor = Color.LightGray.copy(alpha = 0.3f),
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nút "Đăng nhập" (Màu đỏ, chữ trắng)
                Button(
                    onClick = { viewModel.login(email, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red), // Nền đỏ
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Đăng nhập", color = Color.White) // Chữ trắng
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { onForgotPasswordClick() }) {
                    Text("Quên mật khẩu?", color = MaroonColor)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ---- Nút Google Sign-In (GIỮ NGUYÊN CHỨC NĂNG VÀ LỜI GỌI GỐC) ----
                // Vì chức năng của nút này đã được đóng gói, ta KHÔNG thay thế nó bằng Button thường
                GoogleSignInButton(onSignInSuccess = { userEmail ->
                    onLoginSuccess(userEmail) // callback giống login email/password
                })

                Spacer(modifier = Modifier.height(8.dp))


                when (uiState) {
                    is LoginUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 4.dp
                    )
                    is LoginUiState.Error -> Text(
                        text = (uiState as LoginUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    is LoginUiState.Success -> onLoginSuccess((uiState as LoginUiState.Success).userEmail)
                    else -> {}
                }

            }
        }
    }
}
