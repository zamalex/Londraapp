package creativitysol.com.planstech.api

import com.gemidroid.londra.forgotpassword.ui.model.CheckRes
import com.gemidroid.londra.forgotpassword.ui.model.SendCodeRes
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import com.gemidroid.londra.home.ui.main.model.CatRes
import com.gemidroid.londra.home.ui.main.model.SliderRes
import com.gemidroid.londra.home.ui.profile.ProfileRes
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("auth/update-avatar")
    fun updateAvatar(
        @Header("Authorization") bearerToken: String,
        @Part file: MultipartBody.Part?): Single<ProfileRes>

    @POST("auth/login")
    fun login(@Body body: JsonObject): Single<LoginRes>

    @POST("auth/register")
    fun register(@Body body: JsonObject): Single<LoginRes>


    @GET("auth/profile")
    fun profile(
        @Header("Authorization") token: String, @Header("Accept") ept: String
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


    @POST("auth/update")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Single<ProfileRes>


    @POST("auth/send-reset-email-link")
    fun requestPassCode(@Body body: JsonObject): Single<SendCodeRes>

    @POST("auth/check-email-token")
    fun checkCode(@Body body: JsonObject): Single<CheckRes>

    @POST("auth/reset-password")
    fun updatePass(@Body body: JsonObject): Single<CheckRes>


}