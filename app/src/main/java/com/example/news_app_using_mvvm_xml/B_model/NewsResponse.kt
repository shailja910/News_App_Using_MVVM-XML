package com.example.news_app_using_mvvm_xml.B_model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)