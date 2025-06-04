package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app_using_mvvm_xml.D_util.Resource
import com.example.news_app_using_mvvm_xml.E_rv_adapters.NewsAdapter
import com.example.news_app_using_mvvm_xml.F_viewmodel.A_NewsViewModel
import com.example.news_app_using_mvvm_xml.MainActivity
import com.example.news_app_using_mvvm_xml.R
import com.example.news_app_using_mvvm_xml.databinding.FragmentBreakingNewsBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [D_BreakingNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class D_BreakingNewsFragment : BaseFragment<FragmentBreakingNewsBinding>() {

    //private var _binding: FragmentBreakingNewsBinding? = null
    //val binding get() = _binding!!

    lateinit var viewModel: A_NewsViewModel

    lateinit var newsAdapter: NewsAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBreakingNewsBinding {
        return FragmentBreakingNewsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).newsviewModelInMainActivity


        //1. set up RV
        setupRV() // now binding is guaranteed non-null


        //2. check internet connection status
        handleInternetCheck(
            showLoading = { showProgressBar() },
            onConnected = {
                viewModel.getBreakingNewsFromViewModel("us")
            },
            onNoInternet = {
                hideProgressBar()
                showNoInternetUI()
            }
        )

        //3.click on the article to show it in web view
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment2_to_articleFragment,
                bundle
            )
        }


        //4. observe the state changes
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Snackbar.make(binding.root, "Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    //function definations
    fun setupRV() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    //kept the progressbar hidden till the Rv is shown
    fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    //to show progressbar while loading
    fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    //textview to show internet connection status
    private fun showNoInternetUI() {
        binding.tvNoInternet.visibility = View.VISIBLE
    }
}