package com.example.bonial_brochure.network

import com.example.bonial_brochure.data.ShelfResponse
import retrofit2.http.GET

interface ApiService {

    @GET("shelf.json")
    suspend fun getBrochures(): ShelfResponse
}