package com.example.giaodien.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giaodien.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Giả định bạn đã import R.drawable hợp lệ
import com.example.giaodien.R

@Composable
fun ViTriBanScreen(
    weatherViewModel: WeatherViewModel,
    banConLai: Int = 5,
    onBack: () -> Unit = {},
    onNext: (viTriBan: String) -> Unit = {}
) {
    // 1. Lấy dữ liệu thời tiết
    val weatherState by weatherViewModel.weather.collectAsState()
    LaunchedEffect(Unit) {
        weatherViewModel.loadWeather("Hanoi")
    }
    val nhietDo = remember(weatherState) {
        weatherState?.main?.temp?.toInt()?.toString() ?: "--"
    }
    val thoiTiet = remember(weatherState) {
        weatherState?.weather?.firstOrNull()?.description?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        } ?: "Đang tải..."
    }

    // --- Dữ liệu không đổi ---
    val danhSachViTri = listOf("Ngoài trời", "Sông hồ", "Trong nhà", "Phòng riêng")
    val danhSachAnh = listOf(
        R.drawable.ngoaitroi,
        R.drawable.songho,
        R.drawable.trongnha,
        R.drawable.phongrieng
    )
    var viTriDaChon by remember { mutableStateOf(danhSachViTri[2]) }

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viTriDaChon) {
        val index = danhSachViTri.indexOf(viTriDaChon)
        if (index != -1) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(index)
            }
        }
    }

    // --- UI Styles & Thời gian ---
    val headerColor = Color(0xFFE8544D)
    val backgroundColor = Color(0xFF282828)
    val buttonColor = Color(0xFFE8544D)
    val darkTextColor = Color.White
    val lightTextColor = Color.White.copy(alpha = 0.7f)
    val ngayThang = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()) }
    val gio = remember { SimpleDateFormat("EEEE - HH:mm", Locale("vi", "VN")).format(Date()) }

    Scaffold(
        topBar = {
            // Phần Header (giữ nguyên)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(headerColor)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại", tint = darkTextColor)
                }
                Text("Vị trí bàn", color = darkTextColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Icon(Icons.Filled.Notifications, contentDescription = "Thông báo", tint = darkTextColor)
            }
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Phần Thông tin Thời tiết/Ngày giờ (Đã tích hợp API)
            // ... (Phần code này giữ nguyên)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🌙", fontSize = 30.sp)
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${nhietDo}°C", color = darkTextColor, fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.width(4.dp))
                            Text("⭐", fontSize = 16.sp)
                        }
                        Text(thoiTiet, color = lightTextColor, fontSize = 18.sp)
                    }

                    Spacer(Modifier.weight(1f))

                    Column(horizontalAlignment = Alignment.End) {
                        Text(ngayThang, color = darkTextColor, fontSize = 16.sp)
                        Text(gio, color = darkTextColor, fontSize = 16.sp)
                    }
                }
            }
            // --- HẾT PHẦN THỜI TIẾT ---

            // --- LAZY ROW cho Hình ảnh ---
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(danhSachAnh) { index: Int, drawableId: Int ->
                    val isSelected = viTriDaChon == danhSachViTri[index]

                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .shadow(8.dp, RoundedCornerShape(20.dp))
                            .background(Color.DarkGray)
                            .clickable { viTriDaChon = danhSachViTri[index] }
                    ) {
                        if (isSelected) {
                            Box(modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(20.dp))
                                .background(headerColor.copy(alpha = 0.4f)))
                        }

                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = danhSachViTri[index],
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Text(
                                danhSachViTri[index],
                                color = darkTextColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
            // --- KẾT THÚC LAZY ROW ---

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ PHẦN ĐÃ BỊ THIẾU: Danh sách lựa chọn vị trí (Radio Button)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                danhSachViTri.forEach { viTri ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable { viTriDaChon = viTri } // Cập nhật vị trí chọn
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = viTri,
                            color = darkTextColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                        RadioButton(
                            selected = viTriDaChon == viTri,
                            onClick = { viTriDaChon = viTri },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = darkTextColor,
                                unselectedColor = lightTextColor
                            )
                        )
                    }
                }
            }
            // --- KẾT THÚC DANH SÁCH CHỌN ---

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút Tiếp tục xuống cuối

            // Nút "Tiếp tục"
            Button(
                onClick = { onNext(viTriDaChon) },
                enabled = viTriDaChon.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text("Tiếp tục", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = darkTextColor)
            }
        }
    }
}
