package com.naez.usecases

import com.naez.data.ResultData
import com.naez.data.repository.SpaceRepository
import com.naez.testshared.mockedSpace
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSpacesTest {

    @Mock
    lateinit var spaceRepository: SpaceRepository

    lateinit var getSpaces: GetSpaces

    @Before
    fun setUp() {
        getSpaces = GetSpaces(spaceRepository)
    }

    @Test
    fun `invoke calls space repository`() {
        runBlocking {

            val space = listOf(mockedSpace.copy(id = 1))
            whenever(spaceRepository.getSpaces()).thenReturn(ResultData.Success(space))
            when (val result = getSpaces.invoke()) {
                is ResultData.Success -> {
                    Assert.assertEquals(space,  result.data)
                }
            }
        }
    }
}