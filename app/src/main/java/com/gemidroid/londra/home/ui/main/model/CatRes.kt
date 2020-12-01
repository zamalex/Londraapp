package com.gemidroid.londra.home.ui.main.model


import com.google.gson.annotations.SerializedName

data class CatRes(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        var name: String = "",
        @SerializedName("products_count")
        var products_count: String = "",
        @SerializedName("banner")
        var banner: String = ""
    )
}