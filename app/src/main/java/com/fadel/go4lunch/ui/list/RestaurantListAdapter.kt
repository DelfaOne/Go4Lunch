package com.fadel.go4lunch.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.fadel.go4lunch.databinding.RestaurantItemBinding

class RestaurantListAdapter constructor(
    private val onItemClicked: (RestaurantsItemUiModel) -> Unit
) : ListAdapter<RestaurantsItemUiModel, RestaurantListAdapter.RestaurantViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder.newInstance(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantViewHolder(
        private val binding: RestaurantItemBinding,
        private val onItemClicked: (RestaurantsItemUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RestaurantsItemUiModel) {
            binding.restaurantContent.setOnClickListener {
                onItemClicked(model)
            }
            binding.restaurantName.text = model.name
            binding.restaurantAddress.text = model.address
            binding.restaurantOpeningHour.text = model.openingHours
            binding.restaurantDistance.text = model.distance
            binding.restaurantInterestedWorkmates.text = model.interestNumber
            binding.restaurantRatingBar.rating = model.numberOfStars

            Glide.with(binding.restaurantPicture)
                .load(model.photo).transform(CenterCrop())
                .into(binding.restaurantPicture)


        }

        companion object {
            fun newInstance(
                parent: ViewGroup,
                onItemClicked: (RestaurantsItemUiModel) -> Unit
            ): RestaurantViewHolder {
                return RestaurantViewHolder(
                    RestaurantItemBinding.inflate(LayoutInflater.from(parent.context)),
                    onItemClicked
                )
            }
        }

    }

    companion object {
        private val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<RestaurantsItemUiModel>() {
            override fun areItemsTheSame(oldItem: RestaurantsItemUiModel, newItem: RestaurantsItemUiModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: RestaurantsItemUiModel, newItem: RestaurantsItemUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}