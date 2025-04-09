package com.example.bonial_brochure.data

data class ContentData(
    val brochureImage: String?,
    val publisher: Publisher?
)

data class Publisher(
    val id: String,
    val name: String
)

