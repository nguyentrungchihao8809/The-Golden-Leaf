package com.example.giaodien.data.repository

import com.example.giaodien.data.model.DatBan
import com.example.giaodien.data.network.RetrofitInstance

class DatBanRepository {

    suspend fun datBan(datBan: DatBan): DatBan {
        return RetrofitInstance.api.createDatBan(datBan)
    }
}
