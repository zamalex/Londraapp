package com.gemidroid.londra.home.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileViewModel:ViewModel() {

    fun profile(token:String,coo:String){
        Retrofit.Api.profile(token,coo)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getResponse.postValue(t1)
                getError.postValue(t2)
            }
    }

    fun updateProfile(token:String,bod:JsonObject){
        Retrofit.Api.updateProfile(token,bod)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getUpdateResponse.postValue(t1)
                getUpdateError.postValue(t2)
            }
    }



    val getResponse = MutableLiveData<ProfileRes>()
    val getError = MutableLiveData<Throwable>()

    val getUpdateResponse = MutableLiveData<ProfileRes>()
    val getUpdateError = MutableLiveData<Throwable>()
}