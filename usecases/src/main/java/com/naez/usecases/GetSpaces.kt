package com.naez.usecases

import com.naez.data.ResultData
import com.naez.data.repository.SpaceRepository
import com.naez.domain.Space

class GetSpaces(private val spaceRepository: SpaceRepository) {
    suspend fun invoke(): ResultData<List<Space>> = spaceRepository.getSpaces()
}