package com.example.habitproproject.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habitos(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
    val progreso: Int,
    val tiempoEnMinutos: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val completado: Boolean,
    val imagenId: Int,
): Parcelable