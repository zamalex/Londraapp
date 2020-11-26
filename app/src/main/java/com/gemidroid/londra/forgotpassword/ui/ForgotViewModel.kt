package com.gemidroid.londra.forgotpassword.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.forgotpassword.ui.model.CheckRes
import com.gemidroid.londra.forgotpassword.ui.model.SendCodeRes
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers

class ForgotViewModel:ViewModel() {

    fun sendMeCode(boddy:JsonObject){
        Retrofit.Api.requestPassCode(boddy)
            .subscribeOn(Schedulers.io())
            .subscribe { r->
                sendMeCodeRes.postValue(r)
            }
    }

    fun checkCode(boddy:JsonObject){
        Retrofit.Api.checkCode(boddy)
            .subscribeOn(Schedulers.io())
            .subscribe { r,r2->
                checkCode.postValue(r)
                setError.postValue(r2)
            }
    }

    fun resetPass(boddy:JsonObject){
        Retrofit.Api.updatePass(boddy)
            .subscribeOn(Schedulers.io())
            .subscribe { r,r2->
                updateres.postValue(r)
                errorres.postValue(r2)
            }
    }


    val sendMeCodeRes = MutableLiveData<SendCodeRes>()
    val checkCode = MutableLiveData<CheckRes>()
    val setError = MutableLiveData<Throwable>()

    val updateres = MutableLiveData<CheckRes>()
    val errorres = MutableLiveData<Throwable>()


}