/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pelajaran(
    @SerializedName("coin")
    @Expose
    val coin: String,
    @SerializedName("created")
    @Expose
    val created: String,
    @SerializedName("id_mata_pelajaran")
    @Expose
    val idMataPelajaran: String,
    @SerializedName("mata_pelajaran")
    @Expose
    val mataPelajaran: String,
    @SerializedName("nama_mata_pelajaran")
    @Expose
    val namaMataPelajaran: String
) : Serializable {

    override fun toString(): String {
        return namaMataPelajaran
    }

}