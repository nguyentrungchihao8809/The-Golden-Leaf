package com.example.giaodien.data.repository

import com.example.giaodien.data.model.ThucDon
import javax.inject.Inject
import javax.inject.Singleton

interface MonAnRepository {
    suspend fun getAllThucDon(): List<ThucDon>
    suspend fun getMonAnById(id: Long): ThucDon?
}
