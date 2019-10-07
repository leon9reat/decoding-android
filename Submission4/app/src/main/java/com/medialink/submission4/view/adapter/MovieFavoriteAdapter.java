package com.medialink.submission4.view.adapter;

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
import com.medialink.submission4.BuildConfig;
import com.medialink.submission4.FavoriteContract;
import com.medialink.submission4.R;
import com.medialink.submission4.model.FavoriteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MyViewHolder> {

    private static final String TAG = "MovieFavoriteAdapter";
    private ArrayList<FavoriteItem> listFavorite = new ArrayList<>();
    private Context mContext;
    private FavoriteContract.ViewInterface mView;

    public MovieFavoriteAdapter(Context context, FavoriteContract.ViewInterface viewInterface) {
        this.mContext = context;
        this.mView = viewInterface;
    }

    public ArrayList<FavoriteItem> getFavItem() {
        return listFavorite;
    }

    public void setFavItem(ArrayList<FavoriteItem> listFav) {
        if (listFavorite.size() > 0) {
            this.listFavorite.clear();
        }
        this.listFavorite.addAll(listFav);
        notifyDataSetChanged();

        //Log.d(TAG, "onBindViewHolder: proses "+listFavorite.size());
    }

    public void addItem(FavoriteItem favItem) {
        this.listFavorite.add(favItem);
        notifyItemInserted(listFavorite.size() - 1);
    }

    public void updateItem(int position, FavoriteItem favItem) {
        this.listFavorite.set(position, favItem);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        this.listFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavorite.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final FavoriteItem item = listFavorite.get(position);
        String releaseLabel = mContext.getString(R.string.label_release_date);

        holder.tvTitleLabel.setText(item.getTitle());
        holder.tvReleaseDateLabel.setText(releaseLabel + ": " + item.getRelease_date());
        holder.tvOverviewLabel.setText(item.getOverview());
        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.ImageUrl + item.getPoster_path())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.itemClick(item, position);
            }
        });
        holder.btnFavDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.itemDelete(String.valueOf(item.getId()), position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listFavorite == null ? 0 : listFavorite.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_title_label)
        TextView tvTitleLabel;
        @BindView(R.id.tv_release_date_label)
        TextView tvReleaseDateLabel;
        @BindView(R.id.btn_fav_delete)
        ImageButton btnFavDelete;
        @BindView(R.id.tv_overview_label)
        TextView tvOverviewLabel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}


