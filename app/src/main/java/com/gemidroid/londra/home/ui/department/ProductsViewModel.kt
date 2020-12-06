package com.gemidroid.londra.home.ui.department

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.home.ui.department.model.ProductDetailsRes
import com.gemidroid.londra.home.ui.profile.ProfileRes
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsViewModel:ViewModel() {

    fun getCatProducts(page:Int,cat:Int?){
        Retrofit.Api.getCatsProducts(page,cat)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getCatProductsResponse.postValue(t1)
                getCatProductsError.postValue(t2)
            }
    }


    fun gwtProduct(id:Int){
        Retrofit.Api.getProduct(id)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                getProductResponse.postValue(t1)
                getProductError.postValue(t2)
            }
    }

    fun addProduct(jsonObject: JsonObject){
        Retrofit.Api.addProduct(jsonObject)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                addProductResponse.postValue(t1)
                addProductError.postValue(t2)
            }
    }


    val addProductResponse = MutableLiveData<AddProductRes>()
    val addProductError = MutableLiveData<Throwable>()


    val getCatProductsResponse = MutableLiveData<CatProducstRes>()
    val getCatProductsError = MutableLiveData<Throwable>()


    val getProductResponse = MutableLiveData<ProductDetailsRes>()
    val getProductError = MutableLiveData<Throwable>()


}