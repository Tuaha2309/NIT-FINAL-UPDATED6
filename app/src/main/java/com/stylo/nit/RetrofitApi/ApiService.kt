package com.stylo.nit.RetrofitApi


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST("auth")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET
    suspend fun getArtList(@Url fullUrl: String): ArtResponse
}





