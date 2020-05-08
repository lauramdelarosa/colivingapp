package com.naez.data.repository

import com.naez.data.ResultData
import com.naez.data.source.LocalDataSource
import com.naez.data.source.RemoteDataSource
import com.naez.domain.Space

class SpaceRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getSpaces(): ResultData<List<Space>> {
        if (localDataSource.isEmpty()) {
            when (val result = remoteDataSource.getColivingList()) {
                is ResultData.Success -> localDataSource.saveSpaces(result.data)
                is ResultData.Error -> return result
            }
        }
        return ResultData.Success(localDataSource.getSpaces())
    }

    suspend fun findById(id: Int): Space = localDataSource.findById(id)

    suspend fun update(space: Space) = localDataSource.update(space)
}