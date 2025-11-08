package com.example.giaodien.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.giaodien.data.model.BanSlot
import com.example.giaodien.navigation.Screen
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.giaodien.R
import com.example.giaodien.viewmodel.DatBanViewModel

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun SoDoBanScreen(
    slot: BanSlot,
    navController: NavHostController,
    ngayChon: String,
    khungGioChon: String
) {
    val total = slot.soBanBanDau
    val banDaDat = total - slot.soBanConLai
    val banConLai = slot.soBanConLai
    val datBanViewModel: DatBanViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            // 1/3 trên: background ảnh + chữ còn trống màu đỏ đô + ngày + khung giờ
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bgcm),
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().alpha(0.3f)
                )

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Còn trống: $banConLai",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = Color(0xFF8B0000)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Ngày: $ngayChon",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "Khung giờ: $khungGioChon",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }

            // 2/3 dưới: sơ đồ bàn, nền đen
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(total) { index ->
                        val isDisabled = index < banDaDat
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDisabled) Color.Gray else Color.Green
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "B${index + 1}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate(
                    Screen.ViTriBan.createRoute(
                        ngayChon = ngayChon,
                        khungGioChon = khungGioChon,
                        banConLai = banConLai  // truyền số bàn còn lại
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Tiếp tục",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }


    }
}
