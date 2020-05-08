package com.naez.colivingapp.ui.spacesDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naez.colivingapp.ui.common.ScopedViewModel
import com.naez.colivingapp.utils.Money
import com.naez.colivingapp.utils.currencyFormat
import com.naez.domain.Space
import com.naez.usecases.FindSpaceById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val spaceId: Int,
    private val findSpaceById: FindSpaceById,
    override val uiDispatcher: CoroutineDispatcher
) :
    ScopedViewModel(uiDispatcher) {

    data class UiModel(val space: Space)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findSpace()
            return _model
        }

    private fun findSpace() = launch {
        _model.value = UiModel(findSpaceById.invoke(spaceId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.space?.let {

        }
    }
}