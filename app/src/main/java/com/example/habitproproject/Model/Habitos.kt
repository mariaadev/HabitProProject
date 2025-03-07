package com.example.habitproproject.Model

data class Habitos(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
    val progreso: Int,
    val tiempoEnMinutos: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val completado: Boolean,
    val imagenId: Int
)