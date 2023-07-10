package com.example.tickets.api

import com.google.gson.annotations.SerializedName


data class SchoolData(
    val dbn: String = "",
    @SerializedName("school_name") val schoolName: String? ="",
    val neighborhood: String ="",
    val location: String ="",
    val website: String =""
)
