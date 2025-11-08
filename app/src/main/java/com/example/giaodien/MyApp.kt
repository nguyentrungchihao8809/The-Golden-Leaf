package com.example.giaodien

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Khởi tạo ThreeTenABP
        AndroidThreeTen.init(this)
    }
}
