package creativitysol.com.planstech.api

import com.gemidroid.londra.forgotpassword.ui.model.CheckRes
import com.gemidroid.londra.forgotpassword.ui.model.SendCodeRes
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.home.ui.main.model.CatRes
import com.gemidroid.londra.home.ui.main.model.SliderRes
import com.gemidroid.londra.home.ui.profile.ProfileRes
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import okhttp3.Call
import okhttp3.ResponseBody

import retrofit2.http.*

interface ApiService {


    @POST("auth/login")
    fun login(@Body body: JsonObject): Single<LoginRes>

    @POST("auth/register")
    fun register(@Body body: JsonObject): Single<LoginRes>



    @GET("auth/profile")
    fun profile(
        @Header("Authorization") token: String,@Header("Accept") ept: String): Single<ProfileRes>

    @GET("products")
    fun getCatsProducts(
       ): Single<CatProducstRes>




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