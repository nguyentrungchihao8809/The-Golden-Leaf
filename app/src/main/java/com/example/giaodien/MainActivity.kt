package com.example.giaodien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.giaodien.navigation.AppNavGraph
import com.example.giaodien.ui.screens.ChonMonAnScreen
import com.example.giaodien.ui.theme.GiaoDienTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
