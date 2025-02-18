package com.example.habitproproject.API

import com.example.habitproproject.Model.Habitos
import retrofit2.Call
import retrofit2.http.GET

interface HabitAPIService {

    @GET("habitos/1")
    fun getHabito(): Call<Habitos>
}