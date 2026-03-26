package com.example.feature.repos.data.dto

import com.google.gson.annotations.SerializedName

data class ReposItemsDTO(
    @SerializedName("items") val items: List<ReposDTO>
)
