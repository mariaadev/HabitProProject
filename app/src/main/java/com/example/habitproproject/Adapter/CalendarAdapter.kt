package com.example.habitproproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.Model.DiaCalendario
import com.example.habitproproject.R

class CalendarAdapter(
    private val context: Context,
    private val habitId: String,
    private val days: List<DiaCalendario>
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    init {
        // Cargar días seleccionados desde SharedPreferences al iniciar el adapter
        val selectedDays = loadSelectedDays(context, habitId)
        days.forEach { it.isChecked = it.day in selectedDays }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendarDay = days[position]
        holder.dayTextView.text = calendarDay.day.toString()

        // Configurar el estado del CheckBox
        holder.checkBox.isChecked = calendarDay.isChecked

        // Manejar clic en el CheckBox
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            calendarDay.isChecked = isChecked
            saveSelectedDays(context, habitId, days)
        }
    }

    override fun getItemCount(): Int = days.size

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    // Función para guardar los días seleccionados en SharedPreferences
    private fun saveSelectedDays(context: Context, habitId: String, selectedDays: List<DiaCalendario>) {
        val sharedPreferences = context.getSharedPreferences("HabitPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val selectedDaysSet = selectedDays.filter { it.isChecked }.map { it.day.toString() }.toSet()
        editor.putStringSet("habit_${habitId}_days", selectedDaysSet)
        editor.apply()
    }

    // Función para cargar los días seleccionados desde SharedPreferences
    private fun loadSelectedDays(context: Context, habitId: String): List<Int> {
        val sharedPreferences = context.getSharedPreferences("HabitPreferences", Context.MODE_PRIVATE)
        val selectedDaysSet = sharedPreferences.getStringSet("habit_${habitId}_days", emptySet()) ?: emptySet()

        return selectedDaysSet.map { it.toInt() }
    }
}

