package com.fadel.go4lunch.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.fadel.go4lunch.databinding.RestaurantItemBinding

class RestaurantListAdapter : ListAdapter<RestaurantsItemUiModel, RestaurantListAdapter.RestaurantViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RestaurantViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantViewHolder(private val binding: RestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RestaurantsItemUiModel) {
            binding.restaurantContent.setOnClickListener {
                model.onItemClicked?.invoke()
            }
            binding.restaurantName.text = model.name
            binding.restaurantAddress.text = model.address
            binding.restaurantOpeningHour.text = model.openingHours
            binding.restaurantDistance.text = model.distance
            binding.restaurantInterestedWorkmates.text = model.interestNumber
            binding.restaurantRatingBar.rating = model.numberOfStars

            Glide.with(binding.restaurantPicture)
                .load(model.photo)
                .transform(CircleCrop())
                .into(binding.restaurantPicture)
        }

        companion object {
            fun newInstance(parent: ViewGroup) =
                RestaurantViewHolder(RestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    companion object {
        private val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<RestaurantsItemUiModel>() {
            override fun areItemsTheSame(oldItem: RestaurantsItemUiModel, newItem: RestaurantsItemUiModel): Boolean =
                // TODO ID Fadel use place_id instead !
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: RestaurantsItemUiModel, newItem: RestaurantsItemUiModel): Boolean = oldItem == newItem
        }
    }

}