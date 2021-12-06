package me.isachenko.loansonline.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoansEndPoint {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("focusstart.appspot.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}