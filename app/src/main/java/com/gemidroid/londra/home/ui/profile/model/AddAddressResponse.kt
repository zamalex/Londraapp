package com.gemidroid.londra.home.ui.profile.model


import com.google.gson.annotations.SerializedName

data class AddAddressResponse(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("address")
        var address: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("latitude")
        var latitude: String = "",
        @SerializedName("longitude")
        var longitude: String = "",
        @SerializedName("phone")
        var phone: String = "",
        var isChecked: Boolean = false
    )
}