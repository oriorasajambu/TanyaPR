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
import com.example.tanyapr.databinding.PelajaranItemBinding
import com.example.tanyapr.model.Pelajaran

class PelajaranAdapter : RecyclerView.Adapter<PelajaranAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PelajaranItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Pelajaran>(){
        override fun areItemsTheSame(oldItem: Pelajaran, newItem: Pelajaran): Boolean {
            return oldItem.idMataPelajaran == newItem.idMataPelajaran
        }

        override fun areContentsTheSame(oldItem: Pelajaran, newItem: Pelajaran): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var listPelajaran : List<Pelajaran>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PelajaranItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val pelajaran = listPelajaran[position]
            tvMataPelajaran.text = pelajaran.mataPelajaran
            tvNamaPelajaran.text = pelajaran.namaMataPelajaran
            tvCoin.text = pelajaran.coin
            tvTanggal.text = pelajaran.created
            btnDelete.setOnClickListener { callback?.onDeleteClicked(pelajaran) }
            btnEdit.setOnClickListener { callback?.onEditClicked(pelajaran) }
        }
    }

    override fun getItemCount() = listPelajaran.size

    interface OnClickedListener {
        fun onDeleteClicked(data : Pelajaran)
        fun onEditClicked(data: Pelajaran)
    }

    companion object { var callback : OnClickedListener? = null }

    fun setOnClickedListener(call : OnClickedListener){ callback = call }
}