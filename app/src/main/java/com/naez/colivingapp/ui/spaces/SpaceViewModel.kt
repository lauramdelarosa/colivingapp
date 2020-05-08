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

class SpaceViewModel(
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
    val loadingProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    val visibilityErrorText: MutableLiveData<Boolean> = MutableLiveData()
    val visibilityRecyclerview: MutableLiveData<Boolean> = MutableLiveData()

    sealed class UiModel {
        data class Content(val spaces: List<Space>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
        visibilityRecyclerview.value = true
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested(request: Boolean) {
        if (request)
            launch {
                loadingProgressBar.value = true
                when (val result = getSpaces.invoke()) {
                    is ResultData.Success -> {
                        _model.value = UiModel.Content(result.data)
                    }
                    is ResultData.Error -> {
                        result.exception.toString()
                    }
                }
                loadingProgressBar.value = false
            }
        else {
            visibilityErrorText.value=true
            visibilityRecyclerview.value = false
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