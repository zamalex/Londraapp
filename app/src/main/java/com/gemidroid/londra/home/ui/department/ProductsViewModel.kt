package com.gemidroid.londra.home.ui.department

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.home.ui.profile.ProfileRes
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsViewModel:ViewModel() {

    fun getCatProducts(){
        Retrofit.Api.getCatsProducts()
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getCatProductsResponse.postValue(t1)
                getCatProductsError.postValue(t2)
            }
    }



    val getCatProductsResponse = MutableLiveData<CatProducstRes>()
    val getCatProductsError = MutableLiveData<Throwable>()


}