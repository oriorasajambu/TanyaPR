/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.R
import com.example.tanyapr.databinding.PertanyaanItemBinding
import com.example.tanyapr.model.Pertanyaan
import com.squareup.picasso.Picasso

class PertanyaanAdapter : RecyclerView.Adapter<PertanyaanAdapter.ViewHolder>() {

    inner class ViewHolder(val itemBinding: PertanyaanItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Pertanyaan>(){
        override fun areItemsTheSame(oldItem: Pertanyaan, newItem: Pertanyaan): Boolean {
            return oldItem.idSoal == newItem.idSoal
        }

        override fun areContentsTheSame(oldItem: Pertanyaan, newItem: Pertanyaan): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var listPertanyaan: List<Pertanyaan>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PertanyaanItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.apply {
            val pertanyaan = listPertanyaan[position]
            pertanyaan.id_solusi?.let {
                cardView.setBackgroundColor(Color.parseColor("#FF03DAC5"))
            }
            tvPertanyaan.text = pertanyaan.pertanyaan
            layout.setOnClickListener {
                callback?.onClicked(pertanyaan)
            }
        }
    }

    override fun getItemCount(): Int = listPertanyaan.size

    companion object {
        var callback : OnClickedListener? = null
    }

    interface OnClickedListener {
        fun onClicked(pertanyaan: Pertanyaan)
    }

    fun setOnClickedListener(cb: OnClickedListener) {
        callback = cb
    }
}