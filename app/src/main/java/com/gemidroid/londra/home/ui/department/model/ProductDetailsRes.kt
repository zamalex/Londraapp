package com.gemidroid.londra.home.ui.department.model


import com.google.gson.annotations.SerializedName

data class ProductDetailsRes(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("additional")
        var additional: List<Any> = listOf(),
        @SerializedName("colors")
        var colors: List<Any> = listOf(),
        @SerializedName("description")
        var description: String = "",
        @SerializedName("designer")
        var designer: String = "",
        @SerializedName("has_offer")
        var hasOffer: Boolean = false,
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("images")
        var images: List<String> = listOf(),
        @SerializedName("material")
        var material: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("price")
        var price: Int = 0,
        @SerializedName("reviews")
        var reviews: List<Review> = listOf(),
        @SerializedName("selling_price")
        var sellingPrice: Int = 0,
        @SerializedName("sizes")
        var sizes: List<Any> = listOf(),
        @SerializedName("thumbnail")
        var thumbnail: String = ""
    ) {
        data class Review(
            @SerializedName("comment")
            var comment: String = "",
            @SerializedName("created_at")
            var createdAt: String = "",
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("rating")
            var rating: Int = 0,
            @SerializedName("reviewer_name")
            var reviewerName: String = ""
        )
    }
}