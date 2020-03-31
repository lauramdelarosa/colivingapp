package com.naez.usecases

import com.naez.data.repository.SpaceRepository
import com.naez.domain.Space

class FindSpaceById(private val spaceRepository: SpaceRepository) {
    suspend fun invoke(id: Int): Space = spaceRepository.findById(id)
}