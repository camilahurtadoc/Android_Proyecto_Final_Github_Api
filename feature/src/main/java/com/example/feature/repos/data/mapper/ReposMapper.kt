package com.example.feature.repos.data.mapper

import com.example.feature.repos.data.dto.ReposDTO
import com.example.feature.repos.data.dto.ReposItemsDTO
import com.example.feature.repos.data.dto.ReposOwnerDTO
import com.example.feature.repos.domain.model.ReposItemsModel
import com.example.feature.repos.domain.model.ReposModel
import com.example.feature.repos.domain.model.ReposOwnerModel

class ReposMapper {

    fun toReposItemsModel(reposItemsDTO: ReposItemsDTO): ReposItemsModel {
        return ReposItemsModel(
            items = reposItemsDTO.items.map {
                it.let(::toReposModel)
            }
        )
    }

    private fun toReposModel(reposDTO: ReposDTO): ReposModel {
        return ReposModel(
            name = reposDTO.name,
            description = reposDTO.description,
            stargazersCount = reposDTO.stargazersCount,
            forksCount = reposDTO.forksCount,
            owner = reposDTO.owner.let(::toReposOwnerModel)
        )
    }

    private fun toReposOwnerModel(reposOwnerDTO: ReposOwnerDTO): ReposOwnerModel {
        return ReposOwnerModel(
            login = reposOwnerDTO.login,
            avatarUrl = reposOwnerDTO.avatarUrl
        )
    }
}
