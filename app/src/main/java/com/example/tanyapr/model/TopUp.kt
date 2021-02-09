/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.example.tanyapr.network.AccesPoint
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TopUp(
    @SerializedName("bukti_pembayaran")
    @Expose
    val buktiPembayaran: String,
    @SerializedName("coin")
    @Expose
    val coin: String,
    @SerializedName("id_topup")
    @Expose
    val idTopup: String,
    @SerializedName("idr")
    @Expose
    val idr: String,
    @SerializedName("uploaded_at")
    @Expose
    val uploadedAt: String,
    @SerializedName("username")
    @Expose
    val username: String
) {
    fun getImage() = "${AccesPoint.endPoint}/uploads/${buktiPembayaran}"
}