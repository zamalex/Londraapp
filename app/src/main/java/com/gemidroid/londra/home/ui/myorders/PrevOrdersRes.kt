package com.gemidroid.londra.home.ui.myorders


import com.google.gson.annotations.SerializedName

data class PrevOrdersRes(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("created_at")
        var createdAt: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("items_num")
        var itemsNum: Int = 0,
        @SerializedName("status")
        var status: String = "",
        @SerializedName("total_price")
        var totalPrice: Float = 0f
    )
}