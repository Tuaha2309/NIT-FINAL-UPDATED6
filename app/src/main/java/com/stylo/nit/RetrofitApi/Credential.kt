package com.stylo.nit.RetrofitApi

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val keypass: String
)

data class ArtResponse(
    val entities: List<Artwork>,
    val entityTotal: Int
)

data class Artwork(
    val artworkTitle: String,
    val artist: String,
    val medium: String,
    val year: Int,
    val description: String
)

