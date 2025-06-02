package com.example.news_app_using_mvvm_xml.F_viewmodel

import androidx.lifecycle.*
import com.example.news_app_using_mvvm_xml.B_model.Article
import com.example.news_app_using_mvvm_xml.I_repository.B_Db_Repository
import kotlinx.coroutines.launch

class B_DB_Viewmodel(private val repository: B_Db_Repository) : ViewModel() {

    val allArticles: LiveData<List<Article>> = repository.allArticles

    fun insert(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }
}
