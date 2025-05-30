package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.news_app_using_mvvm_xml.F_viewmodel.NewsViewModel
import com.example.news_app_using_mvvm_xml.MainActivity
import com.example.news_app_using_mvvm_xml.R
import com.example.news_app_using_mvvm_xml.databinding.FragmentAArticleBinding
import com.google.android.material.snackbar.Snackbar

class A_ArticleFragment : Fragment(R.layout.fragment_a__article) {
    lateinit var viewModel: NewsViewModel
    var articleBinding: FragmentAArticleBinding?=null
    val  args: A_ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModelInMainActivity

        //show the article passed on webview
        val article = args.article
        articleBinding?.webView?.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        articleBinding?.fab?.setOnClickListener{
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articleBinding = FragmentAArticleBinding.inflate(inflater,container,false)
        return articleBinding!!.root
    }
}