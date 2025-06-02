package com.example.news_app_using_mvvm_xml.G_RoomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news_app_using_mvvm_xml.B_model.Article

    @Dao
    interface ArticleDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(article: Article)

        @Query("SELECT * FROM article_table WHERE url = :url LIMIT 1")
        suspend fun getArticleByUrl(url: String): Article?

        @Query("SELECT * FROM article_table")
        fun getAllArticles(): LiveData<List<Article>>

        @Delete
        suspend fun swipeToDelete(article :Article)

    }