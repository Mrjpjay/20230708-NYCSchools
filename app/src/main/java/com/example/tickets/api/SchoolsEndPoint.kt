package com.example.tickets.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolsEndPoint {

    @GET("s3k6-pzi2.json")
    fun getSchools(): Call<List<SchoolData>>

    @GET("f9bf-2cp4.json")
    fun getSchool(@Query("dbn") schoolName: String): Call<List<SchoolDetailData>>
}