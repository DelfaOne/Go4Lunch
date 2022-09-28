package com.fadel.go4lunch.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fadel.go4lunch.databinding.FragmentDetailBinding
import com.fadel.go4lunch.ui.workmates.WorkmatesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val vm by viewModels<DetailViewModel>()
    private lateinit var vb: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WorkmatesAdapter()
        vb.detailRecyclerView.adapter = adapter

        vm.getViewStateLiveData().observe(this.viewLifecycleOwner) { restaurantDetail ->
            vb.detailRestaurantAddress.text = restaurantDetail.address
            vb.detailRestaurantName.text = restaurantDetail.name
            Glide.with(vb.detailPicture)
                .load(restaurantDetail.imageUrl.toUri())
                .into(vb.detailPicture)

            adapter.submitList(restaurantDetail.workmatesInterested)
        }

        vb.detailCallButton.setOnClickListener {
            ContextCompat.startActivity(
                this.requireContext(),
                Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${restaurantDetail.phoneNumber}")
                },
                null
            )
        }

        vb.detailWebIcon.setOnClickListener {
            ContextCompat.startActivity(
                this.requireContext(),
                Intent(Intent.ACTION_VIEW, Uri.parse(restaurantDetail.website)),
                null
            )
        }

        vb.choseRestaurantButton.apply {
            backgroundTintList = ColorStateList.valueOf(restaurantDetail.buttonChoiceColor)
            setOnClickListener {
                vm.onRestaurantChooseClicked()
            }
        }
    }

    companion object {
        const val KEY_PLACE_ID = "KEY_PLACE_ID"

        fun newInstance(placeId: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_PLACE_ID, placeId)
            }
        }
    }
}