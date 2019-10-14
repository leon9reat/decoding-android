package com.medialink.submission5.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medialink.submission5.BuildConfig;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.FavoriteItem;

import java.util.List;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.MyViewHolder> {

    private static final String TAG = "MovieFavAdapter";
    private Context mContext;
    private List<FavoriteItem> listMovie;
    private FavoriteContract.MovieFavInterface mFavMovie;

    public MovieFavAdapter(Context context, List<FavoriteItem> listMovie,
                           FavoriteContract.MovieFavInterface favMovie) {
        this.mContext = context;
        this.listMovie = listMovie;
        this.mFavMovie = favMovie;
    }

    public void setData(List<FavoriteItem> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    public void addItem(FavoriteItem favItem) {
        this.listMovie.add(favItem);
        notifyItemInserted(listMovie.size() - 1);
    }

    public void updateItem(int position, FavoriteItem favItem) {
        this.listMovie.set(position, favItem);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        this.listMovie.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovie.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final FavoriteItem item = listMovie.get(position);
        String releaseLabel = mContext.getString(R.string.label_release_date);

        holder.tvTitle.setText(item.getTitle());
        holder.tvRelease.setText(String.format("%s : %s",
                releaseLabel,
                item.getReleaseDate()));
        holder.tvOverview.setText(item.getOverview());
        if (item.getPosterPath() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(BuildConfig.ImageUrl + item.getPosterPath())
                    .into(holder.imgPoster);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFavMovie.listClick(item, position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFavMovie.itemDelete(item, position);
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
        ImageButton btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title_favorite);
            tvRelease = itemView.findViewById(R.id.tv_release_favorite);
            tvOverview = itemView.findViewById(R.id.tv_overview_favorite);
            btnDelete = itemView.findViewById(R.id.btn_delete_favorite);
        }
    }
}
