package com.example.feature.pulls.data.service

import com.example.feature.pulls.data.dto.PullDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PullsService {

    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getPulls(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<PullDTO>
}