package com.example.comparepharma.model.dto

data class SearchAprilDTO(
    val ID: Int?,
    val price: PriceAprilDTO?,
    val name: String?,
    val isRecipe: Boolean?,
    val description: Array<DescriptionAprilDTO?>,
    val properties: Array<PropertiesAprilDTO?>,
    val images: Array<String?>
)