package com.susiyanti.mobileui.favorited

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.susiyanti.mobileui.R
import com.susiyanti.mobileui.ext.inflate
import com.susiyanti.mobileui.model.Movie
import javax.inject.Inject

class FavoritedAdapter @Inject constructor() : RecyclerView.Adapter<FavoritedAdapter.ViewHolder>() {

    var movies: List<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.item_favorited_movie)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = movies[position]
        holder.title.text = project.title
        holder.releaseDate.text = project.releaseDate

        Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500/"+project.poster)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.poster)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var poster: ImageView = view.findViewById(R.id.movie_poster)
        var title: TextView = view.findViewById(R.id.movie_title)
        var releaseDate: TextView = view.findViewById(R.id.movie_releasedate)
    }
}
