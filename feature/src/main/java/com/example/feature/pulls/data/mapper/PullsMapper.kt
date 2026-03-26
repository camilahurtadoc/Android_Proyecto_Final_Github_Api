package com.example.feature.pulls.data.mapper

import com.example.feature.pulls.data.dto.PullDTO
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel

class PullsMapper {
    fun toPullsModel(pullsDTO: List<PullDTO>): List<PullModel> {
        return pullsDTO.map {
            PullModel(
                title = it.title,
                body = it.body,
                createdAt = it.createdAt,
                htmlUrl = it.htmlUrl,
                user = PullUserModel(
                    login = it.user.login,
                    avatarUrl = it.user.avatarUrl
                )
            )
        }
    }
}