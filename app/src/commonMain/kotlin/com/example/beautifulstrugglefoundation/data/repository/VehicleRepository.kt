package com.example.beautifulstrugglefoundation.data.repository

import com.example.beautifulstrugglefoundation.data.model.Vehicle
import com.example.beautifulstrugglefoundation.data.model.VehicleSubmission
import com.example.beautifulstrugglefoundation.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VehicleRepository {

    suspend fun getInventory(): List<Vehicle> {
        return supabase.from("vehicles").select().decodeList<Vehicle>()
    }

    suspend fun getSubmissions(): List<VehicleSubmission> {
        // Submissions might contain sensitive location data
        return supabase.from("submissions").select().decodeList<VehicleSubmission>()
    }

    suspend fun getTotalImpactPoints(): Int {
        // Logic to calculate impact points based on vehicles donated and submissions
        // For now, let's just return a placeholder or sum some values
        val donatedCount = supabase.from("vehicles").select {
            filter {
                eq("status", "DONATED")
            }
        }.decodeList<Vehicle>().size
        
        val submissionCount = supabase.from("submissions").select().decodeList<VehicleSubmission>().size
        
        return (donatedCount * 100) + (submissionCount * 10)
    }
}
