package com.naez.data.source

import com.naez.data.ResultData
import com.naez.domain.Space

interface RemoteDataSource {
    suspend fun getcolivingList(): ResultData<List<Space>>
}