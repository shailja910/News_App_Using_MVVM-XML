package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app_using_mvvm_xml.D_util.Resource
import com.example.news_app_using_mvvm_xml.E_rv_adapters.NewsAdapter
import com.example.news_app_using_mvvm_xml.F_viewmodel.NewsViewModel
import com.example.news_app_using_mvvm_xml.MainActivity
import com.example.news_app_using_mvvm_xml.R
import com.example.news_app_using_mvvm_xml.databinding.FragmentBSearchNewsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class B_SearchNewsFragment : Fragment(R.layout.fragment_b__search_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    var searchBinding: FragmentBSearchNewsBinding?=null
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ////viewmodel called on search news
        viewModel = (activity as MainActivity).viewModelInMainActivity
        setupRV()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        //function in view model to show top news on search news page before search query typed.
        viewModel.loadDefaultNews()

        //search query
        searchBinding?.etSearch?.doOnTextChanged { text, _, _, _ ->
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(500)
                val query = text?.toString()?.trim()
                if (!query.isNullOrEmpty()) {
                    viewModel.searchFromNewsInViewModel(query)
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success ->
                {
                    hideProgressBar()
                    response.data?.let {
                            newsResponse-> newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error->{
                    response?.message?.let {
                            message-> "error"
                    }
                }
                is Resource.Loading-> {
                    showProgressBar()
                }
            }})
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchBinding= FragmentBSearchNewsBinding.inflate(inflater,container,false)
        return searchBinding!!.root
    }

    fun setupRV()
    {
        newsAdapter= NewsAdapter()
        searchBinding?.rvSearchNews?.apply {
            adapter=newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun hideProgressBar()
    {
        searchBinding?.paginationProgressBar?.visibility=View.INVISIBLE
    }
    fun showProgressBar()
    {
        searchBinding?.paginationProgressBar?.visibility=View.VISIBLE
    }
}