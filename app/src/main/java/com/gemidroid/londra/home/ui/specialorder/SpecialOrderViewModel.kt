package com.gemidroid.londra.home.ui.specialorder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody


class SpecialOrderViewModel : ViewModel() {


    fun requestSpecialOrder(token: String, jsonObject: JsonObject) {
        Retrofit.Api.requestSpecialOrder(token, jsonObject).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                requestResponse.postValue(t1)
                error.postValue(t2)
            }
    }


    fun getDesigners() {
        Retrofit.Api.getDesigners().subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                designersResponse.postValue(t1)
                error.postValue(t2)
            }
    }

    var designersResponse = MutableLiveData<DesignersRes>()

    var requestResponse = MutableLiveData<ResponseBody>()
    var error = MutableLiveData<Throwable>()

}