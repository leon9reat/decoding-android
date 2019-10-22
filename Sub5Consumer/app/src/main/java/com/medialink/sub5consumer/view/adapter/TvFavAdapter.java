package com.medialink.sub5consumer.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medialink.sub5consumer.BuildConfig;
import com.medialink.sub5consumer.R;
import com.medialink.sub5consumer.contract.FavoriteContract;
import com.medialink.sub5consumer.model.local.FavoriteItem;

import java.util.List;

public class TvFavAdapter extends RecyclerView.Adapter<TvFavAdapter.MyViewHolder> {
    private static final String TAG = "TvFavAdapter";
    private Context mContext;
    private List<FavoriteItem> listTv;
    private FavoriteContract.TvFavInterface mFavTvView;

    public TvFavAdapter(Context context, List<FavoriteItem> listTv, FavoriteContract.TvFavInterface favTvView) {
        this.mContext = context;
        this.listTv = listTv;
        this.mFavTvView = favTvView;
    }

    public void setData(List<FavoriteItem> listTv) {
        this.listTv = listTv;
        notifyDataSetChanged();
    }

    public void addItem(FavoriteItem favItem) {
        this.listTv.add(favItem);
        notifyItemInserted(listTv.size() - 1);
    }

    public void updateItem(int position, FavoriteItem favItem) {
        this.listTv.set(position, favItem);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        this.listTv.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTv.size());
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
        final FavoriteItem item = listTv.get(position);
        String airingLabel = mContext.getString(R.string.label_airing_date);

        holder.tvTitle.setText(item.getTitle());
        holder.tvRelease.setText(String.format("%s : %s",
                airingLabel,
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
                mFavTvView.listClick(item);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFavTvView.itemDelete(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTv == null ? 0 : listTv.size();
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
