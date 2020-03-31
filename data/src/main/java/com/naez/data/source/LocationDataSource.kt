package com.naez.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}