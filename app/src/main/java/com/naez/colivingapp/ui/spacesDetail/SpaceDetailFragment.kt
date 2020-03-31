package com.naez.colivingapp.ui.spacesDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.naez.colivingapp.R
import com.naez.colivingapp.databinding.FragmentDetailBinding
import com.naez.colivingapp.ui.common.loadUrl
import com.naez.colivingapp.utils.Money
import com.naez.colivingapp.utils.currencyFormat
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SpaceDetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(arguments?.getInt(SPACE, -1))
    }
    private lateinit var dataBindingView: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBindingView = FragmentDetailBinding.inflate(inflater, container, false).apply {
            viewModel = detailViewModel
        }
        return dataBindingView.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBindingView.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.model.observe(this, Observer(::updateUi))
        spaceDetailFavorite.setOnClickListener { detailViewModel.onFavoriteClicked() }
    }


    private fun updateUi(model: DetailViewModel.UiModel) = with(model.space) {
        val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        spaceDetailFavorite?.setImageResource(icon)
        spaceDetailToolbar?.title = title
        spaceDetailImage?.loadUrl(image)
        spaceDescription?.text = description
        spaceTypeRooms?.text = "$typeLivingPlace - $roomsNumber Rooms available"
        spacePrice?.text = Money.format(amount, amountCurrency).currencyFormat()
    }

    companion object {
        const val SPACE = "idSpace"
    }

}