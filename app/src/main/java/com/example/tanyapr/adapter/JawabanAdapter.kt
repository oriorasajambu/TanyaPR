/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.R
import com.example.tanyapr.databinding.JawabanItemBinding
import com.example.tanyapr.model.JawabanX
import com.squareup.picasso.Picasso

class JawabanAdapter(val idSolusi: Int?) : RecyclerView.Adapter<JawabanAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: JawabanItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<JawabanX>(){
        override fun areItemsTheSame(oldItem: JawabanX, newItem: JawabanX): Boolean {
            return oldItem.idSolusi == newItem.idSolusi
        }

        override fun areContentsTheSame(oldItem: JawabanX, newItem: JawabanX): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var listJawabanX: List<JawabanX>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            JawabanItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val jawabanX = listJawabanX[position]
            if(idSolusi != 0){
                if(idSolusi == jawabanX.idSolusi)
                    layoutParent.setBackgroundColor(Color.parseColor("#FF03DAC5"))
            }
            else layoutParent.setOnClickListener {
                callback?.onClicked(jawabanX)
            }
            tvUsername.text = jawabanX.username
            tvPertanyaan.text = jawabanX.solusi
            tvTime.text = jawabanX.answeredAt
            Picasso.get().load(jawabanX.getImage())
                .error(R.drawable.ic_error_image)
                .into(ivImage)

        }
    }

    override fun getItemCount(): Int = listJawabanX.size

    companion object {
        var callback : OnClickedListener? = null
    }
    interface OnClickedListener {
        fun onClicked(jawaban: JawabanX)
    }
    fun setOnClickedListener(cb: OnClickedListener){
        callback = cb
    }
}