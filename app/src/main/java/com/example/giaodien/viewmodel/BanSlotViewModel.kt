package com.example.giaodien.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giaodien.data.model.BanSlot
import com.example.giaodien.data.network.RetrofitInstance
import com.example.giaodien.data.repository.BanSlotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BanSlotViewModel : ViewModel() {

    private val _slots = MutableStateFlow<List<BanSlot>>(emptyList())
    val slots: StateFlow<List<BanSlot>> = _slots

    init {
        viewModelScope.launch {
            try {
                val fetched = RetrofitInstance.api.getBanSlots() // endpoint trả về danh sách BanSlot
                _slots.value = fetched
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSlotById(id: Long) = _slots.value.find { it.id == id }
}
