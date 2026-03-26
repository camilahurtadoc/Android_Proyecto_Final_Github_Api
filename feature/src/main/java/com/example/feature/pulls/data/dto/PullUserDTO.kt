package com.example.feature.pulls.data.dto

import com.google.gson.annotations.SerializedName

data class PullUserDTO(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
