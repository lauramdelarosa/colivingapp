package com.naez.colivingapp.ui.spacesDetail

import androidx.lifecycle.MutableLiveData
import com.naez.colivingapp.ui.common.ScopedViewModel
import com.naez.colivingapp.utils.Money
import com.naez.colivingapp.utils.currencyFormat
import com.naez.usecases.FindSpaceById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val spaceId: Int,
    private val findSpaceById: FindSpaceById,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    val titleText: MutableLiveData<String> = MutableLiveData()
    val descriptionText: MutableLiveData<String> = MutableLiveData()
    val typeRoomText: MutableLiveData<String> = MutableLiveData()
    val priceText: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String> = MutableLiveData()
    val favoriteBoolean: MutableLiveData<Boolean> = MutableLiveData()

    init {
        findSpace()
    }

    private fun findSpace() = launch {
        with(findSpaceById.invoke(spaceId)) {
            titleText.value = title
            descriptionText.value = description
            typeRoomText.value = "$typeLivingPlace - $roomsNumber Rooms available"
            priceText.value = Money.format(amount, amountCurrency).currencyFormat()
            favoriteBoolean.value = favorite
            url.value = image
        }
    }

    fun onFavoriteClicked() {}
}