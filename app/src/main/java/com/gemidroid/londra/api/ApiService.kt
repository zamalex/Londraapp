package creativitysol.com.planstech.api

import com.gemidroid.londra.forgotpassword.ui.model.CheckRes
import com.gemidroid.londra.forgotpassword.ui.model.SendCodeRes
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import com.gemidroid.londra.home.ui.design.AttributeRes
import com.gemidroid.londra.home.ui.notifications.NotificationRes
import com.gemidroid.londra.home.ui.main.model.CatRes
import com.gemidroid.londra.home.ui.main.model.SliderRes
import com.gemidroid.londra.home.ui.profile.ProfileRes
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("auth/update-avatar")
    fun updateAvatar(
        @Header("Authorization") bearerToken: String,
        @Part file: MultipartBody.Part?
    ): Single<ProfileRes>

    @POST("auth/login")
    fun login(@Body body: JsonObject): Single<LoginRes>

    @POST("auth/register")
    fun register(@Body body: JsonObject): Single<LoginRes>


    @GET("auth/profile")
    fun profile(
        @Header("Authorization") token: String, @Header("Cookie") ept: String
    ): Single<ProfileRes>

    @GET("products")
    fun getCatsProducts(
        @Query("page") page: Int,
        @Query("category_id") category_id: Int?
    ): Single<CatProducstRes>


    @GET("products/{id}/show")
    fun getProduct(
        @Path("id") id: Int
    ): Single<ProductDetailsRes>


    @GET("main-slider")
    fun getSlider(): Single<SliderRes>

    @GET("categories")
    fun getCats(): Single<CatRes>

    @GET("products")
    fun getProducts(): Single<ResponseBody>

    @GET("carts/{id}/items")
    fun getCart(@Path("id") cart: String): Single<AddProductRes>

    @GET("notifications")
    fun getNotifications(@Header("Authorization") token: String): Single<NotificationRes>

    @GET("appearance/attributes")
    fun getAppearanceAttributes(
        @Header("Authorization") token: String,
        @Query("type") type: String
    ): Single<AttributeRes>

    @GET("auth/cart")
    fun getCartId(@Header("Authorization") token: String): Single<AddProductRes>


    @POST("auth/update")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<ProfileRes>


    @POST("notifications/subscribe")
    fun subscribeNotifications(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<ResponseBody>


    @POST("appearance/send")
    fun sendAppearance(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<ResponseBody>


    @POST("auth/send-reset-email-link")
    fun requestPassCode(@Body body: JsonObject): Single<SendCodeRes>

    @POST("auth/check-email-token")
    fun checkCode(@Body body: JsonObject): Single<CheckRes>

    @POST("auth/reset-password")
    fun updatePass(@Body body: JsonObject): Single<CheckRes>

    @POST("carts/add")
    fun addProduct(@Body parts: RequestBody): Single<AddProductRes>

    @POST("carts/items/{item_id}/update-quantity")
    fun updateCartQuantity(
        @Path("item_id") item_id: String,
        @Body parts: RequestBody
    ): Single<AddProductRes>

    @POST("carts/{cart}/user/{user}/update")
    fun updateCart(@Path("cart") cart: String, @Path("user") user: String): Single<AddProductRes>

    @POST("carts/{cart_id}/items/{product_id}/remove")
    fun removeItem(
        @Path("cart_id") cart: String,
        @Path("product_id") product: String
    ): Single<AddProductRes>


    //address

    @POST("auth/addresses/create")
    fun addAddress(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<AddAddressResponse>


    @POST("auth/addresses/{id}/update")
    fun updateAddress(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<AddAddressResponse>

    @GET("auth/addresses")
    fun getAddresses(
        @Header("Authorization") token: String
    ): Single<AddAddressResponse>


    //favorites
    @GET("auth/favourites")
    fun getFavs(
        @Header("Authorization") token: String
    ): Single<CatProducstRes>

    @POST("auth/favourite")
    fun addRemoveFavs(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<ResponseBody>


}