/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.model

data class Rekening (
    val rekening : String,
    val noRekening : String
    ) {
    override fun toString(): String {
        return rekening
    }
}