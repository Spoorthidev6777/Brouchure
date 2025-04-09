package com.example.bonial_brochure.data

import com.google.gson.JsonElement

data class ShelfResponse(
    val _embedded: EmbeddedContents
)

data class EmbeddedContents(
    val contents: List<Content>
)

data class Content(
    val contentType: String,
    val content: JsonElement
)
