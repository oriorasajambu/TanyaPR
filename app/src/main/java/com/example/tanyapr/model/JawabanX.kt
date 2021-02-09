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

data class JawabanX(
    @SerializedName("answered_at")
    @Expose
    val answeredAt: String,
    @SerializedName("gambar")
    @Expose
    val gambar: String,
    @SerializedName("id_soal")
    @Expose
    val idSoal: Int,
    @SerializedName("id_solusi")
    @Expose
    val idSolusi: Int,
    @SerializedName("solusi")
    @Expose
    val solusi: String,
    @SerializedName("username")
    @Expose
    val username: String
) : Serializable {
    fun getImage() = gambar.let { "${AccesPoint.endPoint}/uploads/${it}" }
}