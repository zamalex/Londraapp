package com.gemidroid.londra.home.ui.department.model


import com.google.gson.annotations.SerializedName

data class CatProducstRes(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("current_page")
        var currentPage: Int = 0,
        @SerializedName("data")
        var `data`: List<Data> = listOf(),
        @SerializedName("first_page_url")
        var firstPageUrl: String = "",
        @SerializedName("from")
        var from: Int = 0,
        @SerializedName("last_page")
        var lastPage: Int = 0,
        @SerializedName("last_page_url")
        var lastPageUrl: String = "",
        @SerializedName("next_page_url")
        var nextPageUrl: Any = Any(),
        @SerializedName("path")
        var path: String = "",
        @SerializedName("per_page")
        var perPage: Int = 0,
        @SerializedName("prev_page_url")
        var prevPageUrl: Any = Any(),
        @SerializedName("to")
        var to: Int = 0,
        @SerializedName("total")
        var total: Int = 0
    ) {
        data class Data(
            @SerializedName("additional")
            var additional: Additional = Additional(),
            @SerializedName("can_custom_sizes")
            var canCustomSizes: Int = 0,
            @SerializedName("colors")
            var colors: Colors = Colors(),
            @SerializedName("custom_sizes")
            var customSizes: Any = Any(),
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
            var sizes: Sizes = Sizes(),
            @SerializedName("thumbnail")
            var thumbnail: String = "",
            @SerializedName("in_favourite")
            var in_favourite: Int = 0
        ) {
            data class Additional(
                @SerializedName("id")
                var id: Int = 0,
                @SerializedName("is_required")
                var isRequired: Int = 0,
                @SerializedName("name")
                var name: String = "",
                @SerializedName("values")
                var values: List<Value> = listOf()
            ) {
                data class Value(
                    @SerializedName("hint_name_1")
                    var hintName1: String = "",
                    @SerializedName("hint_name_2")
                    var hintName2: String = "",
                    @SerializedName("hint_name_3")
                    var hintName3: String = "",
                    @SerializedName("id")
                    var id: Int = 0,
                    @SerializedName("is_selected")
                    var isSelected: Int = 0,
                    @SerializedName("label")
                    var label: String = "",
                    @SerializedName("price")
                    var price: Int = 0
                )
            }

            data class Colors(
                @SerializedName("id")
                var id: Int = 0,
                @SerializedName("is_required")
                var isRequired: Int = 0,
                @SerializedName("name")
                var name: String = "",
                @SerializedName("values")
                var values: List<Value> = listOf()
            ) {
                data class Value(
                    @SerializedName("hint_name_1")
                    var hintName1: String = "",
                    @SerializedName("hint_name_2")
                    var hintName2: String = "",
                    @SerializedName("hint_name_3")
                    var hintName3: String = "",
                    @SerializedName("id")
                    var id: Int = 0,
                    @SerializedName("is_selected")
                    var isSelected: Int = 0,
                    @SerializedName("label")
                    var label: String = "",
                    @SerializedName("price")
                    var price: Int = 0
                )
            }

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

            data class Sizes(
                @SerializedName("id")
                var id: Int = 0,
                @SerializedName("is_required")
                var isRequired: Int = 0,
                @SerializedName("name")
                var name: String = "",
                @SerializedName("values")
                var values: List<Value> = listOf()
            ) {
                data class Value(
                    @SerializedName("hint_name_1")
                    var hintName1: String = "",
                    @SerializedName("hint_name_2")
                    var hintName2: String = "",
                    @SerializedName("hint_name_3")
                    var hintName3: String = "",
                    @SerializedName("id")
                    var id: Int = 0,
                    @SerializedName("is_selected")
                    var isSelected: Int = 0,
                    @SerializedName("label")
                    var label: String = "",
                    @SerializedName("price")
                    var price: Int = 0
                )
            }
        }
    }
}