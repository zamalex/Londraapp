package com.gemidroid.londra.home.ui.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.schedulers.Schedulers

class NotificationsViewModel:ViewModel() {

    fun getNotifications(token:String){
        creativitysol.com.planstech.api.Retrofit.Api.getNotifications(token)
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                notificationsResponse.postValue(t1)
                notificationsError.postValue(t2)
            }
    }

    val notificationsResponse = MutableLiveData<NotificationRes>()
    val notificationsError = MutableLiveData<Throwable>()
}