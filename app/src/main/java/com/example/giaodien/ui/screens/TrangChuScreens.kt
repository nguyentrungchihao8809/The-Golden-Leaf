package com.example.giaodien.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giaodien.R
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource // Rất qua

// --- Custom Colors ---
object AppColors {
    val CoralRed = Color(0xFFE9655D)
    val DarkBackground = Color(0xFF1E1E1E)
    val LightText = Color(0xFFFFFFFF)
    val SemiDarkText = Color(0xFFCCCCCC)
    val SemiTransparentBlack = Color(0x66000000)
    val ImagePlaceholder = Color(0xFF8B0000)
    val EventPlaceholder = Color(0xFF006400)
    val FoodPlaceholder = Color.Gray
}

// --- Data Models ---
data class FoodItem(
    val name: String,
    val price: String,
    val rating: Double
)

// --- Sample Data ---
private val sampleFoodItems = listOf(
    FoodItem("Súp Bào Ngư", "₫ 590.000", 4.8),
    FoodItem("Súp Đuôi Bò", "₫ 400.000", 4.2),
    FoodItem("Nem Cua Bể", "₫ 490.000", 4.5),
)

private val categoryTabs = listOf("Special", "Seasonal", "Appetizers", "Main Courses")

// --- Main Screen ---
@Composable
fun FoodAppScreen() {
    var selectedTab by remember { mutableStateOf("Special") }

    Scaffold(
        bottomBar = { BottomNavBar() },
        backgroundColor = AppColors.DarkBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            TopLocationBar()
            Spacer(modifier = Modifier.height(16.dp))
            LoveBanner()
            Spacer(modifier = Modifier.height(20.dp))
            CategoryTabs(
                tabs = categoryTabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            Spacer(modifier = Modifier.height(20.dp))
            FoodListSection(sampleFoodItems)
            Spacer(modifier = Modifier.height(20.dp))
            UpcomingEventsSection()
            Spacer(modifier = Modifier.height(80.dp)) // Space for FAB
        }
    }
}

// --- Top Bar ---
@Composable
fun TopLocationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LocationSection()
        ActionButtons()
    }
}

@Composable
fun LocationSection(modifier: Modifier = Modifier) { // Thêm modifier parameter
    Row(
        // Áp dụng modifier nhận được từ bên ngoài
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            tint = AppColors.CoralRed,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Your Location",
                color = AppColors.SemiDarkText,
                fontSize = 12.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "995, Tô Ký, Quang Trung, Quận 12",
                    color = AppColors.LightText,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Change Location",
                    tint = AppColors.LightText,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ActionButtons() {
    Row {
        IconButton(onClick = { /* Handle notification */ }) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = AppColors.LightText
            )
        }
        IconButton(onClick = { /* Handle menu */ }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = AppColors.LightText
            )
        }
    }
}

// --- Love Banner ---
@Composable
fun LoveBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        // 1. LỚP DƯỚI CÙNG: HÌNH ẢNH THỰC TẾ
        Image(
            // THAY THẾ R.drawable.banner_love BẰNG ID ẢNH CỦA BẠN
            painter = painterResource(id = R.drawable.nhahang),
            contentDescription = "Valentine's Day Banner",
            contentScale = ContentScale.Crop, // Đảm bảo ảnh lấp đầy Box và được cắt (crop) nếu cần
            modifier = Modifier.fillMaxSize()
        )

        // 2. LỚP TRÊN: OVERLAY MÀU ĐEN MỜ (GIỮ NGUYÊN)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SemiTransparentBlack)
        )

        // 3. LỚP TRÊN CÙNG: VĂN BẢN (GIỮ NGUYÊN)
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(20.dp)
        ) {
            Text(
                text = "Celebrate Love at",
                color = AppColors.LightText,
                fontSize = 16.sp
            )
            Text(
                text = "The Golden Leaf",
                color = AppColors.LightText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Treat your special\nsomeone our exclusive\nValentine's menu",
                color = AppColors.SemiDarkText,
                fontSize = 14.sp
            )
        }
    }
}

// --- Category Tabs ---
@Composable
fun CategoryTabs(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(tabs) { tab ->
            CategoryTab(
                text = tab,
                isSelected = tab == selectedTab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

@Composable
private fun CategoryTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (isSelected) AppColors.LightText else AppColors.SemiDarkText,
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

// --- Food List Section ---
@Composable
fun FoodListSection(items: List<FoodItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            FoodCard(item)
        }
    }
}

@Composable
fun FoodCard(item: FoodItem) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF2A2A2A),
        elevation = 4.dp,
        modifier = Modifier.width(150.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(AppColors.FoodPlaceholder)
            ) {
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) AppColors.CoralRed else AppColors.LightText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.name,
                    color = AppColors.LightText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.price,
                        color = AppColors.SemiDarkText,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = item.rating.toString(),
                            color = AppColors.SemiDarkText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

// --- Upcoming Events Section ---
@Composable
fun UpcomingEventsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionHeader()
        Spacer(modifier = Modifier.height(12.dp))
        EventCard()
    }
}

@Composable
private fun SectionHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Upcoming Events",
            color = AppColors.LightText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = { /* Handle See all */ }) {
            Text(
                text = "See all",
                color = AppColors.SemiDarkText,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
//            Icon(
//                imageVector = Icons.Default.ic_arrow_back,
//                contentDescription = "See all",
//                tint = AppColors.SemiDarkText,
//                modifier = Modifier.size(14.dp)
//            )
        }
    }
}

@Composable
private fun EventCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF2A2A2A),
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                // THAY THẾ R.drawable.event_gamenight BẰNG ID ẢNH SỰ KIỆN CỦA BẠN
                painter = painterResource(id = R.drawable.game),
                contentDescription = "Game Night Event Image",
                contentScale = ContentScale.Crop, // Đảm bảo ảnh lấp đầy khung
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Game Night",
                    color = AppColors.LightText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Là sự kiện mang đến không gian giải trí sôi động với nhiều hoạt động hấp dẫn...",
                    color = AppColors.SemiDarkText,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "2 Seats",
                        color = AppColors.CoralRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "5-10-2025, 7:00 PM",
                        color = AppColors.SemiDarkText,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.width(12.dp))

    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF2A2A2A),
        elevation = 2.dp,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                // THAY THẾ R.drawable.event_gamenight BẰNG ID ẢNH SỰ KIỆN CỦA BẠN
                painter = painterResource(id = R.drawable.game2),
                contentDescription = "Game Night Event Image",
                contentScale = ContentScale.Crop, // Đảm bảo ảnh lấp đầy khung
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Game Night",
                    color = AppColors.LightText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Là sự kiện mang đến không gian giải trí sôi động với nhiều hoạt động hấp dẫn...",
                    color = AppColors.SemiDarkText,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "2 Seats",
                        color = AppColors.CoralRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "5-10-2025, 7:00 PM",
                        color = AppColors.SemiDarkText,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// --- Bottom Navigation Bar ---
@Composable
fun BottomNavBar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        BottomAppBar(
            backgroundColor = AppColors.CoralRed,
            contentColor = AppColors.LightText,
            cutoutShape = CircleShape,
            modifier = Modifier.height(60.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(Icons.Default.Home, "Home") { /* Navigate to Home */ }
                BottomNavItem(Icons.Default.ShoppingCart, "Cart") { /* Navigate to Cart */ }
                Spacer(Modifier.width(56.dp))
                BottomNavItem(Icons.Default.ChatBubble, "Chat") { /* Navigate to Chat */ }
                BottomNavItem(Icons.Default.AccountCircle, "Account") { /* Navigate to Account */ }
            }
        }

        FloatingActionButton(
            onClick = { /* Handle Add */ },
            backgroundColor = AppColors.CoralRed,
            contentColor = AppColors.LightText,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = AppColors.LightText
        )
    }
}

// --- Preview ---
@Preview(showBackground = true, heightDp = 800)
@Composable
fun FoodAppScreenPreview() {
    MaterialTheme(
        colors = darkColors(
            primary = AppColors.CoralRed,
            background = AppColors.DarkBackground,
            surface = AppColors.DarkBackground
        )
    ) {
        FoodAppScreen()
    }
}
