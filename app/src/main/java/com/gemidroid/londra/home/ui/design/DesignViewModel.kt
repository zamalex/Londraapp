package com.gemidroid.londra.home.ui.design

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody

class DesignViewModel: ViewModel() {
    fun getAttributes(token:String,type: String) {
        Retrofit.Api.getAppearanceAttributes(token,"hair_colour")
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                hairResponse.postValue(t1)
                errorResponse.postValue(t2)
                Retrofit.Api.getAppearanceAttributes(token,"skin_colour").subscribeOn(Schedulers.io())
                    .subscribe { t1, t2 ->
                        skinResponse.postValue(t1)
                        errorResponse.postValue(t2)
                        Retrofit.Api.getAppearanceAttributes(token,"body_type").subscribeOn(Schedulers.io())
                            .subscribe { t1, t2 ->
                                bodyResponse.postValue(t1)
                                errorResponse.postValue(t2)
                            }

                    }
            }
    }


    fun sendAppearance(
        jsonObject: JsonObject,
        token: String
    ) {
        Retrofit.Api.sendAppearance(token, jsonObject).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                sendResponse.postValue(t1)
                errorResponse.postValue(t2)
            }
    }




    val sendResponse = MutableLiveData<ResponseBody>()
    val hairResponse = MutableLiveData<AttributeRes>()
    val bodyResponse = MutableLiveData<AttributeRes>()
    val skinResponse = MutableLiveData<AttributeRes>()
    val errorResponse = MutableLiveData<Throwable>()

}