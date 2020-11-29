package com.gemidroid.londra.home.ui.main.model


import com.google.gson.annotations.SerializedName

data class SliderRes(
    @SerializedName("data")
    var `data`: List<String> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)