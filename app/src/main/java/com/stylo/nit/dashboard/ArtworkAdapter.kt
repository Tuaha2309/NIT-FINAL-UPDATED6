package com.stylo.nit.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stylo.nit.detail.ArtDetailActivity
import com.stylo.nit.RetrofitApi.Artwork
import com.stylo.nit.databinding.ItemArtworkBinding

class ArtworkAdapter(
    private val artworks: List<Artwork>
) : RecyclerView.Adapter<ArtworkAdapter.ArtViewHolder>() {

    inner class ArtViewHolder(val binding: ItemArtworkBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkBinding.inflate(inflater, parent, false)
        return ArtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val artwork = artworks[position]
        holder.binding.apply {
            tvExerciseName.text = artwork.artworkTitle
            tvMuscleGroup.text = "Muscle: ${artwork.artist}"
            chipDifficulty.text = artwork.medium
            chipCalories.text = "${artwork.year} kcal/h"
            tvEquipment.text = "Equipment: ${artwork.description}"
        }

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, ArtDetailActivity::class.java).apply {
                putExtra("title", artwork.artworkTitle)
                putExtra("artist", artwork.artist)
                putExtra("medium", artwork.medium)
                putExtra("year", artwork.year)
                putExtra("description", artwork.description)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = artworks.size
}