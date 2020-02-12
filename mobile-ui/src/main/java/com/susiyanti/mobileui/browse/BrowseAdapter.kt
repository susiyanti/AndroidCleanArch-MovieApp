package com.susiyanti.mobileui.browse

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.susiyanti.mobileui.R
import com.susiyanti.mobileui.ext.inflate
import com.susiyanti.mobileui.model.Movie
import javax.inject.Inject

class BrowseAdapter @Inject constructor() : ListAdapter<Movie, BrowseAdapter.ViewHolder>(DIFF_CALLBACK) {

    var movieListener: MovieListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.item_movie)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = getItem(position)
        holder.releaseDate.text = project.releaseDate
        holder.title.text = project.title

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/"+project.poster)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.poster)

        val starResource = if (project.isFavorited) {
            R.drawable.ic_star_black_24dp
        } else {
            R.drawable.ic_star_border_black_24dp
        }

        holder.favoritedImage.setImageResource(starResource)

        holder.itemView.apply {
            setOnClickListener {
                if (project.isFavorited) {
                    movieListener?.onFavoritedMovieClicked(project.id)
                } else {
                    movieListener?.onMovieClicked(project.id)
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var poster: ImageView = view.findViewById(R.id.movie_poster)
        var releaseDate: TextView = view.findViewById(R.id.movie_releasedate)
        var title: TextView = view.findViewById(R.id.movie_title)
        var favoritedImage: ImageView = view.findViewById(R.id.image_favorited)
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
