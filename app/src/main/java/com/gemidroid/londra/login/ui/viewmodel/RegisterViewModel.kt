package com.gemidroid.londra.login.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody

class RegisterViewModel:ViewModel() {
    fun register(jsonObject: JsonObject) {
        Retrofit.Api.register(jsonObject).subscribeOn(Schedulers.io())
            // .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                getResponse.postValue(t1)
                getError.postValue(t2)
            }

    }

    val getResponse = MutableLiveData<ResponseBody>()
    val getError = MutableLiveData<Throwable>()
}