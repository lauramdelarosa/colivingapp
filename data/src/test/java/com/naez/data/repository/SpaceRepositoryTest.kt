package com.naez.data.repository

import com.naez.data.ResultData
import com.naez.data.source.LocalDataSource
import com.naez.data.source.RemoteDataSource
import com.naez.testshared.mockedSpace
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SpaceRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var spaceRepository: SpaceRepository

    @Before
    fun setUp() {
        spaceRepository =
            SpaceRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getSpace gets from local data source first`() {
        runBlocking {
            val localSpace = listOf(mockedSpace.copy(1))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getSpaces()).thenReturn(localSpace)

            when (val result = spaceRepository.getSpaces()) {
                is ResultData.Success -> assertEquals(localSpace, result.data)
            }
        }
    }

    @Test
    fun `getSpace saves remote data to local`() {
        runBlocking {

            val remoteSpace = listOf(mockedSpace.copy(2))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getColivingList()).thenReturn(ResultData.Success(remoteSpace))
            whenever(localDataSource.getSpaces()).thenReturn(remoteSpace)

            when (spaceRepository.getSpaces()) {
                is ResultData.Success -> verify(localDataSource).saveSpaces(remoteSpace)
            }
        }
    }

    @Test
    fun `findById calls local data source`() {
        runBlocking {

            val space = mockedSpace.copy(id = 5)
            whenever(localDataSource.findById(5)).thenReturn(space)

            val result = spaceRepository.findById(5)

            assertEquals(space, result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {

            val space = mockedSpace.copy(id = 1)

            spaceRepository.update(space)

            verify(localDataSource).update(space)
        }
    }
}