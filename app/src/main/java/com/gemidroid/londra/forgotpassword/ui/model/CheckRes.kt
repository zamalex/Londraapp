package com.gemidroid.londra.forgotpassword.ui.model


import com.google.gson.annotations.SerializedName

data class CheckRes(
    @SerializedName("data")
    var `data`: String = "",
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)