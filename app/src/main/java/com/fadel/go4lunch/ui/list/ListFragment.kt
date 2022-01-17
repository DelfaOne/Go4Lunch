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
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.fadel.go4lunch.R
import com.fadel.go4lunch.ui.detail.DetailScreen
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
        val navController = rememberNavController()

        restaurantList(
            modifier = Modifier,
            restaurantsItemUiModels = list,
            navHostController = navController
        )


        NavHost(navController = navController, startDestination = Routes.ListFragment.route) {

            // First route : ListRestaurant

            composable(Routes.ListFragment.route) {

                // Lay down the restaurantList Composable
                // and pass the navController
                restaurantList(restaurantsItemUiModels = list, modifier = Modifier, navHostController = navController)
            }

            composable(Routes.DetailFragment.route + "/{name}" + "/{address}" + "/{numberOfStars}") {

                val name = it.arguments?.getString("name")
                val address = it.arguments?.getString("address")
                val numberOfStars = it.arguments?.getFloat("numberOfStars")
                // Lay down the DetailScreen Composable
                // and pass the navController
                DetailScreen(name, address, numberOfStars)
            }


        }
    }

    private fun setupObservers() {
        vm.navigationOrder.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    ListViewModel.NavigationOrder.Detail -> findNavController().navigate(
                        R.id.toDetail
                    )
                }
            }
        )
    }

    sealed class Routes(val route: String) {
        object ListFragment : Routes("list")
        object DetailFragment : Routes("detail")
    }
}