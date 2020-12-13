package com.gemidroid.londra.home.ui.myorders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import creativitysol.com.planstech.api.Retrofit
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class OrdersViewModel:ViewModel() {

    fun listCart(cart: String){
        Retrofit.Api.getCart(cart).subscribeOn(Schedulers.io())
            .subscribe {t1,t2->
                cartResponse.postValue(t1)
                cartError.postValue(t2)
            }
    }

    fun addProduct(parts: RequestBody){
        Retrofit.Api.addProduct(parts)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                cartResponse.postValue(t1)
                cartError.postValue(t2)
            }
    }

    fun removeProduct(cart: String,prodct:String){
        Retrofit.Api.removeItem(cart,prodct)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                cartResponse.postValue(t1)
                cartError.postValue(t2)
            }
    }

   // val addProductResponse = MutableLiveData<AddProductRes>()
   // val addProductError = MutableLiveData<Throwable>()


    val cartResponse = MutableLiveData<AddProductRes>()
    val cartError = MutableLiveData<Throwable>()
}