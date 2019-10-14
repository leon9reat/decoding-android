package com.medialink.submission5.view.adapter;

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
import com.medialink.submission5.BuildConfig;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.movie.MovieResult;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private static final String TAG = "MovieAdapter";
    private Context context;
    private ArrayList<MovieResult> listMovie = new ArrayList<>();
    private MainContract.MovieInterface mView;

    public MovieAdapter(Context context, MainContract.MovieInterface mView) {
        this.context = context;
        this.mView = mView;
    }

    public void setData(ArrayList<MovieResult> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final MovieResult movie = listMovie.get(position);
        String releaseLabel = context.getString(R.string.label_release_date);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvRelease.setText(String.format("%s : %s",
                releaseLabel,
                movie.getReleaseDate()));
        holder.tvOverview.setText(movie.getOverview());

        if (movie.getPosterPath() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(BuildConfig.ImageUrl+movie.getPosterPath())
                    .into(holder.imgPoster);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.listClick(movie, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie == null ? 0 : listMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView tvTitle, tvRelease, tvOverview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_label_title);
            tvRelease = itemView.findViewById(R.id.tv_label_release_date);
            tvOverview = itemView.findViewById(R.id.tv_label_overview);
        }
    }
}
