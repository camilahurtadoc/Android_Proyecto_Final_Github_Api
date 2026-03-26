package com.example.feature.repos.data.dto

import com.google.gson.annotations.SerializedName

data class ReposOwnerDTO(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)
