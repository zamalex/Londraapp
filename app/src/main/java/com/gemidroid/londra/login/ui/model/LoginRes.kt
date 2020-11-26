package com.gemidroid.londra.login.ui.model


import com.google.gson.annotations.SerializedName

data class LoginRes(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("access_token")
        var accessToken: String = "",
        @SerializedName("user")
        var user: User = User()
    ) {
        data class User(
            @SerializedName("avatar")
            var avatar: String = "",
            @SerializedName("country_code")
            var countryCode: String = "",
            @SerializedName("email")
            var email: String = "",
            @SerializedName("first_name")
            var firstName: String = "",
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("last_name")
            var lastName: String = "",
            @SerializedName("mobile")
            var mobile: String = "",
            @SerializedName("mobile_country_code")
            var mobileCountryCode: String = ""
        )
    }
}