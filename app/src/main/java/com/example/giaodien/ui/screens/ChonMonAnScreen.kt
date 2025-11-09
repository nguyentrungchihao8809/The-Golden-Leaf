@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.giaodien.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.giaodien.data.model.ThucDon
import com.example.giaodien.viewmodel.ThucDonViewModel
import com.example.giaodien.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.border
import androidx.navigation.NavController
import com.example.giaodien.viewmodel.DatBanViewModel
import com.example.giaodien.viewmodel.GioHangViewModel

// Định nghĩa màu Đỏ Đô
val DeepRed = Color(0xFF8B0000)

@Composable
fun ChonMonAnScreen(navController: NavController,
                    viewModel: ThucDonViewModel = viewModel(),
                    gioHangViewModel: GioHangViewModel = viewModel()) {
    val thucDonList by viewModel.thucDonList.collectAsState()
    val datBanViewModel: DatBanViewModel = viewModel()

    LaunchedEffect(Unit) { viewModel.loadThucDon() }

    Scaffold(
        containerColor = Color.Black // Nền cả màn hình là Đen
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // --- Phần header ---

                val zeroCorner = CornerSize(0.dp)
                val mediumShape = MaterialTheme.shapes.medium as RoundedCornerShape

                val headerShape: Shape = RoundedCornerShape(
                    topStart = zeroCorner,
                    topEnd = zeroCorner,
                    bottomStart = mediumShape.bottomStart,
                    bottomEnd = mediumShape.bottomEnd
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(headerShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bgcm),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.6f
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        // CẬP NHẬT: Khi bấm, điều hướng đến GioHangScreen
                        IconButton(onClick = { navController.navigate("gio_hang_screen") }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Giỏ hàng", tint = Color.White)
                        }
                    }

                    Text(
                        text = "The Kitchen\nBy The River",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // --- SỬA LỖI LAYOUT: Spacer và LazyColumn nằm trong Column chính ---
                Spacer(modifier = Modifier.height(16.dp))

                // --- LazyColumn chứa LazyRow theo nhóm ---
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight() // chiếm toàn bộ chiều cao còn lại
                        .padding(vertical = 8.dp)
                ) {
                    // TRUYỀN gioHangViewModel VÀO MonAnRow
                    item { MonAnRow("Món khai vị", thucDonList.filter { it.nhom.lowercase() == "khai_vi" }, gioHangViewModel) }
                    item { MonAnRow("Món chính", thucDonList.filter { it.nhom.lowercase() == "mon_chinh" }, gioHangViewModel) }
                    item { MonAnRow("Món tráng miệng", thucDonList.filter { it.nhom.lowercase() == "trang_mieng" }, gioHangViewModel) }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
                // --- KẾT THÚC Column ---
            }

            // --- Nút Bỏ Qua (Vẫn nằm trong Box để đặt ở BottomCenter) ---
            val datBan = datBanViewModel.currentDatBan // hoặc bạn lưu trước đó từ NhapSoLuongScreen

            Button(
                onClick = {
                    datBan?.let {
                        datBanViewModel.datBan(
                            datBan = it,
                            onSuccess = { navController.navigate("dat_ban_thanh_cong") },
                            onError = { /* hiển thị lỗi */ }
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DeepRed, contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(50.dp)
            ) {
                Text("Bỏ Qua")
            }

        }
    }
}

@Composable
fun MonAnRow(title: String, monList: List<ThucDon>, gioHangViewModel: GioHangViewModel) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(monList) { mon ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(140.dp)
                        .padding(vertical = 4.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.2f), MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(mon.anh),
                            contentDescription = mon.tenMon,
                            modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(mon.tenMon, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        Text("${mon.gia} VND", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { gioHangViewModel.addToCart(mon) }, // GỌI HÀM THÊM VÀO GIỎ HÀNG
                            colors = ButtonDefaults.buttonColors(containerColor = DeepRed, contentColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Chọn Món")
                        }
                    }
                }
            }
        }
    }
}
