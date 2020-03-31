package com.naez.data.source

import com.naez.domain.Space

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveSpaces(spaces: List<Space>)
    suspend fun getSpaces(): List<Space>
    suspend fun findById(id: Int): Space
    suspend fun update(space: Space)
}