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
import com.example.tanyapr.databinding.GuruItemBinding
import com.example.tanyapr.model.Guru

class GuruAdapter : RecyclerView.Adapter<GuruAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GuruItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Guru>(){
        override fun areItemsTheSame(oldItem: Guru, newItem: Guru): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: Guru, newItem: Guru): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var listGuru : List<Guru>
        get() = differ.currentList
        set(value) {differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GuruItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.binding.apply {
            val guru = listGuru[position]
            namaGuru.text = context.getString(R.string.nama_guru, guru.namaGuru)
            username.text = context.getString(R.string.name, guru.username)
            universitas.text = context.getString(R.string.universitas, guru.universitas)
            created.text = context.getString(R.string.created, guru.created)
            coin.text = context.getString(R.string.coin, guru.coin)
        }
    }

    override fun getItemCount() = listGuru.size
}