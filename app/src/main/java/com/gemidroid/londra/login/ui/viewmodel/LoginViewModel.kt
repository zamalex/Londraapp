package com.gemidroid.londra.login.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.profile.ProfileRes
import com.gemidroid.londra.login.ui.model.LoginRes
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
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

    // test retrofit network chains
    /*
     fun login(jsonObject: JsonObject) {
        Retrofit.Api.login(jsonObject).subscribeOn(Schedulers.io())
           // .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Retrofit.Api.profile("Bearer ${it.data.accessToken}","")
            }
            .subscribe { t1, t2 ->
                getProfileRes.postValue(t1)
                getError.postValue(t2)
            }

    }
     */

    val getLoginResponse = MutableLiveData<LoginRes>()
    val getError = MutableLiveData<Throwable>()
}