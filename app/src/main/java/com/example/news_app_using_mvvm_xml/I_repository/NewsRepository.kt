package com.example.news_app_using_mvvm_xml.I_repository

import com.example.news_app_using_mvvm_xml.B_model.NewsResponse
import com.example.news_app_using_mvvm_xml.C_retrofit.B_RetrofitInstance
import retrofit2.Response

class NewsRepository() {
    //function for repository interact with network API

    // 1. breaking news
    suspend fun getBreakingNewsFxnInRepo(country: String, page: Int): Response<NewsResponse>{
        val response = B_RetrofitInstance.api.getBreakingNewsInRetro(country, page)
        return response
    }

    // 2. search news
    suspend fun searchFromNewsInRepo(searchQ:String, page:Int)=
        B_RetrofitInstance.api.searchfromNewsInRetro(searchQ,page)
}


