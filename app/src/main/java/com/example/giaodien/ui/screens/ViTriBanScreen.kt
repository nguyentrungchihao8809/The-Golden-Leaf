package com.example.giaodien.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@Composable
fun ViTriBanScreen(
    banConLai: Int,
    onBack: () -> Unit,
    onNext: (viTriBan: String) -> Unit
) {
    val danhSachViTri = listOf("Ngoài trời", "Tầng thượng", "Cửa sổ", "Phòng riêng", "Bờ sông")
    var viTriDaChon by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chọn vị trí bàn", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        danhSachViTri.forEach { viTri ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viTriDaChon = viTri }
                    .padding(12.dp)
            ) {
                RadioButton(selected = viTriDaChon == viTri, onClick = { viTriDaChon = viTri })
                Text(viTri)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNext(viTriDaChon) },
            enabled = viTriDaChon.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tiếp tục")
        }
    }
}
