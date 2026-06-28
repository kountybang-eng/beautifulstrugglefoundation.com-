package com.example.beautifulstrugglefoundation

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

// Configuration for Supabase
object SupabaseConfig {
    const val URL = "https://lvddcidqxqofdputxrex.supabase.co"
    const val ANON_KEY = "lvddcidqxqofdputxrexsb_publishable_-F-RS0EEc-yR30sB0dRrCg_Ls8jA9AN"
}

val supabase by lazy {
    createSupabaseClient(
        supabaseUrl = SupabaseConfig.URL,
        supabaseKey = SupabaseConfig.ANON_KEY
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime)
    }
}
