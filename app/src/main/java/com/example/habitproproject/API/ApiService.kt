package com.example.habitproproject.API

import com.example.habitproproject.Model.Habitos
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("habitos")
    suspend fun getHabitos(): List<Habitos>

    @POST("habitos")
    suspend fun createHabitos(@Body nuevoHabito: Habitos): Habitos

    @PUT("habitos/{id}")
    suspend fun updateHabito(@Path("id") id: Int, @Body habito: Habitos): Habitos

    @DELETE("habitos/{id}")
    suspend fun deleteHabito(@Path("id") id: Int): Unit
}

