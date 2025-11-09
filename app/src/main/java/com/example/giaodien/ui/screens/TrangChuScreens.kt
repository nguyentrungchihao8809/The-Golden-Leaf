@file:OptIn(ExperimentalFoundationApi::class)
package com.example.giaodien.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giaodien.R
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.giaodien.navigation.Screen
import coil.compose.AsyncImage

// 🆕 THÊM CÁC IMPORT CẦN THIẾT CHO VIEWMODEL VÀ DATA
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.giaodien.viewmodel.ThucDonViewModel
import com.example.giaodien.data.model.ThucDon
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.platform.LocalContext // Cho Preview

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
    val DiscountPriceColor = Color(0xFF6C6C6C) // Màu xám cho giá gạch ngang
}

// --- Constants & Helper Functions ---

private val categoryTabs = listOf("Nổi bật", "Món mới", "Giảm giá")
private const val MAX_ITEMS_TO_SHOW = 6 // Chỉ hiển thị tối đa 6 món

/**
 * Hàm tiện ích định dạng Double sang tiền Việt Nam Đồng (VND).
 */
fun Double.toVND(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    return format.format(this).replace("₫", "₫ ")
}


// --- Main Screen ---

@Composable
fun TrangChuScreen(
    navController: NavHostController,
    // Lấy ViewModel. ViewModel sẽ được tự động tạo/tìm thấy trong NavHost
    thucDonViewModel: ThucDonViewModel = viewModel()
) {
    // Lấy danh sách thực đơn từ ViewModel state
    val allThucDon by thucDonViewModel.thucDonList.collectAsState()
    var selectedTab by remember { mutableStateOf(categoryTabs.first()) } // Mặc định là "Nổi bật"

    // Load dữ liệu khi Composable được tạo lần đầu
    LaunchedEffect(Unit) {
        if (allThucDon.isEmpty()) {
            thucDonViewModel.loadThucDon()
        }
    }

    // Logic lọc/sắp xếp món ăn dựa trên Tab và dữ liệu thực
    // Lưu danh sách random cố định
    var featuredList by remember { mutableStateOf<List<ThucDon>>(emptyList()) }
    var discountList by remember { mutableStateOf<List<ThucDon>>(emptyList()) }

    LaunchedEffect(allThucDon) {
        if (allThucDon.isNotEmpty()) {
            featuredList = allThucDon.shuffled().take(MAX_ITEMS_TO_SHOW)
            discountList = allThucDon.shuffled().take(MAX_ITEMS_TO_SHOW)
        }
    }

    val filteredThucDon = remember(selectedTab, featuredList, discountList, allThucDon) {
        when (selectedTab) {
            "Nổi bật" -> featuredList
            "Món mới" -> allThucDon.sortedByDescending { it.idThucDon }.take(MAX_ITEMS_TO_SHOW)
            "Giảm giá" -> discountList
            else -> emptyList()
        }
    }


    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        Scaffold(
            topBar = { TopLocationBar() },  // sẽ cố định
            bottomBar = { BottomNavBar(navController) },
            backgroundColor = AppColors.DarkBackground
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()) // chỉ scroll phần body
            ) {
                LoveBanner()
                Spacer(modifier = Modifier.height(20.dp))
                CategoryTabs(
                    tabs = categoryTabs,
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
                Spacer(modifier = Modifier.height(20.dp))

                FoodListSection(
                    items = filteredThucDon,
                    isDiscountTab = selectedTab == "Giảm giá"
                )

                Spacer(modifier = Modifier.height(20.dp))
                UpcomingEventsSection()
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

    }
}
// --- Top Bar ---
@Composable
fun TopLocationBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                clip = false
            )
            .background(
                color = AppColors.DarkBackground,
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon + text địa chỉ căn trái
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = AppColors.CoralRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "995 Tô Ký...",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // đẩy 2 icon còn lại sang phải

            // 2 icon gần nhau, căn phải
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = { /* thông báo */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { /* danh sách */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        }
    }
}




@Composable
fun LocationSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = AppColors.CoralRed,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "995 Tô Ký, P. Trung Mỹ Tây",
                style = MaterialTheme.typography.subtitle1,
                color = AppColors.LightText
            )
        }

        Row {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.List, contentDescription = "Menu")
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
        Image(
            painter = painterResource(id = R.drawable.nhahang),
            contentDescription = "Valentine's Day Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SemiTransparentBlack)
        )

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
fun FoodListSection(items: List<ThucDon>, isDiscountTab: Boolean) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            FoodCard(item, isDiscountTab)
        }
    }
}

@Composable
fun FoodCard(item: ThucDon, isDiscountTab: Boolean) {
    var isFavorite by remember { mutableStateOf(false) }

    val actualPrice = item.gia
    val discountedPrice = if (isDiscountTab) actualPrice / 0.9 else 0.0

    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF2A2A2A),
        elevation = 4.dp,
        modifier = Modifier.width(150.dp)
    ) {
        Column {

            // ẢNH + ICON YÊU THÍCH
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                AsyncImage(
                    model = item.anh,
                    contentDescription = item.tenMon,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) AppColors.CoralRed else AppColors.LightText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // THÔNG TIN MÓN ĂN
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = item.tenMon,
                    color = AppColors.LightText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (isDiscountTab) {
                    Text(
                        text = discountedPrice.toVND(),
                        color = AppColors.DiscountPriceColor,
                        fontSize = 10.sp,
                        textDecoration = TextDecoration.LineThrough,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = actualPrice.toVND(),
                        color = AppColors.SemiDarkText,
                        fontSize = 12.sp,
                        fontWeight = if (isDiscountTab) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1
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
                            text = "4.5",
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
        // Lỗi Ambiguity sẽ biến mất khi chỉ còn 1 hàm EventCard()
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
        }
    }
}

// Đã hợp nhất logic để chỉ còn 1 hàm EventCard
@Composable
private fun EventCard() {
    // Event Card 1
    EventItemCard(
        imageRes = R.drawable.game, // Thay bằng ID ảnh sự kiện 1 của bạn
        title = "Game Night",
        description = "Là sự kiện mang đến không gian giải trí sôi động với nhiều hoạt động hấp dẫn...",
        seats = "2 Seats",
        time = "5-10-2025, 7:00 PM"
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Event Card 2
    EventItemCard(
        imageRes = R.drawable.game2, // Thay bằng ID ảnh sự kiện 2 của bạn
        title = "Live Music Show",
        description = "Thưởng thức đêm nhạc sống lãng mạn với các ca sĩ nổi tiếng.",
        seats = "VIP Table",
        time = "10-10-2025, 8:00 PM"
    )
}

@Composable
private fun EventItemCard(imageRes: Int, title: String, description: String, seats: String, time: String) {
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
                painter = painterResource(id = imageRes),
                contentDescription = "$title Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = AppColors.LightText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
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
                        text = seats,
                        color = AppColors.CoralRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = time,
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
fun BottomNavBar(navController: NavHostController) {
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
                BottomNavItem(Icons.Default.Home, "Trang chủ") { /* Navigate to Home */ }
                BottomNavItem(Icons.Default.Receipt, "Lịch sử đặt") { /* Navigate to Cart */ }
                Spacer(Modifier.width(56.dp))
                BottomNavItem(Icons.Default.ChatBubble, "Tin Nhắn") { /* Navigate to Chat */ }
                BottomNavItem(Icons.Default.AccountCircle, "Tài khoản") {
                    navController.navigate(Screen.Main.route)
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Screen.NgayGio.route) },
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

