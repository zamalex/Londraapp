package com.gemidroid.londra.home.ui.payment


import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("discount")
        var discount: Float = 0f
    )
}