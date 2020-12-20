package com.gemidroid.londra.home.ui.myorders

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemidroid.londra.home.ui.department.model.AddProductRes
import creativitysol.com.planstech.api.Retrofit
import io.paperdb.Paper
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class OrdersViewModel : ViewModel() {


    fun getCartId(token: String) {
        Retrofit.Api.getCartId(token).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                if (t1 != null && t1.success) {
                    listCart(t1.data.cartId.toString())
                } else {
                    cartResponse.postValue(t1)
                    cartError.postValue(t2)

                }
            }
    }


    fun listCart(cart: String) {
        Retrofit.Api.getCart(cart).subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                cartResponse.postValue(t1)
                cartError.postValue(t2)
            }
        Log.e("coooo", Retrofit.cookieJar.cookies.get(0).toString())
        Log.e("coooo", Retrofit.cookieJar.cookies.get(0).value)

    }

    fun addProduct(parts: RequestBody) {
        Retrofit.Api.addProduct(parts)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                cartResponse.postValue(t1)
                cartError.postValue(t2)
            }
    }

    fun removeProduct(cart: String, prodct: String) {
        Retrofit.Api.removeItem(cart, prodct)
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