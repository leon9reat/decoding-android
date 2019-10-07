package com.medialink.submission4.view.adapter;

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
import com.medialink.submission4.BuildConfig;
import com.medialink.submission4.R;
import com.medialink.submission4.TvContract;
import com.medialink.submission4.model.tv.TvItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private String TAG = TvAdapter.class.getSimpleName();
    private ArrayList<TvItem> listTv = new ArrayList<>();
    private TvContract.ViewInterface mView;
    private Context context;

    // constructor
    public TvAdapter(Context context, TvContract.ViewInterface mView) {
        this.context = context;
        this.mView = mView;
    }

    public void setData(ArrayList<TvItem> items) {
        listTv.clear();
        listTv.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tv, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        final TvItem tv = listTv.get(position);

        String airLabel = context.getString(R.string.label_first_air);

        holder.tvTvTitle.setText(tv.getName());
        holder.tvTvFirstAir.setText(airLabel + ": " + tv.getFirstAirDate());
        holder.tvTvOverview.setText(tv.getOverview());
        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.ImageUrl + tv.getPosterPath())
                .into(holder.imgTvPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.itemClick(tv, position);
                Log.d(TAG, "onClick: Tv View Holder");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTv == null ? 0 : listTv.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_tv_poster)
        ImageView imgTvPoster;
        @BindView(R.id.tv_tv_title)
        TextView tvTvTitle;
        @BindView(R.id.tv_tv_first_air)
        TextView tvTvFirstAir;
        @BindView(R.id.tv_tv_overview)
        TextView tvTvOverview;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
