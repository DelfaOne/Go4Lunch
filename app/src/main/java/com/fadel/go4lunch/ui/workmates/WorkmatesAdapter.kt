package com.fadel.go4lunch.ui.workmates


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.fadel.go4lunch.databinding.WorkmateItemBinding

class WorkmatesAdapter :
    ListAdapter<WorkmatesUiModel, WorkmatesAdapter.WorkmatesViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WorkmatesViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: WorkmatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WorkmatesViewHolder(private val binding: WorkmateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: WorkmatesUiModel) {
            Glide.with(binding.itemWorkmateAvatar)
                .load(model.photoUrl)
                .transform(CircleCrop())
                .into(binding.itemWorkmateAvatar)

            binding.itemWorkmateDescription.text = model.detail
        }

        companion object {
            fun newInstance(parent: ViewGroup) =
                WorkmatesViewHolder(
                    WorkmateItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    companion object {
        private val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<WorkmatesUiModel>() {
            override fun areItemsTheSame(
                oldItem: WorkmatesUiModel,
                newItem: WorkmatesUiModel
            ): Boolean =
                oldItem.workmateId == newItem.workmateId

            override fun areContentsTheSame(
                oldItem: WorkmatesUiModel,
                newItem: WorkmatesUiModel
            ): Boolean = oldItem == newItem
        }
    }

}