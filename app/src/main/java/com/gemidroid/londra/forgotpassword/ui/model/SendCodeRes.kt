package com.gemidroid.londra.forgotpassword.ui.model


import com.google.gson.annotations.SerializedName

data class SendCodeRes(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("code")
        var code: String = ""
    )
}