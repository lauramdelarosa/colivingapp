package com.naez.colivingapp.data.server.source

import com.naez.colivingapp.data.mappers.toDomainSpace
import com.naez.colivingapp.data.server.callServices
import com.naez.colivingapp.data.server.endpoints.SpaceService
import com.naez.colivingapp.data.server.response.SpaceResult
import com.naez.colivingapp.data.server.safeApiCall
import com.naez.data.ResultData
import com.naez.data.source.RemoteDataSource
import com.naez.domain.Constans
import com.naez.domain.Space
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSpaceDataSource(private val spaceService: SpaceService) : RemoteDataSource {

    override suspend fun getcolivingList(): ResultData<List<Space>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = {
                renderData(
                    spaceService.listColivingAsync().callServices()
                )
            },
            errorMessage = " something failed calling service '../coliving'"
        )
    }

    private fun renderData(resultData: ResultData<SpaceResult>): ResultData<List<Space>> =
        when (resultData) {
            is ResultData.Success -> ResultData.Success(resultData.data.results.map { it.toDomainSpace() })
            is ResultData.Error -> resultData
        }


}