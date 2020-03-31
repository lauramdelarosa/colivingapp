package com.naez.colivingapp.ui.spaces

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.naez.colivingapp.ui.spaces.MainViewModel.UiModel
import com.naez.data.ResultData
import com.naez.testshared.mockedSpace
import com.naez.usecases.GetSpaces
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getSpaces: GetSpaces

    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getSpaces, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.model.observeForever(observer)

        verify(observer).onChanged(UiModel.RequestLocationPermission)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val spaces = listOf(mockedSpace.copy(id = 1))
            whenever(getSpaces.invoke()).thenReturn(ResultData.Success(spaces))
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()
            verify(observer).onChanged(UiModel.Loading)
        }
    }

    @Test
    fun `after requesting the permission, getSpaces is called`() {

        runBlocking {
            val spaces = listOf(mockedSpace.copy(id = 1))
            whenever(getSpaces.invoke()).thenReturn(ResultData.Success(spaces))

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(UiModel.Content(spaces))
        }
    }
}