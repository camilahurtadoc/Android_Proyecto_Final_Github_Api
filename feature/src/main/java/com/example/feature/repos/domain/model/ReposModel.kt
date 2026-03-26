package com.example.feature.repos.domain.model

data class ReposModel(
    val name: String,
    val description: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val owner: ReposOwnerModel
)
