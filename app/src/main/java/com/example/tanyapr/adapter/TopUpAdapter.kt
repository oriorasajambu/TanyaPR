/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.R
import com.example.tanyapr.databinding.TopUpItemBinding
import com.example.tanyapr.model.TopUp
import com.squareup.picasso.Picasso

class TopUpAdapter : RecyclerView.Adapter<TopUpAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TopUpItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<TopUp>(){
        override fun areItemsTheSame(oldItem: TopUp, newItem: TopUp): Boolean {
            return oldItem.idTopup == newItem.idTopup
        }

        override fun areContentsTheSame(oldItem: TopUp, newItem: TopUp): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var listTopUp: List<TopUp>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TopUpItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.binding.apply {
            val topUp = listTopUp[position]
            Picasso.get()
                    .load(topUp.getImage())
                    .error(R.drawable.ic_error_image)
                    .into(buktiPembayaran)
            username.text = context.getString(R.string.name, topUp.username)
            uploadedAt.text = context.getString(R.string.uploaded_at, topUp.uploadedAt)
            idr.text = context.getString(R.string.idr, topUp.idr)
            coin.text = context.getString(R.string.coin, topUp.coin)
        }
    }

    override fun getItemCount() = listTopUp.size
}