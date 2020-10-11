package com.nikhil.asuriontask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.asuriontask.R
import com.nikhil.asuriontask.databinding.PetsAdapterListItemBinding
import com.nikhil.asuriontask.entities.PetsEntity

class PetsListAdapter(private var iClickListener: IClickListener) :
    ListAdapter<PetsEntity, PetsListAdapter.PetsViewHolder>(differCallback) {

    inner class PetsViewHolder(itemView: PetsAdapterListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        var binding: PetsAdapterListItemBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val petsItemBinding: PetsAdapterListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.pets_adapter_list_item, parent, false
        )

        return PetsViewHolder(petsItemBinding)
    }

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        val petEntity = getItem(position)
        holder.binding.petAdapterItem = petEntity

        holder.binding.llMain.setOnClickListener { iClickListener.onClickListener(petEntity) }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<PetsEntity>() {

            override fun areItemsTheSame(oldItem: PetsEntity, newItem: PetsEntity): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: PetsEntity,
                newItem: PetsEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface IClickListener {
        fun onClickListener(petsEntity: PetsEntity)
    }

}