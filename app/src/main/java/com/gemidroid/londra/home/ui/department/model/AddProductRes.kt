package com.gemidroid.londra.home.ui.department.model


import com.google.gson.annotations.SerializedName

data class AddProductRes(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("cart_id")
        var cartId: Int = 0,
        @SerializedName("has_offer")
        var hasOffer: Boolean = false,
        @SerializedName("items")
        var items: List<Item> = listOf(),
        @SerializedName("price")
        var price: Int = 0,
        @SerializedName("selling_price")
        var sellingPrice: Int = 0,
        @SerializedName("user_id")
        var userId: Int = 0
    ) {
        data class Item(
            @SerializedName("cart_id")
            var cartId: Int = 0,
            @SerializedName("quantity")
            var quantity: Int = 0,
            @SerializedName("selected_options")
            var selectedOptions: List<Any> = listOf(),
            @SerializedName("user_size")
            var userSize: List<Any> = listOf()
        )
    }
}