package com.example.habitproproject.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.Activity.DetalleHabitoActivity
import com.example.habitproproject.Adapter.HabitosAdapter.HabitosViewHolder
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R

class TusHabitosAdapter(
    private var listaHabitos: List<Habitos>
) : RecyclerView.Adapter<TusHabitosAdapter.HabitosViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TusHabitosAdapter.HabitosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return HabitosViewHolder(view)
    }

    override fun getItemCount(): Int = listaHabitos.size

    override fun onBindViewHolder(holder: TusHabitosAdapter.HabitosViewHolder, position: Int) {
        val habito = listaHabitos[position]
        holder.bind(habito)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalleHabitoActivity::class.java).apply {
                putExtra("habito", habito)
            }
            context.startActivity(intent)
        }
    }

    fun actualizarLista(nuevaLista: List<Habitos>) {
        listaHabitos = nuevaLista
        notifyDataSetChanged() // Notificar que la lista ha cambiado
    }

    class HabitosViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNombreHabit: TextView = itemView.findViewById(R.id.textNombreHabit)
        private val imageHabit: ImageView = itemView.findViewById(R.id.imageHabit)
        fun bind(habito: Habitos) {
            textNombreHabit.text = habito.nombre
            imageHabit.setImageResource(habito.imagenId)

            val layoutParams = imageHabit.layoutParams
            layoutParams.width = 50  // ancho en píxeles
            layoutParams.height = 50 // alto en píxeles
            imageHabit.layoutParams = layoutParams
        }

    }
}