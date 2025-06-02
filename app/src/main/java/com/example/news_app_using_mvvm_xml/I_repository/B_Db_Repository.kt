package com.example.news_app_using_mvvm_xml.I_repository

import com.example.news_app_using_mvvm_xml.B_model.Article
import com.example.news_app_using_mvvm_xml.G_RoomDB.ArticleDao

class B_Db_Repository(private val dao: ArticleDao) {
    suspend fun insert(article: Article) {
        dao.insert(article)
    }
}
