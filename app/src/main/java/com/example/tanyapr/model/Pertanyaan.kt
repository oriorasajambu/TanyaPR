/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.example.tanyapr.network.AccesPoint
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Pertanyaan(
        @SerializedName("answered_at")
        @Expose
        val answeredAt: String? = null,
        @SerializedName("count")
        @Expose
        val count: Int? = null,
        @SerializedName("gambar")
        @Expose
        val gambar: String? = null,
        @SerializedName("id_soal")
        @Expose
        val idSoal: Int,
        @SerializedName("penanya")
        @Expose
        val penanya: String,
        @SerializedName("penjawab")
        @Expose
        val penjawab: String? = null,
        @SerializedName("pertanyaan")
        @Expose
        val pertanyaan: String,
        @SerializedName("solusi")
        @Expose
        val solusi: String? = null,
        @SerializedName("id_solusi")
        @Expose
        val id_solusi: Int? = null,
        @SerializedName("status")
        @Expose
        val status: Int,
        @SerializedName("coin")
        @Expose
        val coin: Int
) : Serializable {
        fun getImage() = gambar?.let { "${AccesPoint.endPoint}/uploads/${it}" }
}