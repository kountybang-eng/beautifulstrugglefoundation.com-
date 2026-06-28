package com.example.beautifulstrugglefoundation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautifulstrugglefoundation.data.repository.VehicleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = VehicleRepository()

    private val _impactPoints = MutableStateFlow(0)
    val impactPoints = _impactPoints.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        refreshImpact()
    }

    fun refreshImpact() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _impactPoints.value = repository.getTotalImpactPoints()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }
}
