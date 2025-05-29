package com.example.news_app_using_mvvm_xml

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.news_app_using_mvvm_xml.F_viewmodel.NewsViewModel
import com.example.news_app_using_mvvm_xml.F_viewmodel.NewsViewModelProviderFactory
import com.example.news_app_using_mvvm_xml.I_repository.NewsRepository
import com.example.news_app_using_mvvm_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewModelInMainActivity:NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repo = NewsRepository()
        val newsProviderFactory = NewsViewModelProviderFactory(repo)
        viewModelInMainActivity = ViewModelProvider(this,newsProviderFactory).get(NewsViewModel::class.java)

        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nav = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(nav.navController)

    }
}