/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Siswa(
    @SerializedName("coin")
    @Expose
    val coin: String,
    @SerializedName("created")
    @Expose
    val created: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("kelas")
    @Expose
    val kelas: String,
    @SerializedName("nama_siswa")
    @Expose
    val namaSiswa: String,
    @SerializedName("role")
    @Expose
    val role: String,
    @SerializedName("sekolah")
    @Expose
    val sekolah: String,
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("token")
    @Expose
    val token: String
)