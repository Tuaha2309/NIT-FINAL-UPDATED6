package com.stylo.nit.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.stylo.nit.dashboard.ArtViewModel
import com.stylo.nit.dashboard.ArtworkAdapter
import com.stylo.nit.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: ArtViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerEntities.layoutManager = LinearLayoutManager(this)

        viewModel.loadArtworks()

        viewModel.artworks.observe(this) { result ->
            result.onSuccess { data ->
                val adapter = ArtworkAdapter(data.entities)
                binding.recyclerEntities.adapter = adapter
            }.onFailure { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}