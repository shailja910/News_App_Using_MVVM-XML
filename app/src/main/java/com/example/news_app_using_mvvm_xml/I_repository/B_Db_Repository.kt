package com.example.news_app_using_mvvm_xml.I_repository

import com.example.news_app_using_mvvm_xml.B_model.Article
import com.example.news_app_using_mvvm_xml.G_RoomDB.ArticleDao

class B_Db_Repository(private val dao: ArticleDao) {

    //get the article from DB and show on to RV
    val allArticles = dao.getAllArticles()


    //insert the article to DB
    suspend fun insert(article: Article) {
        dao.insert(article)
    }

    //code from saving duplicate in database
    suspend fun insertIfNotExists(article: Article): Boolean {
        val existing = dao.getArticleByUrl(article.url)
        return if (existing == null) {
            dao.insert(article)
            true
        } else {
            false
        }
    }

    //swipe to delete
    suspend fun swipeToDelete(article:Article) {
        dao.swipeToDelete(article)
    }


}
