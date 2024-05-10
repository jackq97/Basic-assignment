package com.example.basicassignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://blonlrveqfsbvgrlfgqb.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJsb25scnZlcWZzYnZncmxmZ3FiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUyNDMwNTIsImV4cCI6MjAzMDgxOTA1Mn0.gpgoJUkUpFwimPXHv0ADPsOLdDtYeHIDzrhhkwz5XZo"
        ) {
            install(Postgrest)
        }
    }
}