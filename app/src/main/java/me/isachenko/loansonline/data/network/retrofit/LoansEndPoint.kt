package me.isachenko.loansonline.data.network.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoansEndPoint {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://focusstart.appspot.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}