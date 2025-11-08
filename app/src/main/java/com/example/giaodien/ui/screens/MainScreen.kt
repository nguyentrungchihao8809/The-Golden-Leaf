package com.example.giaodien.ui.screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.giaodien.R
import com.example.giaodien.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val name = user?.displayName ?: "User"
    val email = user?.email ?: "Unknown email"
    val photoUrl = user?.photoUrl

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(20.dp)
    ) {
        // 🔹 Góc trên trái chữ Profile
        Text(
            text = "Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .align(Alignment.TopStart)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
        ) {
            // 🔹 Avatar
            if (photoUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(photoUrl),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.bgcm),
                    contentDescription = "Default Avatar",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🔹 Name Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Name",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1565C0)
                    )
                }
            }

            // 🔹 Email Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Email",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = email,
                        fontSize = 16.sp,
                        color = Color(0xFF1E88E5)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // 🆕 Nút Điều hướng đến ChonMonAnScreen
            Button(
                onClick = {
                    // Thực hiện điều hướng đến màn hình Chọn Món Ăn
                    navController.navigate(Screen.ChonMonAn.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // Màu cam nổi bật
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Xem Thực Đơn",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = { navController.navigate(Screen.NgayGio.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            modifier = Modifier.fillMaxWidth(0.85f).height(50.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Đặt bàn ngay", fontWeight = FontWeight.Bold, color = Color.White)
        }



        // 🔹 Logout button dưới cùng

        Button(
            onClick = {
                val context = navController.context
                // 1. Firebase sign out
                FirebaseAuth.getInstance().signOut()

                // 2. Google sign out
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                googleSignInClient.signOut().addOnCompleteListener {
                    // 3. Navigate back to login
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Logout",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

    }
}
