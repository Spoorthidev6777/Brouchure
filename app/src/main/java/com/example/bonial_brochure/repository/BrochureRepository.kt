package com.example.bonial_brochure.repository

import com.example.bonial_brochure.data.Brochure
import com.example.bonial_brochure.data.ContentData
import com.example.bonial_brochure.network.ApiService
import com.google.gson.Gson
import com.google.gson.JsonElement
import javax.inject.Inject

class BrochureRepository @Inject constructor(
    private val apiService: ApiService
) {

    private val gson = Gson()

    suspend fun getFilteredBrochures(): List<Brochure> {
        val response = apiService.getBrochures()
        return response._embedded.contents
            .filter { it.contentType == "brochure" || it.contentType == "brochurePremium" }
            .flatMap { content ->
                val brochures = parseContent(content.content)
                brochures.mapNotNull { brochureContent ->
                    if (brochureContent.brochureImage != null && brochureContent.publisher?.name != null) {
                        Brochure(
                            imageUrl = brochureContent.brochureImage,
                            retailerName = brochureContent.publisher.name
                        )
                    } else {
                        null
                    }
                }
            }
    }

    private fun parseContent(content: JsonElement): List<ContentData> {
        return if (content.isJsonArray) {
            gson.fromJson(content, Array<ContentData>::class.java).toList()
        } else {
            listOf(gson.fromJson(content, ContentData::class.java))
        }
    }
}
