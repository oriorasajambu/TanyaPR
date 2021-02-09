/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.R
import com.example.tanyapr.databinding.PertanyaanGuruItemBinding
import com.example.tanyapr.model.Pertanyaan
import com.squareup.picasso.Picasso

class PertanyaanGuruAdapter : RecyclerView.Adapter<PertanyaanGuruAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PertanyaanGuruItemBinding) : RecyclerView.ViewHolder(binding.root)

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
        return ViewHolder(PertanyaanGuruItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val pertanyaan = listPertanyaan[position]
            if (pertanyaan.status == 0){
                tvUsername.text = "Username : ${pertanyaan.penanya}"
                tvCoin.text = "Coin : ${pertanyaan.coin}"
                tvPertanyaan.text = pertanyaan.pertanyaan
                if (pertanyaan.status == 1) {
                    btnJawab.isEnabled = false
                    btnJawab.text = "Pertanyaan Sudah Di jawab"
                }
                btnJawab.setOnClickListener {
                    callback?.onClicked(pertanyaan)
                }
            }
        }
    }

    override fun getItemCount(): Int = listPertanyaan.size

    interface OnClickListener {
        fun onClicked(pertanyaan: Pertanyaan)
    }

    companion object {
        var callback : OnClickListener? = null
    }

    fun setOnClickedListener(cb: OnClickListener){
        callback = cb
    }

}