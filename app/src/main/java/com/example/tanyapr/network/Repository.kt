/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.network

class Repository {

    private val client = RestClient.create(AccesPoint.endPoint)

    suspend fun getLogin(username : String, password : String) = client.getLogin(username, password)
    suspend fun getData(task : String) = client.getData(task)

    suspend fun updatePelajaran(field: List<String>) = client.updatePelajaran(
        field[0].toInt(), field[1], field[2], field[3].toInt())

    suspend fun hapusPelajaran(id: Int) = client.deletePelajaran(id)

    suspend fun insertPelajaran(field: Map<String, String>) = client.insertPelajaran(field)

}