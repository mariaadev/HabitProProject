package com.example.habitproproject.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R

class HabitosAdapter(
    private val listaHabitos: List<Habitos>
) : RecyclerView.Adapter<HabitosAdapter.HabitosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habito, parent, false)
        return HabitosViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitosViewHolder, position: Int) {
        val habito = listaHabitos[position]
        holder.bind(habito)
    }

    override fun getItemCount(): Int = listaHabitos.size

    class HabitosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNombreHabit: TextView = itemView.findViewById(R.id.textNombreHabit)
        private val textDescripcion: TextView = itemView.findViewById(R.id.textDescripcion)
        private val imageHabit: ImageView = itemView.findViewById(R.id.imageHabit)

        fun bind(habito: Habitos) {
            textNombreHabit.text = habito.nombre
            textDescripcion.text = habito.descripcion
            imageHabit.setImageResource(habito.imagenId)
        }
    }
}
