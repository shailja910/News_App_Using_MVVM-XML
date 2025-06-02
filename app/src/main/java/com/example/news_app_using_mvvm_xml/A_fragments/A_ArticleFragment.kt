package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.news_app_using_mvvm_xml.F_viewmodel.A_NewsViewModel
import com.example.news_app_using_mvvm_xml.F_viewmodel.B_DBViewModelProviderFactory
import com.example.news_app_using_mvvm_xml.F_viewmodel.B_DB_Viewmodel
import com.example.news_app_using_mvvm_xml.G_RoomDB.ArticleDatabase
import com.example.news_app_using_mvvm_xml.I_repository.B_Db_Repository
import com.example.news_app_using_mvvm_xml.MainActivity
import com.example.news_app_using_mvvm_xml.databinding.FragmentAArticleBinding
import com.google.android.material.snackbar.Snackbar

class A_ArticleFragment : Fragment() {

    private var _binding: FragmentAArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: A_NewsViewModel

    private lateinit var dbviewModel: B_DB_Viewmodel

    private val args: A_ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = ArticleDatabase.getDatabase(requireContext())
        val repository = B_Db_Repository(database.getArticleDao())
        val viewModelFactory = B_DBViewModelProviderFactory(repository)
        dbviewModel = ViewModelProvider(this, viewModelFactory)[B_DB_Viewmodel::class.java]

        // Get ViewModels from MainActivity
        viewModel = (activity as MainActivity).newsviewModelInMainActivity

        val article = args.article

        // Load the article URL in WebView
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        // Handle FAB click to save article
        binding.fab.setOnClickListener {
            dbviewModel.saveArticleIfNotExists(article) { success ->
                if (success) {
                    Snackbar.make(it, "Article saved", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(it, "Article already saved", Snackbar.LENGTH_SHORT).show()
                }
            }
             }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
