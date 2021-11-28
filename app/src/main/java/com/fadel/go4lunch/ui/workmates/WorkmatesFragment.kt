package com.fadel.go4lunch.ui.workmates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fadel.go4lunch.databinding.FragmentWorkmatesBinding

class WorkmatesFragment : Fragment() {

    private lateinit var notificationsViewModel: WorkmatesViewModel
    private var _binding: FragmentWorkmatesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(WorkmatesViewModel::class.java)

        _binding = FragmentWorkmatesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textWorkmates
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}