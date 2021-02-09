/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NotificationUser(
    @SerializedName("canonical_ids")
    @Expose
    val canonicalIds: Int,
    @SerializedName("failure")
    @Expose
    val failure: Int,
    @SerializedName("multicast_id")
    @Expose
    val multicastId: Long,
    @SerializedName("results")
    @Expose
    val results: List<Result>,
    @SerializedName("success")
    @Expose
    val success: Int
)