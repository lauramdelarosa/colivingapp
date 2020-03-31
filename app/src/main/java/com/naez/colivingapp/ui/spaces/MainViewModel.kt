package com.naez.colivingapp.ui.spaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naez.colivingapp.ui.common.Event
import com.naez.colivingapp.ui.common.ScopedViewModel
import com.naez.data.ResultData
import com.naez.domain.Space
import com.naez.usecases.GetSpaces
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val getSpaces: GetSpaces,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Space>>()
    val navigation: LiveData<Event<Space>> = _navigation

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val spaces: List<Space>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading

            when (val result = getSpaces.invoke()) {
                is ResultData.Success -> {
                    _model.value = UiModel.Content(result.data)
                }
                is ResultData.Error -> {
                    result.exception.toString()
                }
            }
        }
    }

    fun onSpaceClicked(space: Space) {
        _navigation.value = Event(space)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}