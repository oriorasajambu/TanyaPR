/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("coin_guru")
    @Expose
    val coinGuru: Int? = null,
    @SerializedName("coin_siswa")
    @Expose
    val coinSiswa: Int? = null,
    @SerializedName("created")
    @Expose
    val created: String,
    @SerializedName("nama_guru")
    @Expose
    val namaGuru: String? = null,
    @SerializedName("nama_siswa")
    @Expose
    val namaSiswa: String? = null,
    @SerializedName("role")
    @Expose
    val role: String,
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("id_mata_pelajaran")
    @Expose
    val id_mata_pelajaran: Int? = null,
    @SerializedName("token")
    @Expose
    val token: String? = null
)