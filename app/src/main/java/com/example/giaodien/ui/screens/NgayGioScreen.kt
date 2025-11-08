package com.example.giaodien.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.giaodien.R
import com.example.giaodien.data.model.BanSlot
import com.example.giaodien.navigation.Screen
import com.example.giaodien.viewmodel.BanSlotViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.DayOfWeek
import java.util.Locale

// Định nghĩa màu sắc theo bản thiết kế (Đỏ cam)
val PrimaryRed = Color(0xFFE5584F) // Màu đỏ cam chính
val LightGreen = Color(0xFF8BC34A) // Màu xanh lá cây

@Composable
fun NgayGioScreen(viewModel: BanSlotViewModel, navController: NavController) {
    // --- KHỐI LOGIC DỮ LIỆU & TRẠNG THÁI (GIỮ NGUYÊN) ---
    val slots by viewModel.slots.collectAsState()
    val groupedSlots = slots.groupBy { it.ngay }
    val today = LocalDate.now()
    val weekDays = (0..6).map { today.plusDays(it.toLong()) }

    var selectedDate by remember { mutableStateOf(today) }
    // Thay đổi kiểu dữ liệu của ID thành String? để tránh lỗi Long/String mismatch
    var selectedSlotId by remember { mutableStateOf<String?>(null) }
    var selectedKhungGio by remember { mutableStateOf<String?>(null) }

    // Dữ liệu slots cho ngày đã chọn
    val daySlots = groupedSlots[selectedDate.toString()] ?: emptyList()
    // --- KẾT THÚC KHỐI LOGIC ---

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. HEADER (Phần đỏ cam)
        HeaderBar(navController = navController)

        // 2. BODY (Phần trắng)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            // Thanh tìm kiếm địa chỉ
            AddressSearch()

            Spacer(modifier = Modifier.height(16.dp))

            // Thông tin quán (THE SALT)
            RestaurantInfo()

            Spacer(modifier = Modifier.height(16.dp))

            // 3. LazyRow & LazyColumn (UI mới, Logic cũ)
            DateTimeSelectionBlock(
                weekDays = weekDays,
                selectedDate = selectedDate,
                daySlots = daySlots,
                selectedSlotId = selectedSlotId,
                onDateSelected = { newDate ->
                    // Logic: Cập nhật ngày và reset slot
                    selectedDate = newDate
                    selectedSlotId = null
                    selectedKhungGio = null
                },
                onSlotSelected = { slot ->
                    // Logic: Cập nhật slot
                    // **QUAN TRỌNG:** Chuyển đổi ID sang String để tránh lỗi type mismatch
                    selectedSlotId = slot.id.toString()
                    selectedKhungGio = slot.khungGio
                }
            )
        }

        // 4. NÚT TIẾP TỤC (Logic điều hướng GIỮ NGUYÊN)
        ContinueButton(
            isEnabled = selectedSlotId != null,
            onClick = {
                if (selectedSlotId != null && selectedKhungGio != null) {
                    // Chuyển đổi String trở lại thành Long
                    val slotIdLong = selectedSlotId!!.toLongOrNull()

                    if (slotIdLong != null) { // Đảm bảo chuyển đổi thành công
                        navController.navigate(
                            Screen.SoDoBan.createRoute(
                                slotId = slotIdLong, // TRUYỀN LONG VÀO ĐÂY
                                ngayChon = selectedDate.toString(),
                                khungGioChon = selectedKhungGio!!
                            )
                        )
                    }
                }
            }
        )
    }
}

// --- CÁC COMPOSABLE PHỤ (ĐÃ ĐƯỢC THIẾT KẾ LẠI UI) ---

@Composable
fun HeaderBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryRed)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back), // Sử dụng resource placeholder
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { navController.popBackStack() }
        )
        Text(
            text = "Khung giờ",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(end = 140.dp)
        )
//        Icon(
//            painter = painterResource(id = R.drawable.ic_menu), // Sử dụng resource placeholder
//            contentDescription = "Menu",
//            tint = Color.White,
//            modifier = Modifier.size(28.dp)
//        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSearch() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "333 Tô Ký, Quận 12, tp.HCM.",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors( // SỬA: Thay thế textFieldColors bằng colors
                focusedContainerColor = Color(0xFFEEEEEE),
                unfocusedContainerColor = Color(0xFFEEEEEE),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent, // Thêm nếu cần thiết
                cursorColor = Color.Black // Thêm màu con trỏ nếu cần thiết
            ),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryRed)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.map), // Sử dụng resource placeholder
                    contentDescription = "Map",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RestaurantInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.nhahang), // Sử dụng resource placeholder
            contentDescription = "Restaurant Image",
            modifier = Modifier
                .size(220.dp) // Kích thước lớn hơn
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "THE SALT",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "995 Quang Trung, Gò Vấp",
                fontSize = 24.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🍽️", fontSize = 26.sp)
                Spacer(modifier = Modifier.width(4.dp))
//                Row {
//                    repeat(5) { // Hiển thị 5 sao
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_rating_star), // Sử dụng resource placeholder
//                            contentDescription = "Rating Star",
//                            modifier = Modifier.size(16.dp)
//                        )
//                    }
//                }
            }
            Text(text = "feedback", fontSize = 22.sp, color = Color.Gray)
        }
    }
}

@Composable
fun DateTimeSelectionBlock(
    weekDays: List<LocalDate>,
    selectedDate: LocalDate,
    daySlots: List<BanSlot>,
    selectedSlotId: String?,
    onDateSelected: (LocalDate) -> Unit,
    onSlotSelected: (BanSlot) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // 1. Hàng các Thứ (CN, Th2,...)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items(weekDays) { date ->
                    val dayName = date.dayOfWeek.getVietnameseShortDay()
                    val isSelected = date == selectedDate
                    Text(
                        text = dayName,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) PrimaryRed else Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .clickable { onDateSelected(date) }
                    )
                }
            }

            // 2. Tên ngày và tháng lớn
            val currentMonth = DateTimeFormatter.ofPattern("MM").format(selectedDate)
            val currentDayOfMonth = DateTimeFormatter.ofPattern("dd").format(selectedDate)
            Text(
                text = "${selectedDate.dayOfWeek.getVietnameseDay()} ${currentDayOfMonth} tháng ${currentMonth}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // 3. LazyColumn Khung giờ
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 280.dp) // Giới hạn chiều cao cho scroll
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(daySlots) { slot ->
                    val isSelected = slot.id.toString() == selectedSlotId
                    val rowColor = if (isSelected) LightGreen.copy(alpha = 0.2f) else Color.Transparent

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(rowColor)
                            .border(
                                2.dp,
                                if (isSelected) LightGreen else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onSlotSelected(slot) }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Khung giờ
                        Text(
                            text = slot.khungGio,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 16.sp,
                            color = if (isSelected) PrimaryRed else Color.Black
                        )

                        // Số lượng trống (SL Trống)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(LightGreen)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = slot.soBanConLai.toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContinueButton(isEnabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            disabledContainerColor = PrimaryRed.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = "Tiếp tục",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

// --- HÀM MỞ RỘNG (HELPER FUNCTIONS) ---
fun DayOfWeek.getVietnameseShortDay(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "Th2"
        DayOfWeek.TUESDAY -> "Th3"
        DayOfWeek.WEDNESDAY -> "Th4"
        DayOfWeek.THURSDAY -> "Th5"
        DayOfWeek.FRIDAY -> "Th6"
        DayOfWeek.SATURDAY -> "Th7"
        DayOfWeek.SUNDAY -> "CN"
    }
}

fun DayOfWeek.getVietnameseDay(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "Thứ Hai"
        DayOfWeek.TUESDAY -> "Thứ Ba"
        DayOfWeek.WEDNESDAY -> "Thứ Tư"
        DayOfWeek.THURSDAY -> "Thứ Năm"
        DayOfWeek.FRIDAY -> "Thứ Sáu"
        DayOfWeek.SATURDAY -> "Thứ Bảy"
        DayOfWeek.SUNDAY -> "Chủ Nhật"
    }
}
