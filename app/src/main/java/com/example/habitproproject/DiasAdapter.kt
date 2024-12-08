package com.example.habitproproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiasAdapter(private val listaDias: List<Dia>) : RecyclerView.Adapter<DiasAdapter.DiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dia, parent, false)
        return DiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaViewHolder, position: Int) {
        val dia = listaDias[position]
        holder.tvNombreAbreviado.text = dia.nombreAbreviado
        holder.tvNumeroDia.text = dia.numeroDia.toString()
    }

    override fun getItemCount(): Int {
        return listaDias.size
    }

    class DiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreAbreviado: TextView = itemView.findViewById(R.id.tvNombreAbreviado)
        val tvNumeroDia: TextView = itemView.findViewById(R.id.tvNumeroDia)
    }
}