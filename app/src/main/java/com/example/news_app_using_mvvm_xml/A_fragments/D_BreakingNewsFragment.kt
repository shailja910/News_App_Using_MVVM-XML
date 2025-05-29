package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app_using_mvvm_xml.D_util.Resource
import com.example.news_app_using_mvvm_xml.E_rv_adapters.NewsAdapter
import com.example.news_app_using_mvvm_xml.F_viewmodel.NewsViewModel
import com.example.news_app_using_mvvm_xml.MainActivity
import com.example.news_app_using_mvvm_xml.R
import com.example.news_app_using_mvvm_xml.databinding.FragmentBreakingNewsBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [D_BreakingNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class D_BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private var _binding: FragmentBreakingNewsBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel

    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("e", "neil5")
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModelInMainActivity
        Log.d("f", "neil6: $viewModel")

        setupRV() // now binding is guaranteed non-null
        viewModel.getBreakingNewsFromViewModel("us")

        newsAdapter.setOnItemClickListener {
        }

        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    Log.d("h", "neil7: ${response::class.simpleName}")
                    response.data?.let { newsResponse ->
                        Log.d("i", "Neil8: ${newsResponse.articles.size}")

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

    fun setupRV() {
        Log.d("k", "neil9")
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}