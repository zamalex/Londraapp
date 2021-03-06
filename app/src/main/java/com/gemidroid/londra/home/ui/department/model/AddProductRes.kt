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
        var price: Float = 0f,
        @SerializedName("selling_price")
        var sellingPrice: Float = 0f,
        @SerializedName("shipping_price")
        var shipping_price: Float = 0f,
        @SerializedName("total_price")
        var total_price: Float = 0f,
        @SerializedName("user_id")
        var userId: Int = 0
    ) {
        data class Item(
            @SerializedName("description")
            var description: String = "",
            @SerializedName("designer")
            var designer: String = "",
            @SerializedName("has_offer")
            var hasOffer: Boolean = false,
            @SerializedName("images")
            var images: List<String> = listOf(),
            @SerializedName("item_id")
            var itemId: Int = 0,
            @SerializedName("material")
            var material: String = "",
            @SerializedName("name")
            var name: String = "",
            @SerializedName("price")
            var price: Float = 0f,
            @SerializedName("product_id")
            var productId: Int = 0,
            @SerializedName("quantity")
            var quantity: Int = 0,
            @SerializedName("selected_addition")
            var selectedAddition: Any = Any(),
            @SerializedName("selected_color")
            var selectedColor: String = "",
            @SerializedName("selected_size")
            var selectedSize: String = "",
            @SerializedName("selling_price")
            var sellingPrice: Float = 0f,
            @SerializedName("shipping_price")
            var shipping_price: Float = 0f,
            @SerializedName("total_price")
            var total_price: Float = 0f,
            @SerializedName("thumbnail")
            var thumbnail: String = "",
            @SerializedName("user_size")
            var userSize: UserSize = UserSize()
        ){
            data class UserSize(
                @SerializedName("arm_length")
                var armLength: Int = 0,
                @SerializedName("arm_width")
                var armWidth: Int = 0,
                @SerializedName("chest")
                var chest: Int = 0,
                @SerializedName("height")
                var height: Int = 0,
                @SerializedName("id")
                var id: Int = 0,
                @SerializedName("waist")
                var waist: Int = 0
            )
        }
    }
}