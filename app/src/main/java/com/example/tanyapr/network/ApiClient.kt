/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.network

import com.example.tanyapr.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {

    @FormUrlEncoded
    @POST("login/")
    suspend fun getLogin(
        @Field("username") username : String,
        @Field("password") password : String
    ) : Response<DataModel<User>>

    @FormUrlEncoded
    @POST("notification/")
    suspend fun sendNotification(
        @FieldMap field: Map<String, String>
    ) : Response<NotificationUser>

    @FormUrlEncoded
    @POST("notification/")
    suspend fun sendNotificationTopic(
        @FieldMap field: Map<String, String>
    ) : Response<NotificationTopic>

    @GET("{task}/")
    suspend fun getData(
        @Path("task") task: String
    ): Response<DataModel<List<Pelajaran>>>

    @GET("{task}/")
    suspend fun getDataSiswa(
        @Path("task") task: String
    ): Response<DataModel<List<Siswa>>>

    @GET("{task}/")
    suspend fun getDataGuru(
        @Path("task") task: String
    ): Response<DataModel<List<Guru>>>

    @GET("{user}/show/{username}/")
    suspend fun getSiswaCredential(
        @Path("user") user: String,
        @Path("username") username: String
    ) : Response<DataModel<Siswa>>

    @GET("{user}/show/{username}/")
    suspend fun getGuruCredential(
        @Path("user") user: String,
        @Path("username") username: String
    ) : Response<DataModel<Guru>>

    @GET("topup/show/{user}")
    suspend fun getTopUp(
        @Path("user") user : String = ""
    ) : Response<DataModel<List<TopUp>>>

    @FormUrlEncoded
    @POST("pertanyaan/")
    suspend fun getPertanyaan(
        @Field("username") username: String? = null,
        @Field("id_mata_pelajaran") id_mata_pelajaran : Int? = null
    ) : Response<DataModel<List<Pertanyaan>>>

    @FormUrlEncoded
    @POST("pertanyaan/")
    suspend fun getJawaban(
        @Field("id_soal") idSoal: Int
    ) : Response<Jawaban>

    @FormUrlEncoded
    @POST("pelajaran/update/")
    suspend fun updatePelajaran(
        @Field("id_mata_pelajaran") idMataPelajaran : Int,
        @Field("mata_pelajaran") mataPelajaran : String,
        @Field("nama_mata_pelajaran") namaMataPelajaran : String,
        @Field("coin") coin : Int
    ) : Response<Request>

    @FormUrlEncoded
    @POST("siswa/update/")
    suspend fun updateCoinSiswa(
            @Field("username") username: String,
            @Field("coin_value") coinValue: String
    ): Response<Request>

    @FormUrlEncoded
    @POST("admin/")
    suspend fun updateAdmin(
        @FieldMap field: Map<String, String>
    ) : Response<Request>

    @FormUrlEncoded
    @POST("pertanyaan/update/")
    suspend fun updatePertanyaan(
        @FieldMap field: Map<String, String>
    ) : Response<Request>

    @FormUrlEncoded
    @POST("pelajaran/delete/")
    suspend fun deletePelajaran(
        @Field("id_mata_pelajaran") idMataPelajaran: Int
    ) : Response<Request>

    @FormUrlEncoded
    @POST("pelajaran/insert/")
    suspend fun insertPelajaran(
        @FieldMap field: Map<String, String>
    ) : Response<Request>

    @Multipart
    @POST("guru/insert/")
    suspend fun insertGuru(
        @PartMap field: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part
    ) : Response<Request>

    @FormUrlEncoded
    @POST("siswa/insert/")
    suspend fun insertSiswa(
        @FieldMap field : Map<String, String>
    ) : Response<Request>

    @Multipart
    @POST("pertanyaan/insert/")
    suspend fun insertPertanyaan(
            @PartMap field: Map<String, @JvmSuppressWildcards RequestBody>,
            @Part file: MultipartBody.Part
    ) : Response<Request>

    @Multipart
    @POST("topup/insert/")
    suspend fun insertTopUp(
            @PartMap field: Map<String, @JvmSuppressWildcards RequestBody>,
            @Part file: MultipartBody.Part
    ) : Response<Request>

    @Multipart
    @POST("solusi/insert/")
    suspend fun insertSolusi(
            @PartMap field: Map<String, @JvmSuppressWildcards RequestBody>,
            @Part file: MultipartBody.Part
    ) : Response<Request>

    @FormUrlEncoded
    @POST("redeem/")
    suspend fun redeem(
        @FieldMap field: Map<String, String>
    ) : Response<Request>
}