/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Request(
    @SerializedName("error")
    @Expose
    val error: String? = null,
    @SerializedName("http_code")
    @Expose
    val httpCode: Int,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("status")
    @Expose
    val status: Int
)