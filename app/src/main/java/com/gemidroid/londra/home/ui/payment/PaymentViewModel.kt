package com.gemidroid.londra.home.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody

class PaymentViewModel : ViewModel() {
    fun checkCoupon(token: String, jsonObject: JsonObject) {
        Retrofit.Api.checkCoupon(token, jsonObject).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                couponResponse.postValue(t1)
                errorResponse.postValue(t2)
            }
    }

    var couponResponse = MutableLiveData<ResponseBody>()
    var errorResponse = MutableLiveData<Throwable>()
}