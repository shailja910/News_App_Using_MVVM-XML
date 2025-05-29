package com.example.news_app_using_mvvm_xml.F_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_app_using_mvvm_xml.I_repository.NewsRepository

class NewsViewModelProviderFactory(val repo:NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repo) as T
    }
}