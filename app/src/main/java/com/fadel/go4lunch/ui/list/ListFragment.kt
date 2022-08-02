package com.fadel.go4lunch.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fadel.go4lunch.databinding.FragmentListBinding
import com.fadel.go4lunch.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val vm by viewModels<ListViewModel>()
    private lateinit var vb: FragmentListBinding
    private lateinit var adapter: RestaurantListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentListBinding.inflate(
            inflater,
            container,
            false
        )
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = RestaurantListAdapter()
        vb.restaurantsRecyclerView.adapter = adapter
        vm.restaurantListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupObservers() {
        vm.navigationOrder.observe(viewLifecycleOwner) {
            when (it) {
                is ListViewModel.NavigationOrder.Detail -> startActivity(
                    DetailActivity.navigate(requireContext(), it.detailId)
                )
            }
        }
    }
}