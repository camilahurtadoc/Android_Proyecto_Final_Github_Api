package com.example.feature.pulls.data.mapper

import com.example.feature.pulls.data.dto.PullDTO
import com.example.feature.pulls.domain.model.PullModel
import org.junit.Test

class PullsMapperTest {

    private val mapper = PullsMapper()

    @Test
    fun `test repos mapper`() {
        val mappedPulls = mapper.toPullsModel(getPullsItemsDTO())
        val expectedPulls = getPullsItemsModel()
        assert(mappedPulls == expectedPulls)
    }

    private fun getPullsItemsDTO(): List<PullDTO> = listOf<PullDTO>()

    private fun getPullsItemsModel(): List<PullModel> = listOf<PullModel>()
}