package com.example.habitproproject.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.Model.DiaCalendario
import com.example.habitproproject.R

class CalendarAdapter(
    private val days: List<DiaCalendario>,
    private val onDayClick: (Int) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendarDay = days[position]
        holder.dayTextView.text = calendarDay.day.toString()

        holder.checkBox.isChecked = calendarDay.isChecked

        holder.itemView.setOnClickListener {
            /*Cambiar el estado del check cuando el usuario haga clic*/
            calendarDay.isChecked = !calendarDay.isChecked
            holder.checkBox.isChecked = calendarDay.isChecked
            onDayClick(calendarDay.day)
        }
    }

    override fun getItemCount(): Int = days.size

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}
