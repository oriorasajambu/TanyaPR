/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model

import com.example.tanyapr.network.AccesPoint
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Jawaban(
    @SerializedName("coin")
    @Expose
    val coin: Int,
    @SerializedName("gambar")
    @Expose
    val gambar: String,
    @SerializedName("id_mata_pelajaran")
    @Expose
    val idMataPelajaran: Int,
    @SerializedName("id_soal")
    @Expose
    val idSoal: Int,
    @SerializedName("id_solusi")
    @Expose
    val idSolusi: Any,
    @SerializedName("jawaban")
    @Expose
    val jawaban: List<JawabanX>,
    @SerializedName("pertanyaan")
    @Expose
    val pertanyaan: String,
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("username")
    @Expose
    val username: String
) {
    fun getImage() = gambar.let { "${AccesPoint.endPoint}/uploads/${it}" }
}