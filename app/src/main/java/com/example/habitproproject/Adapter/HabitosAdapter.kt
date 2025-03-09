package com.example.habitproproject.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitproproject.Activity.DetalleHabitoActivity
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R

class HabitosAdapter(
    private var listaHabitos: List<Habitos>
) : RecyclerView.Adapter<HabitosAdapter.HabitosViewHolder>() {

    fun actualizarListaHabitos(nuevaLista: List<Habitos>) {
        listaHabitos = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habito, parent, false)
        return HabitosViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitosViewHolder, position: Int) {
        val habito = listaHabitos[position]
        holder.bind(habito)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalleHabitoActivity::class.java).apply {
                putExtra("nombre", habito.nombre)
                putExtra("duracion", habito.descripcion)
                putExtra("progreso", habito.progreso)
                putExtra("completado", habito.completado)
                putExtra("fechaInicio", habito.fechaInicio)
                putExtra("fechaFin", habito.fechaFin)
                putExtra("imagenId", habito.imagenId)
                putExtra("tiempoEnMinutos", habito.tiempoEnMinutos)

            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaHabitos.size

    class HabitosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNombreHabit: TextView = itemView.findViewById(R.id.textNombreHabit)
        private val textDescripcion: TextView = itemView.findViewById(R.id.textDescripcion)
        private val imageHabit: ImageView = itemView.findViewById(R.id.imageHabit)

        fun bind(habito: Habitos) {
            textNombreHabit.text = habito.nombre
            textDescripcion.text = habito.descripcion
            if (habito.imagenId.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(habito.imagenId)
                    .placeholder(R.drawable.subir_imagen)
                    .error(R.drawable.ic_clear)
                    .into(imageHabit)
            } else {
                imageHabit.setImageResource(R.drawable.subir_imagen)
            }
        }
    }
}
