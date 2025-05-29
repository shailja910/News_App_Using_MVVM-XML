package com.example.news_app_using_mvvm_xml.E_rv_adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app_using_mvvm_xml.B_model.Article
import com.example.news_app_using_mvvm_xml.databinding.RvItemPreviewBinding
import com.squareup.picasso.Picasso

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(var rvBinding: RvItemPreviewBinding) : RecyclerView.ViewHolder(rvBinding.root)

    //implement DiffUtil
    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    //implement adapter methods
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        /*older way without binding
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview, parent, false)) */
        Log.d("b", "neil1")
            val binding = RvItemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("c", "neil2 ${differ.currentList.size}")
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]
        holder.rvBinding.apply {
            tvSource.text = article.source.name
            tvDescription.text = article.description
            tvTitle.text = article.title
            tvPublishedAt.text = article.publishedAt
            Picasso.get()   //in previous version it had with(context) instaed of get()
                .load(article.urlToImage)
                .into(ivArticleImage)
            root.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
        Log.d("d", "Tneil4: ${article.title}")
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    //INNER CLASS
    //older way of inner class without view binding
    //inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}