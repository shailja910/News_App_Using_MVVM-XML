package com.example.news_app_using_mvvm_xml

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.news_app_using_mvvm_xml.F_viewmodel.A_NewsViewModel
import com.example.news_app_using_mvvm_xml.F_viewmodel.A_NewsViewModelProviderFactory
import com.example.news_app_using_mvvm_xml.I_repository.A_NewsRepository
import com.example.news_app_using_mvvm_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var newsviewModelInMainActivity:A_NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // news viewmodel
        val repo = A_NewsRepository()
        val newsProviderFactory = A_NewsViewModelProviderFactory(repo)
        newsviewModelInMainActivity = ViewModelProvider(this,newsProviderFactory).get(A_NewsViewModel::class.java)

        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nav = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(nav.navController)

    }
}