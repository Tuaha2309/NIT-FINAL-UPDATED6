package com.stylo.nit.RetrofitApi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchArtworksWithKeypass(keypass: String): Result<ArtResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val fullUrl = "https://nit3213api.onrender.com/dashboard/$keypass"
                val response = apiService.getArtList(fullUrl)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}