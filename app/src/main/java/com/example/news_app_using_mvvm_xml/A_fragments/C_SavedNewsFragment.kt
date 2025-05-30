package com.example.news_app_using_mvvm_xml.A_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.news_app_using_mvvm_xml.R

/**
 * A simple [Fragment] subclass.
 * Use the [C_SavedNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class C_SavedNewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c__saved_news, container, false)
    }

}