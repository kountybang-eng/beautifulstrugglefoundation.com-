package com.example.beautifulstrugglefoundation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautifulstrugglefoundation.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                println("Attempting login for: $email")
                supabase.auth.signInWith(Email) {
                    this.email = email.trim()
                    this.password = pass.trim()
                }
                println("Login successful")
                _success.value = true
            } catch (e: Exception) {
                println("Login failed: ${e.message}")
                e.printStackTrace()
                _error.value = e.message ?: "Login failed"
            } finally {
                _loading.value = false
            }
        }
    }

    fun signUp(email: String, pass: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                println("Attempting signup for: $email")
                supabase.auth.signUpWith(Email) {
                    this.email = email.trim()
                    this.password = pass.trim()
                }
                println("Signup successful")
                _success.value = true
            } catch (e: Exception) {
                println("Signup failed: ${e.message}")
                e.printStackTrace()
                _error.value = e.message ?: "Signup failed"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearSuccess() {
        _success.value = false
    }
}
