package com.spcswabapp.services

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody





class RetrofitConnection {
    private lateinit var disposable : Disposable

    fun<T> connection(method: Observable<T>, success : (response: T?) -> Unit,error: (error: String?) -> Unit){
        disposable = method.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            val gson = Gson()


            success(it)
        },{
            error(it.message)
        })
    }
}