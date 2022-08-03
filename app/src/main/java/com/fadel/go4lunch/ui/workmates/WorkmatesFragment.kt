package com.fadel.go4lunch.ui.workmates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fadel.go4lunch.databinding.FragmentListBinding
import com.fadel.go4lunch.databinding.FragmentWorkmatesBinding
import com.fadel.go4lunch.ui.list.ListViewModel
import com.fadel.go4lunch.ui.list.RestaurantListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkmatesFragment : Fragment() {

    private val vm by viewModels<WorkmatesViewModel>()
    private lateinit var vb: FragmentWorkmatesBinding
    private lateinit var adapter: WorkmatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentWorkmatesBinding.inflate(
            inflater,
            container,
            false
        )
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = WorkmatesAdapter()
        vb.workmateRecyclerView.adapter = adapter
        vm.workmatesListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}