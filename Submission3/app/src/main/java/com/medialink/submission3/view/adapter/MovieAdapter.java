package com.medialink.submission3.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medialink.submission3.BuildConfig;
import com.medialink.submission3.MovieContract;
import com.medialink.submission3.R;
import com.medialink.submission3.model.movie.MovieItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private String TAG = MovieAdapter.class.getSimpleName();
    private ArrayList<MovieItem> listMovies = new ArrayList<>();
    private MovieContract.ViewInterface mView;
    private Context context;

    public MovieAdapter(Context context, MovieContract.ViewInterface iView) {
        this.context = context;
        this.mView = iView;
    }

    public void setData(ArrayList<MovieItem> items) {
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
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
        final MovieItem movie = listMovies.get(position);
        String releaseLabel = context.getString(R.string.label_release_date);

        holder.tvTitleLabel.setText(movie.getTitle());
        holder.tvReleaseDateLabel.setText(releaseLabel + ": " + movie.getReleaseDate());
        holder.tvOverviewLabel.setText(movie.getOverview());
        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.ImageUrl + movie.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.itemClick(movie, position);
                Log.d(TAG, "onClick: Movie View Holder");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies == null ? 0 : listMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_title_label)
        TextView tvTitleLabel;
        @BindView(R.id.tv_release_date_label)
        TextView tvReleaseDateLabel;
        @BindView(R.id.tv_overview_label)
        TextView tvOverviewLabel;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
