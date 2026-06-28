package com.example.beautifulstrugglefoundation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Route : NavKey {
    @Serializable
    data object Login : Route()

    @Serializable
    data object Signup : Route()

    @Serializable
    data object Home : Route()

    @Serializable
    data object SparkBoard : Route()
}
