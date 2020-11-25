package creativitysol.com.planstech.api

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody

import retrofit2.http.*

interface ApiService {




    @POST("auth/login")
    fun login(@Body body: JsonObject): Single<ResponseBody>

    @POST("auth/register")
    fun register(@Body body: JsonObject): Single<ResponseBody>


}