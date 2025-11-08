package com.example.giaodien.navigation
import com.example.giaodien.data.network.RetrofitInstance
import com.example.giaodien.viewmodel.GioHangViewModelFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.giaodien.ui.screens.*
import com.example.giaodien.viewmodel.BanSlotViewModel
import com.example.giaodien.viewmodel.DatBanViewModel
import com.example.giaodien.data.model.DatBan
import com.google.firebase.auth.FirebaseAuth
import com.example.giaodien.viewmodel.GioHangViewModel
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
    object ChonMonAn : Screen("chon_mon_an")
    object NgayGio : Screen("ngay_gio")

    object SoDoBan : Screen("soDoBan/{slotId}/{ngayChon}/{khungGioChon}") {
        fun createRoute(slotId: Long, ngayChon: String, khungGioChon: String) =
            "soDoBan/$slotId/$ngayChon/$khungGioChon"
    }

    object ViTriBan : Screen("vi_tri_ban/{ngayChon}/{khungGioChon}/{banConLai}") {
        fun createRoute(ngayChon: String, khungGioChon: String, banConLai: Int) =
            "vi_tri_ban/$ngayChon/$khungGioChon/$banConLai"
    }

    object NhapSoLuong :
        Screen("nhap_so_luong/{ngayChon}/{khungGioChon}/{viTriBan}/{banConLai}") {
        fun createRoute(
            ngayChon: String,
            khungGioChon: String,
            viTriBan: String,
            banConLai: Int
        ) = "nhap_so_luong/$ngayChon/$khungGioChon/$viTriBan/$banConLai"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val banSlotViewModel: BanSlotViewModel = viewModel()
    val datBanViewModel: DatBanViewModel = viewModel()
    val apiService = RetrofitInstance.api
    val gioHangViewModel: GioHangViewModel = viewModel(
        factory = GioHangViewModelFactory(apiService)
    )
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {}
            )
        }

        // Main
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }

        // NgayGio
        composable(Screen.NgayGio.route) {
            NgayGioScreen(viewModel = banSlotViewModel, navController = navController)
        }

        // SoDoBan
        composable(
            route = Screen.SoDoBan.route,
            arguments = listOf(
                navArgument("slotId") { type = NavType.LongType },
                navArgument("ngayChon") { type = NavType.StringType },
                navArgument("khungGioChon") { type = NavType.StringType }
            )
        ) { entry ->
            val slotId = entry.arguments?.getLong("slotId") ?: 0L
            val ngayChon = entry.arguments?.getString("ngayChon") ?: ""
            val khungGioChon = entry.arguments?.getString("khungGioChon") ?: ""
            val slot = banSlotViewModel.getSlotById(slotId)
            if (slot != null) {
                SoDoBanScreen(
                    slot = slot,
                    navController = navController,
                    ngayChon = ngayChon,
                    khungGioChon = khungGioChon
                )
            }
        }

        // ViTriBan
        composable(
            route = Screen.ViTriBan.route,
            arguments = listOf(
                navArgument("ngayChon") { type = NavType.StringType },
                navArgument("khungGioChon") { type = NavType.StringType },
                navArgument("banConLai") { type = NavType.IntType }
            )
        ) { entry ->
            val ngayChon = entry.arguments?.getString("ngayChon") ?: ""
            val khungGioChon = entry.arguments?.getString("khungGioChon") ?: ""
            val banConLai = entry.arguments?.getInt("banConLai") ?: 0

            ViTriBanScreen(
                banConLai = banConLai,
                onBack = { navController.popBackStack() },
                onNext = { viTriBan ->
                    navController.navigate(
                        Screen.NhapSoLuong.createRoute(
                            ngayChon = ngayChon,
                            khungGioChon = khungGioChon,
                            viTriBan = viTriBan,
                            banConLai = banConLai
                        )
                    )
                }
            )
        }

        // NhapSoLuong
        composable(
            route = Screen.NhapSoLuong.route,
            arguments = listOf(
                navArgument("ngayChon") { type = NavType.StringType },
                navArgument("khungGioChon") { type = NavType.StringType },
                navArgument("viTriBan") { type = NavType.StringType },
                navArgument("banConLai") { type = NavType.IntType }
            )
        ) { entry ->
            val ngayChon = entry.arguments?.getString("ngayChon") ?: ""
            val khungGioChon = entry.arguments?.getString("khungGioChon") ?: ""
            val viTriBan = entry.arguments?.getString("viTriBan") ?: ""
            val banConLai = entry.arguments?.getInt("banConLai") ?: 0

            NhapSoLuongScreen(
                navController = navController,
                ngayChon = ngayChon,
                khungGioChon = khungGioChon,
                viTriBan = viTriBan,
                banConLai = banConLai,
                onDatBan = { soLuong, ghiChu ->
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val datBan = DatBan(
                        idDat = null,
                        email = currentUser?.email ?: "",
                        ten = currentUser?.displayName ?: "Khách",
                        ngay = ngayChon,
                        khungGio = khungGioChon,
                        soLuong = soLuong,
                        ghiChu = ghiChu,
                        viTriBan = viTriBan
                    )

                    datBanViewModel.datBan(
                        datBan = datBan,
                        onSuccess = { savedDatBan ->
                            val datBanId = savedDatBan.idDat
                            if (datBanId != null) {
                                gioHangViewModel.setDatBanId(datBanId) // ⚡ cập nhật idDat cho giỏ hàng
                            }
                            navController.navigate(Screen.ChonMonAn.route)
                        },
                        onError = { msg ->
                            Log.e("DatBan", "Lỗi khi đặt bàn: $msg")
                        }
                    )
                }
            )
        }
        // ChonMonAn
// ChonMonAn
        composable(Screen.ChonMonAn.route) {
            // TRUYỀN INSTANCE CHUNG
            ChonMonAnScreen(
                navController = navController,
                gioHangViewModel = gioHangViewModel
            )
        }

        composable("gio_hang_screen") {
            GioHangScreen(
                navController = navController,
                gioHangViewModel = gioHangViewModel // dùng instance đã tạo ở đầu NavGraph
            )
        }


    }
}
