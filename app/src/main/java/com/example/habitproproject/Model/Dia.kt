package com.example.habitproproject.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dia(
    val nombreAbreviado: String,
    val numeroDia: Int
): Parcelable