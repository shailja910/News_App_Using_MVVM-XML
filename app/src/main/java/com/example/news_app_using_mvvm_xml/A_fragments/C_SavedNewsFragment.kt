package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app_using_mvvm_xml.D_util.Resource
import com.example.news_app_using_mvvm_xml.E_rv_adapters.NewsAdapter
import com.example.news_app_using_mvvm_xml.F_viewmodel.A_NewsViewModel
import com.example.news_app_using_mvvm_xml.F_viewmodel.B_DBViewModelProviderFactory
import com.example.news_app_using_mvvm_xml.F_viewmodel.B_DB_Viewmodel
import com.example.news_app_using_mvvm_xml.G_RoomDB.ArticleDatabase
import com.example.news_app_using_mvvm_xml.I_repository.B_Db_Repository
import com.example.news_app_using_mvvm_xml.MainActivity
import com.example.news_app_using_mvvm_xml.R
import com.example.news_app_using_mvvm_xml.databinding.FragmentAArticleBinding
import com.example.news_app_using_mvvm_xml.databinding.FragmentBSearchNewsBinding
import com.example.news_app_using_mvvm_xml.databinding.FragmentBreakingNewsBinding
import com.example.news_app_using_mvvm_xml.databinding.FragmentCSavedNewsBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [C_SavedNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class C_SavedNewsFragment : Fragment(R.layout.fragment_c__saved_news) {
    private var _savedBinding: FragmentCSavedNewsBinding? = null
     val savedBinding get() = _savedBinding


    lateinit var db_viewModel: B_DB_Viewmodel
    lateinit var newsAdapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _savedBinding = FragmentCSavedNewsBinding.inflate(inflater,container,false)
        return _savedBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = ArticleDatabase.getDatabase(requireContext())
        val repository = B_Db_Repository(database.getArticleDao())
        val viewModelFactory = B_DBViewModelProviderFactory(repository)
        db_viewModel = ViewModelProvider(this, viewModelFactory)[B_DB_Viewmodel::class.java]

        setupRV()

        db_viewModel.allArticles.observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
    }




    fun setupRV() {
        newsAdapter = NewsAdapter()
        savedBinding!!.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}