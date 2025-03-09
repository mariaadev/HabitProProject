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

class TusHabitosAdapter(
    private val listaHabitos: MutableList<Habitos>
) : RecyclerView.Adapter<TusHabitosAdapter.HabitosViewHolderMañana>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TusHabitosAdapter.HabitosViewHolderMañana {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return HabitosViewHolderMañana(view)
    }

    override fun getItemCount(): Int = listaHabitos.size

    override fun onBindViewHolder(holder: TusHabitosAdapter.HabitosViewHolderMañana, position: Int) {
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
    fun actualizarLista(nuevaLista: List<Habitos>) {
        listaHabitos.clear()
        listaHabitos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
    class HabitosViewHolderMañana (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNombreHabit: TextView = itemView.findViewById(R.id.textNombreHabit)
        private val imageHabit: ImageView = itemView.findViewById(R.id.imageHabit)
        fun bind(habito: Habitos) {
            textNombreHabit.text = habito.nombre
            Glide.with(itemView.context).load(habito.imagenId).into(imageHabit)

            val layoutParams = imageHabit.layoutParams
                layoutParams.width = 50
                layoutParams.height = 50
                imageHabit.layoutParams = layoutParams
        }

    }
}