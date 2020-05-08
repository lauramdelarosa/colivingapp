package com.naez.colivingapp.ui.spacesDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naez.colivingapp.databinding.FragmentDetailBinding
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

    companion object {
        const val SPACE = "idSpace"
    }

}