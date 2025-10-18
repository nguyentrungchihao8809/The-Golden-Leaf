package com.example.goldenleafapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.goldenleafapp.R
import com.example.goldenleafapp.viewmodel.Dish
import com.example.goldenleafapp.viewmodel.Event
import com.example.goldenleafapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel()
    val categories by homeViewModel.categories.collectAsState()
    val specialDishes by homeViewModel.specialDishes.collectAsState()
    val upcomingEvents by homeViewModel.upcomingEvents.collectAsState()

    Scaffold(
        bottomBar = { AppBottomNavigation() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            item { TopBar(modifier = Modifier.padding(top = 24.dp)) }
            item { HeroBanner() }
            item { CategoryTabs(categories = categories) }
            item { SectionHeader(title = "Special") }
            item { SpecialDishesList(dishes = specialDishes) }
            item { SectionHeader(title = "Upcoming Events", showSeeAll = true) }
            items(upcomingEvents) { event -> EventCard(event = event) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}


@Composable
fun AppBottomNavigation() {
    var selectedItem by remember { mutableStateOf("Home") }
    val items = listOf("Home", "Cart", "Chat", "Profile")
    val icons = mapOf(
        "Home" to Icons.Default.Home,
        "Cart" to Icons.Default.ShoppingCart,
        "Chat" to Icons.Default.Check,
        "Profile" to Icons.Default.Person
    )

    BottomAppBar(containerColor = Color(0xFF222222)) {
        NavigationBarItem(
            selected = selectedItem == "Home",
            onClick = { selectedItem = "Home" },
            icon = { Icon(icons["Home"]!!, contentDescription = "Home", tint = if (selectedItem == "Home") MaterialTheme.colorScheme.primary else Color.Gray) }
        )
        NavigationBarItem(
            selected = selectedItem == "Cart",
            onClick = { selectedItem = "Cart" },
            icon = { Icon(icons["Cart"]!!, contentDescription = "Cart", tint = if (selectedItem == "Cart") MaterialTheme.colorScheme.primary else Color.Gray) }
        )
        Spacer(Modifier.weight(1f))
        NavigationBarItem(
            selected = selectedItem == "Chat",
            onClick = { selectedItem = "Chat" },
            icon = { Icon(icons["Chat"]!!, contentDescription = "Chat", tint = if (selectedItem == "Chat") MaterialTheme.colorScheme.primary else Color.Gray) }
        )
        NavigationBarItem(
            selected = selectedItem == "Profile",
            onClick = { selectedItem = "Profile" },
            icon = { Icon(icons["Profile"]!!, contentDescription = "Profile", tint = if (selectedItem == "Profile") MaterialTheme.colorScheme.primary else Color.Gray) }
        )
    }
}

// --- Các Composable con của HomeScreen ---

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location Icon", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "995, Tô Ký, Quận 12", fontWeight = FontWeight.Bold, color = Color.White)
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown Icon", tint = Color.White)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications Icon", tint = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon", tint = Color.White)
        }
    }
}

@Composable
fun HeroBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = R.drawable.login_background,
            contentDescription = "Restaurant Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Celebrate Love at\nThe Golden Leaf", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Treat your special someone our exclusive Valentine's menu", color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun CategoryTabs(categories: List<String>, modifier: Modifier = Modifier) {
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }
    LazyRow(
        modifier = modifier.padding(vertical = 16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Text(text = category, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, fontSize = 18.sp, modifier = Modifier.clickable { selectedCategory = category })
        }
    }
}

@Composable
fun SectionHeader(title: String, showSeeAll: Boolean = false, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
        if (showSeeAll) {
            Text(text = "See All", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SpecialDishesList(dishes: List<Dish>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dishes) { dish -> DishCard(dish = dish) }
    }
}

@Composable
fun DishCard(dish: Dish, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
    ) {
        Column {
            AsyncImage(model = dish.imageResId, contentDescription = dish.name, modifier = Modifier.fillMaxWidth().height(120.dp), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = dish.name, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1)
                Text(text = dish.price, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC312), modifier = Modifier.size(16.dp))
                    Text(text = " ${dish.rating}", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun EventCard(event: Event, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(model = event.imageResId, contentDescription = event.title, modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = event.title, fontWeight = FontWeight.Bold, color = Color.White, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = event.description, color = Color.Gray, style = MaterialTheme.typography.bodySmall, maxLines = 3)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    event.tags.forEach { tag ->
                        Text(text = tag, color = Color.White, fontSize = 10.sp, modifier = Modifier.background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
            }
        }
    }
}