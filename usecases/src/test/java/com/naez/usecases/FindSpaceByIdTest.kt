package com.naez.usecases

import com.naez.data.repository.SpaceRepository
import com.naez.testshared.mockedSpace
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindSpaceByIdTest {

    @Mock
    lateinit var spaceRepository: SpaceRepository

    lateinit var findSpaceById: FindSpaceById

    @Before
    fun setUp() {
        findSpaceById = FindSpaceById(spaceRepository)
    }

    @Test
    fun `invoke calls space repository`() {
        runBlocking {

            val space = mockedSpace.copy(id = 1)
            whenever(spaceRepository.findById(1)).thenReturn(space)

            val result = findSpaceById.invoke(1)

            assertEquals(space, result)
        }
    }
}