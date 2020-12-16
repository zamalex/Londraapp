package com.gemidroid.londra.home.ui.notifications


import com.google.gson.annotations.SerializedName

data class NotificationRes(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("content")
        var content: String = "",
        @SerializedName("created_at")
        var createdAt: String = "",
        @SerializedName("created_at_human")
        var createdAtHuman: String = "",
        @SerializedName("id")
        var id: Int = 0
    )
}