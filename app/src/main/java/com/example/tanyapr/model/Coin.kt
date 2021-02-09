/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model

data class Coin(
    val coin: String,
    val harga: String
) {
    override fun toString(): String {
        return coin
    }

}