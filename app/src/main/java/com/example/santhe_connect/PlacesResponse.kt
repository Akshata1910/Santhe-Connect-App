package com.example.santhe_connect

data class PlacesResponse(
    val results: List<GooglePlace>
)

data class GooglePlace(
    val name: String,
    val vicinity: String?,
    val rating: Double?
)