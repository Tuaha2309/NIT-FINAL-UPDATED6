package com.stylo.nit.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stylo.nit.databinding.ActivityArtDetailBinding

class ArtDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val title = intent.getStringExtra("title")
        val artist = intent.getStringExtra("artist")
        val medium = intent.getStringExtra("medium")
        val year = intent.getIntExtra("year", 0)
        val description = intent.getStringExtra("description")

        // Bind to UI
        binding.tvDetailTitle.text = title
        binding.tvDetailMuscleGroup.text = "Muscle group: $artist"
        binding.tvDetailEquipment.text = "Equipment: $medium"
        binding.tvDetailDifficulty.text = "Difficulty: $year"
        binding.tvDetailCalories.text = "Calories burned per hour: $description"
        binding.tvDetailDescription.text = description
    }
}