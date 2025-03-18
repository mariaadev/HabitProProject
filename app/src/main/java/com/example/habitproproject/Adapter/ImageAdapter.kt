package com.example.habitproproject.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitproproject.R

class ImageAdapter(private val imagenes: List<String>, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var imagenSeleccionada: String? = null

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivImagen)
        val overlayView: View = view.findViewById(R.id.seleccionadoOverlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imagenes[position]
        Glide.with(holder.imageView.context).load(imagenes[position]).into(holder.imageView)
        if (imageUrl == imagenSeleccionada) {
            holder.overlayView.visibility = View.VISIBLE
        } else {
            holder.overlayView.visibility = View.GONE
        }

        holder.imageView.setOnClickListener {
            imagenSeleccionada = imageUrl
            notifyDataSetChanged()
            onClick(imageUrl)
        }
    }

    override fun getItemCount(): Int = imagenes.size
}
