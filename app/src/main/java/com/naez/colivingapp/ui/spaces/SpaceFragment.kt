package com.naez.colivingapp.ui.spaces

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

    private val spaceViewModel: SpaceViewModel by currentScope.viewModel(this)
    private lateinit var dataBindingView: FragmentSpaceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingView = FragmentSpaceBinding.inflate(inflater, container, false).apply {
            viewModel = spaceViewModel
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
        adapter = SpaceAdapter(spaceViewModel::onSpaceClicked)
        recycler.adapter = adapter
        spaceViewModel.model.observe(this, Observer(::updateUi))
        spaceViewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val bundle = Bundle().apply { putInt(SPACE, it.id) }
                findNavController().navigate(
                    R.id.action_spaceFragment_to_spaceDetailFragment,
                    bundle
                )
            }
        })
    }

    private fun updateUi(model: SpaceViewModel.UiModel) {
        when (model) {
            is SpaceViewModel.UiModel.Content -> adapter.spaces = model.spaces
            SpaceViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                spaceViewModel.onCoarsePermissionRequested(it)
            }
        }
    }

}