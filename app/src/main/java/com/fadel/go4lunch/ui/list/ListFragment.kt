package com.fadel.go4lunch.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.fadel.go4lunch.ui.detail.DetailActivity
import com.fadel.go4lunch.ui.list.components.restaurantList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val vm by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                liveDataComponent(restaurantsItemUiModels = vm.restaurantListLiveData)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    @Composable
    fun liveDataComponent(
        restaurantsItemUiModels: LiveData<List<RestaurantsItemUiModel>>,
    ) {
        val list by restaurantsItemUiModels.observeAsState(emptyList())

        restaurantList(
            modifier = Modifier,
            restaurantsItemUiModels = list
        )
    }

    private fun setupObservers() {
        vm.navigationOrder.observe(viewLifecycleOwner) {
            when (it) {
                is ListViewModel.NavigationOrder.Detail -> startActivity(
                    DetailActivity.navigate(
                        requireContext(),
                        it.detailId
                    )
                )
            }
        }
    }
}