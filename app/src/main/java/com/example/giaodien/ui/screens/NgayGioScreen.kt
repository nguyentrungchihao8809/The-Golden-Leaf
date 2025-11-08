package com.example.giaodien.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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


@Composable
fun NgayGioScreen(viewModel: BanSlotViewModel, navController: NavController) {
    val slots by viewModel.slots.collectAsState()

    // Nhóm theo ngày (yyyy-MM-dd)
    val groupedSlots = slots.groupBy { it.ngay }

    // Danh sách 7 ngày sắp tới (yyyy-MM-dd)
    val today = LocalDate.now()
    val weekDays = (0..6).map { today.plusDays(it.toLong()) }

    var selectedDate by remember { mutableStateOf(today) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 1/3 trên: background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bgcm),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 2/3 dưới: ngày + khung giờ
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(8.dp)
        ) {
            // LazyRow 7 ngày
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(weekDays) { date ->
                    val formatterDay = DateTimeFormatter.ofPattern("dd")
                    val formatterMonth = DateTimeFormatter.ofPattern("MM")
                    val formatterYear = DateTimeFormatter.ofPattern("yyyy")
                    val isSelected = date == selectedDate

                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clickable { selectedDate = date },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFFFF9800) else Color.LightGray
                        )
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(formatterMonth.format(date), fontSize = 12.sp)
                            Text(formatterDay.format(date), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(formatterYear.format(date), fontSize = 10.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hiển thị các khung giờ theo ngày đã chọn
            val daySlots = groupedSlots[selectedDate.toString()] ?: emptyList()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(daySlots) { slot ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.SoDoBan.createRoute(
                                        slotId = slot.id,
                                        ngayChon = selectedDate.toString(),  // ngày đã chọn
                                        khungGioChon = slot.khungGio         // khung giờ của slot
                                    )
                                )
                            },

                                shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(slot.khungGio, fontWeight = FontWeight.Medium)
                            Text("Bàn còn: ${slot.soBanConLai}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
