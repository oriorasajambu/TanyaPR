/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Solusi(
    @SerializedName("answered_at")
    @Expose
    val answeredAt: String,
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
)