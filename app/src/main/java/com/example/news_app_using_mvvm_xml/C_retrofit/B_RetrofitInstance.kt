package com.example.news_app_using_mvvm_xml.C_retrofit

import com.example.news_app_using_mvvm_xml.D_util.constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class B_RetrofitInstance {
    companion object {
        //creating the retrofit object by automatic serialization using GSON
        private val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        //now link the retrofit object with the retrofit interface and this class
        // will be used everywhere to make the network request.
        val api by lazy {
            retrofit.create(A_RetrofitNewsApi::class.java)
        }
    }

    /* //OR Second approach to do it
    fun returnRetroInstance():Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
     and write linking part in main activity
    */
}