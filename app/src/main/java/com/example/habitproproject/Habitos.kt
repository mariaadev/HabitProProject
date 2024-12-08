package com.example.habitproproject

data class Habitos(
    val nombre: String,
    val descripcion: String,
    val progreso: Int,
    val tiempoEnMinutos: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val completado: Boolean,
    val imagenId: Int
)