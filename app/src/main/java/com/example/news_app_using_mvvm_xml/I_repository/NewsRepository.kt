package com.example.news_app_using_mvvm_xml.I_repository

import com.example.news_app_using_mvvm_xml.B_model.NewsResponse
import com.example.news_app_using_mvvm_xml.C_retrofit.B_RetrofitInstance
import retrofit2.Response

class NewsRepository() {
    //function for repository interact with network API
    suspend fun getBreakingNewsFxnInRepo(country: String, page: Int): Response<NewsResponse>{
        val response = B_RetrofitInstance.api.getBreakingNewsInRetro(country, page)
        return response
    }
}


