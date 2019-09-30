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
import com.medialink.decomovie.model.TvShow;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private List<TvShow> listTv;
    private TvCallback tvCallback;

    public void setTvCallback(TvCallback tvCallback) {
        this.tvCallback = tvCallback;
    }

    public TvAdapter(List<TvShow> listTv) {
        this.listTv = listTv;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        final TvShow tv = listTv.get(position);

        holder.tvTitle.setText(tv.getName());
        holder.tvReleaseDate.setText(tv.getFirstAirDate());
        holder.tvOverview.setText(tv.getOverview());
        Glide.with(holder.itemView.getContext())
                .load(MainActivity.URL_IMAGE+tv.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCallback.onItemClicked(tv);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvReleaseDate, tvOverview;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title_label);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date_label);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }

    public interface TvCallback {
        void onItemClicked(TvShow data);
    }
}
