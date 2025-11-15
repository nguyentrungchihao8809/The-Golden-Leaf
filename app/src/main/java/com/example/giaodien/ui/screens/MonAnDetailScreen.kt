package com.example.giaodien.ui.screens // (Kiểm tra lại package của anh)

// ⭐ ĐIỂM NHẤN 1: Thêm import cho hiệu ứng (Animation) và Gradient (Brush)
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.giaodien.data.model.ThucDon
import com.example.giaodien.viewmodel.MonAnDetailUiState
import com.example.giaodien.viewmodel.MonAnDetailViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.runtime.remember
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonAnDetailScreen(
    monAnId: Long,
    onBackPress: () -> Unit,
    viewModel: MonAnDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Định dạng tiền tệ
    val currencyFormatter = remember { NumberFormat.getNumberInstance(Locale("vi", "VN")) }

    Scaffold(
        floatingActionButton = {
            // ⭐ ĐIỂM NHẤN 2: Nút CTA hiển thị cả giá tiền
            // Chỉ hiển thị nút khi đã tải thành công
            if (uiState is MonAnDetailUiState.Success) {
                val thucDon = (uiState as MonAnDetailUiState.Success).thucDon
                val formattedPrice = currencyFormatter.format(thucDon.gia)

                Button(
                    onClick = { /* TODO: Thêm logic thêm vào giỏ hàng */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Thêm vào giỏ hàng",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${formattedPrice}đ",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            // TopAppBar này sẽ nằm TRÊN NỘI DUNG (ảnh)
            // và được làm trong suốt
            TopAppBar(
                title = { },
                navigationIcon = {
                    // Thêm một nền tròn mờ để nút back nổi bật hơn
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(40.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(50)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onBackPress) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Quay lại",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
        },
        // Bỏ qua padding của TopAppBar, để ảnh tràn lên
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        // ⭐ ĐIỂM NHẤN 3: Dùng Crossfade để chuyển đổi trạng thái mượt mà
        // Thay vì hiện/ẩn đột ngột, nó sẽ mờ dần
        Crossfade(
            targetState = uiState,
            label = "State Crossfade",
            modifier = Modifier
                .fillMaxSize()
                // Áp dụng padding của Scaffold (chủ yếu là cho FAB) ở đây
                // nhưng BỎ qua padding top
                .padding(
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) { state ->
            // Quyết định hiển thị gì dựa trên trạng thái
            Surface(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is MonAnDetailUiState.Loading -> {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator()
                        }
                    }
                    is MonAnDetailUiState.Error -> {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(text = "Lỗi: ${state.message}")
                        }
                    }
                    is MonAnDetailUiState.Success -> {
                        MonAnDetailContent(
                            thucDon = state.thucDon,
                            // Tính toán padding cho nội dung cuối cùng
                            // (chính là chiều cao nút + margin của nó)
                            bottomContentPadding = 56.dp + 32.dp // 56dp (nút) + 16dp*2 (margin)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MonAnDetailContent(thucDon: ThucDon, bottomContentPadding: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
        // Không dùng systemBarsPadding() ở đây nữa
        // vì TopAppBar đã xử lý (nó nằm ngoài)
    ) {
        // 1. Hình ảnh món ăn (nằm sau cùng, tràn lên top)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thucDon.anh)
                .crossfade(true)
                .build(),
            contentDescription = thucDon.tenMon,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp), // Tăng chiều cao ảnh một chút
            contentScale = ContentScale.Crop
        )

        // ⭐ ĐIỂM NHẤN 4: Lớp phủ Gradient (trên và dưới ảnh)
        // Lớp phủ trên (che status bar và giúp nút back nổi hơn)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f), // Đậm hơn ở trên
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )

        // Lớp phủ dưới (giúp thẻ Card chuyển tiếp mượt mà)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface // Màu nền của Card
                        )
                    )
                )
                .align(Alignment.BottomCenter)
                // Dịch chuyển lớp phủ này lên trên một chút để nó bắt đầu
                // trước khi thẻ Card bắt đầu
                .offset(y = (50).dp) // Dịch lên 50dp
        )


        // 2. Thẻ nội dung (nằm phía trên, có thể cuộn)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Spacer để "đẩy" thẻ nội dung xuống, tạo hiệu ứng overlap
            Spacer(modifier = Modifier.height(350.dp)) // = Chiều cao ảnh - 50dp overlap

            // Nội dung (Không dùng Card nữa, chỉ dùng Column với nền)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 500.dp)
                    // Bo góc trên
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(24.dp), // Padding lớn hơn
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = thucDon.tenMon,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(thucDon.gia)
                Text(
                    text = "${formattedPrice}đ",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "Mô tả",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = thucDon.moTa,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 26.sp
                )

                // Spacer ở cuối để đảm bảo nội dung không bị nút CTA che
                Spacer(modifier = Modifier.height(bottomContentPadding))
            }
        }
    }
}
