package com.example.feature.repos.data.dto

import com.google.gson.annotations.SerializedName

data class ReposDTO(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("owner") val owner: ReposOwnerDTO,
)
