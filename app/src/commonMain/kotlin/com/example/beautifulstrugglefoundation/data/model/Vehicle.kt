package com.example.beautifulstrugglefoundation.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id: String,
    val make: String,
    val model: String,
    val year: Int,
    val condition: String,
    val blueBookValue: Double,
    val foundationPrice: Double,
    val monthlyPayment: Double,
    val status: VehicleInventoryStatus,
    val imageUrl: String? = null,
    val repairNotes: String? = null
)

@Serializable
enum class VehicleInventoryStatus {
    AVAILABLE, IN_REPAIR, SOON, DONATED
}

@Serializable
data class VehicleSubmission(
    val id: String? = null,
    val scoutId: String,
    val make: String,
    val model: String,
    val year: Int? = null,
    val color: String? = null,
    val condition: String,
    val location: String? = null, // Sensitive data
    val status: VehicleStatus = VehicleStatus.NEW,
    val adminNotes: String? = null,
    val blueBookValue: Double? = null
)

@Serializable
enum class VehicleStatus {
    NEW, CHECKED, NEGOTIATION, ACQUIRED, IN_REPAIR, READY, DONATED
}
