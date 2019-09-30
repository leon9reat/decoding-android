package com.medialink.decomovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medialink.decomovie.MainActivity;
import com.medialink.decomovie.R;
import com.medialink.decomovie.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> listMovie;
    private MovieCallback movieCallback;

    public void setMovieCallback(MovieCallback movieCallback) {
        this.movieCallback = movieCallback;
    }

    public MovieAdapter(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = listMovie.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvOverview.setText(movie.getOverview());
        Glide.with(holder.itemView.getContext())
                .load(MainActivity.URL_IMAGE+movie.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieCallback.onItemClicked(movie);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvReleaseDate, tvOverview;
        private MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title_label);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date_label);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }

    public interface MovieCallback {
        void onItemClicked(Movie data);
    }
}
