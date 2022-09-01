package com.app.featureplaces.presentation.ui.listplaces

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.presentation.R
import com.app.presentation.common.launchAndCollect
import com.app.presentation.databinding.FragmentListPlacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPlacesFragment : Fragment(R.layout.fragment_list_places) {
    private val viewModel: ListPlacesViewModel by viewModels()
    private lateinit var binding: FragmentListPlacesBinding
    private lateinit var listPlacesState: ListPlacesState
    private val adapter = PlacesAdapter { listPlacesState.onPlaceClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listPlacesState = buildMainState()

        binding = FragmentListPlacesBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.places = it.places
            binding.error = it.error?.let(listPlacesState::errorToString)
        }

        listPlacesState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }
}