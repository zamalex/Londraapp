package com.gemidroid.londra.home.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.main.model.CatRes
import com.gemidroid.londra.home.ui.main.model.SliderRes
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody

class MainViewModel:ViewModel() {

    val sliderResponse  = MutableLiveData<SliderRes>()
    val sliderError  = MutableLiveData<Throwable>()

    fun getSlider(){
        Retrofit.Api.getSlider()
            .subscribeOn(Schedulers.io())
            .subscribe { r1,r2->
                sliderResponse.postValue(r1)
                sliderError.postValue(r2)

            }
    }


    val catsResponse  = MutableLiveData<CatRes>()
    val catsError  = MutableLiveData<Throwable>()
    fun getCats(){
        Retrofit.Api.getCats()
            .subscribeOn(Schedulers.io())
            .subscribe { r1,r2->
                catsResponse.postValue(r1)
                catsError.postValue(r2)

            }
    }

}