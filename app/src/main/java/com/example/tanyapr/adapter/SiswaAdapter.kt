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
import com.example.tanyapr.databinding.SiswaItemBinding
import com.example.tanyapr.model.Siswa

class SiswaAdapter : RecyclerView.Adapter<SiswaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SiswaItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Siswa>(){
        override fun areItemsTheSame(oldItem: Siswa, newItem: Siswa): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: Siswa, newItem: Siswa): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var listSiswa : List<Siswa>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SiswaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.binding.apply {
            val siswa = listSiswa[position]
            namaSiswa.text = context.getString(R.string.nama_siwa, siswa.namaSiswa)
            username.text = context.getString(R.string.name, siswa.username)
            sekolah.text = context.getString(R.string.sekolah, siswa.sekolah)
            created.text = context.getString(R.string.created, siswa.created)
            coin.text = context.getString(R.string.coin, siswa.coin)
        }
    }

    override fun getItemCount() = listSiswa.size
}