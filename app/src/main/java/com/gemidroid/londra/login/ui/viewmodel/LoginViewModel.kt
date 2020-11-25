package com.gemidroid.londra.login.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody

class LoginViewModel : ViewModel() {


    fun login(jsonObject: JsonObject) {
        Retrofit.Api.login(jsonObject).subscribeOn(Schedulers.io())
           // .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                getLoginResponse.postValue(t1)
                getError.postValue(t2)
            }

    }

    val getLoginResponse = MutableLiveData<ResponseBody>()
    val getError = MutableLiveData<Throwable>()
}