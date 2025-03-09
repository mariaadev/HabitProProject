package com.example.habitproproject.Model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaCalendario(
    val day: Int,
    var isChecked: Boolean
): Parcelable
