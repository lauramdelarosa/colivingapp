package com.naez.colivingapp.ui.spaces

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.naez.colivingapp.R
import com.naez.colivingapp.databinding.FragmentSpaceBinding
import com.naez.colivingapp.ui.common.PermissionRequester
import com.naez.colivingapp.ui.spacesDetail.SpaceDetailFragment.Companion.SPACE
import kotlinx.android.synthetic.main.fragment_space.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class SpaceFragment : Fragment() {

    private lateinit var adapter: SpaceAdapter
    private lateinit var coarsePermissionRequester: PermissionRequester

    private val mainViewModel: MainViewModel by currentScope.viewModel(this)
    private lateinit var dataBindingView: FragmentSpaceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBindingView = FragmentSpaceBinding.inflate(inflater, container, false).apply {
            viewModel = mainViewModel
        }
        return dataBindingView.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBindingView.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            coarsePermissionRequester =
                PermissionRequester(it, Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        adapter = SpaceAdapter(mainViewModel::onSpaceClicked)
        recycler.adapter = adapter
        mainViewModel.model.observe(this, Observer(::updateUi))
        mainViewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val bundle = Bundle().apply { putInt(SPACE, it.id) }
                findNavController().navigate(R.id.action_spaceFragment_to_spaceDetailFragment, bundle)
            }
        })
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        progress.visibility =
            if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.spaces = model.spaces
            MainViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                mainViewModel.onCoarsePermissionRequested()
            }
        }
    }

}