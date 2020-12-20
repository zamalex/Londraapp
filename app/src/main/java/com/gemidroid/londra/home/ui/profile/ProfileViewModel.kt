package com.gemidroid.londra.home.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody

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

    fun updateAvatar(token:String,file: MultipartBody.Part?){
        Retrofit.Api.updateAvatar(token,file)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getUpdateResponse.postValue(t1)
                getUpdateError.postValue(t2)
            }
    }


    fun getAddresses(token: String){
        Retrofit.Api.getAddresses(token).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getAddressResponse.postValue(t1)
                getAddressError.postValue(t2)
            }
    }

    fun addAddresses(token: String,jsonObject: JsonObject){
        Retrofit.Api.addAddress(token,jsonObject).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getAddressResponse.postValue(t1)
                getAddressError.postValue(t2)
            }
    }

    fun updateAddress(id:String,token: String,jsonObject: JsonObject){
        Retrofit.Api.updateAddress(id,token,jsonObject).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getAddressResponse.postValue(t1)
                getAddressError.postValue(t2)
            }
    }

    val getAddressResponse = MutableLiveData<AddAddressResponse>()
    val getAddressError = MutableLiveData<Throwable>()

    val getResponse = MutableLiveData<ProfileRes>()
    val getError = MutableLiveData<Throwable>()

    val getUpdateResponse = MutableLiveData<ProfileRes>()
    val getUpdateError = MutableLiveData<Throwable>()
}