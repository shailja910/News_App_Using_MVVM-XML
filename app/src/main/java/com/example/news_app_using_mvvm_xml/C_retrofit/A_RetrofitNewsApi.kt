package com.example.news_app_using_mvvm_xml.C_retrofit


import com.example.news_app_using_mvvm_xml.B_model.NewsResponse
import com.example.news_app_using_mvvm_xml.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
//this class will define endpoints of news api for each of our request
interface A_RetrofitNewsApi {

    //get this url from the documentation of api website under each tab , in our case
    //we are using breaking news.the query parameters in @Query are taken from documentation of newsapi too.
    //    // The parameters spelling should be exactly matching with the name mentioned
    //    // in the api,s documentation . We are generating the query on the basis of
    //    // breaking news for country canada with page number initially shown and to
    //    // inform the news api about the "apikey " from which the request is being made.
    @GET("v2/top-headlines")
    suspend fun getBreakingNewsInRetro(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int=1,
        @Query("apiKey") apiKey: String= BuildConfig.API_KEY
    ):Response<NewsResponse>

}