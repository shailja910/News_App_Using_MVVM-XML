package com.example.news_app_using_mvvm_xml.F_viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app_using_mvvm_xml.I_repository.NewsRepository
import com.example.news_app_using_mvvm_xml.B_model.NewsResponse
import com.example.news_app_using_mvvm_xml.D_util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(val repository: NewsRepository) : ViewModel() {

    //**************A. Network interaction******************
    //////// (1)BREAKING NEWS CODE /////////////////////
    //a. mutable live data object*/  private live object should be immutable so made a copy of it
    private val _breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNews: LiveData<Resource<NewsResponse>> get() = _breakingNews
    val breakingNewsPageNumber = 1

    init {
        getBreakingNewsFromViewModel("us")
    }

    ////b. fxn to search the news running search sql query
    fun getBreakingNewsFromViewModel(countryCode: String) = viewModelScope.launch {
        _breakingNews.postValue(Resource.Loading())
        try {
            val response = repository.getBreakingNewsFxnInRepo(countryCode, breakingNewsPageNumber)

            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    Log.d("a", "neil0: $resultResponse")
                    _breakingNews.postValue(Resource.Success(resultResponse))
                } ?: run {
                    _breakingNews.postValue(Resource.Error("Empty response body"))
                }
            } else {
                _breakingNews.postValue(Resource.Error(response.message()))
            }

        } catch (t: Throwable) {
            val errorMessage = when (t) {
                is IOException -> "Network Failure"
                else -> "Conversion Error"
            }
            _breakingNews.postValue(Resource.Error(errorMessage))
        }
    }


    //////// (1)SEARCH NEWS CODE /////////////////////
    private val _searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: LiveData<Resource<NewsResponse>> get() = _searchNews
    val searchNewsPageNumber = 1



// //fto show top news on search news page before search query typed.
    fun loadDefaultNews() = viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
        try {
            val response = repository.getBreakingNewsFxnInRepo("us",breakingNewsPageNumber) // default news API call
            if (response.isSuccessful) {
                _searchNews.postValue(Resource.Success(response.body()!!))
            } else {
                _searchNews.postValue(Resource.Error("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            _searchNews.postValue(Resource.Error("Exception: ${e.message}"))
        }
    }

    // response on the basis of search query
    fun searchFromNewsInViewModel(searchQ: String) = viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
        try {
            val responseOfSearchNews =
                repository.searchFromNewsInRepo(searchQ, searchNewsPageNumber)
            if (responseOfSearchNews.isSuccessful) {
                responseOfSearchNews.body()?.let { resultResponse ->
                    Log.d("a", "neil0: $resultResponse")
                    _searchNews.postValue(Resource.Success(resultResponse))
                } ?: run {
                    _searchNews.postValue(Resource.Error("Empty response body"))
                }
            } else {
                _searchNews.postValue(Resource.Error(responseOfSearchNews.message()))
            }

        } catch (t: Throwable) {
            val errorMessage = when (t) {
                is IOException -> "Network Failure"
                else -> "Conversion Error"
            }
            _searchNews.postValue(Resource.Error(errorMessage))
        }
    }
}