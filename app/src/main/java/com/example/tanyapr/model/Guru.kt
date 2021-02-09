/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Guru(
    @SerializedName("bukti_pembayaran")
    @Expose
    val buktiPembayaran: String,
    @SerializedName("coin")
    @Expose
    val coin: String,
    @SerializedName("created")
    @Expose
    val created: String,
    @SerializedName("nama_guru")
    @Expose
    val namaGuru: String,
    @SerializedName("nama_mata_pelajaran")
    @Expose
    val namaMataPelajaran: String,
    @SerializedName("role")
    @Expose
    val role: String,
    @SerializedName("semester")
    @Expose
    val semester: String,
    @SerializedName("universitas")
    @Expose
    val universitas: String,
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("token")
    @Expose
    val token: String
)