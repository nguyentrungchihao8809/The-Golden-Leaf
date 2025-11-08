package com.example.giaodien.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.giaodien.data.model.DatBan
import com.example.giaodien.viewmodel.DatBanViewModel
import com.google.firebase.auth.FirebaseAuth
@Composable
fun NhapSoLuongScreen(
    navController: NavController,
    ngayChon: String,
    khungGioChon: String,
    viTriBan: String,
    banConLai: Int,
    onDatBan: (soLuong: Int, ghiChu: String) -> Unit
) {
    var soLuong by remember { mutableStateOf(1) }
    var ghiChu by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ngày: $ngayChon", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Khung giờ: $khungGioChon", fontSize = 16.sp, color = Color.Gray)
        Text("Vị trí: $viTriBan", fontSize = 16.sp, color = Color.Gray)
        Spacer(Modifier.height(20.dp))
        Text("Còn trống: $banConLai", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (soLuong > 1) soLuong-- }) { Text("-") }
            Text(
                text = soLuong.toString(),
                modifier = Modifier.padding(horizontal = 32.dp),
                fontSize = 36.sp
            )
            Button(onClick = { if (soLuong < banConLai) soLuong++ }) { Text("+") }
        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = ghiChu,
            onValueChange = { ghiChu = it },
            label = { Text("Ghi chú (tùy chọn)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))

        Button(onClick = {
            onDatBan(soLuong, ghiChu)
        }) {
            Text("Xác nhận thông tin")
        }
    }
}
