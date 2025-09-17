package com.stylo.nit.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stylo.nit.RetrofitApi.ArtRepository
import com.stylo.nit.RetrofitApi.ArtResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _artworks = MutableLiveData<Result<ArtResponse>>()
    val artworks: LiveData<Result<ArtResponse>> get() = _artworks

    fun loadArtworks() {
        val prefs = getApplication<Application>().getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val keypass = prefs.getString("KEYPASS", null)

        if (keypass.isNullOrEmpty()) {
            _artworks.value = Result.failure(Exception("Keypass not found in SharedPreferences"))
            return
        }

        viewModelScope.launch {
            try {
                val result = repository.fetchArtworksWithKeypass(keypass)
                _artworks.value = result
            } catch (e: Exception) {
                _artworks.value = Result.failure(e)
            }
        }
    }
}