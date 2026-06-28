package com.example.beautifulstrugglefoundation.ui.spark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautifulstrugglefoundation.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.*

class DailySparkViewModel : ViewModel() {
    
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting = _isSubmitting.asStateFlow()

    @OptIn(io.github.jan.supabase.annotations.SupabaseExperimental::class)
    val sparks: StateFlow<List<DailySpark>> = supabase.from("daily_sparks")
        .selectAsFlow(DailySpark::id)
        .map { list -> list.sortedByDescending { it.created_at } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalTime::class)
    fun submitSpark(name: String, content: String, onComplete: () -> Unit) {
        if (content.isBlank()) return

        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val spark = DailySpark(
                    author_name = name.ifBlank { "Anonymous" },
                    content = content,
                    created_at = Clock.System.now().toString()
                )
                supabase.from("daily_sparks").insert(spark)
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isSubmitting.value = false
            }
        }
    }
}
