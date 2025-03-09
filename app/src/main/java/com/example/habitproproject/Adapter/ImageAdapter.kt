package com.example.habitproproject.Adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(private val imagenes: List<String>, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(200, 200)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ImageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context).load(imagenes[position]).into(holder.imageView)
        holder.imageView.setOnClickListener { onClick(imagenes[position]) }
    }

    override fun getItemCount(): Int = imagenes.size
}
