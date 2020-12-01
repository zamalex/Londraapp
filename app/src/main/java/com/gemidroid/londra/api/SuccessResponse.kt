package com.gemidroid.londra.api


import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)