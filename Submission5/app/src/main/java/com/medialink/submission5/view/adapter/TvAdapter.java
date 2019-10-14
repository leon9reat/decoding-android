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
import com.medialink.submission5.model.tv.TvResult;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {

    private static final String TAG = "TvAdapter";
    private Context context;
    private ArrayList<TvResult> listTv = new ArrayList<>();
    private MainContract.TvInterface mView;

    public TvAdapter(Context context, MainContract.TvInterface mView) {
        this.context = context;
        this.mView = mView;
    }

    public void setData(ArrayList<TvResult> items) {
        listTv.clear();
        listTv.addAll(items);
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
        final TvResult tv = listTv.get(position);
        String airingLabel = context.getString(R.string.label_airing_date);

        holder.tvTitle.setText(tv.getName());
        holder.tvRelease.setText(String.format("%s : %s",
                airingLabel,
                tv.getFirstAirDate()));
        holder.tvOverview.setText(tv.getOverview());

        if (tv.getPosterPath() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(BuildConfig.ImageUrl+tv.getPosterPath())
                    .into(holder.imgPoster);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.listClick(tv, position);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_label_title);
            tvRelease = itemView.findViewById(R.id.tv_label_release_date);
            tvOverview = itemView.findViewById(R.id.tv_label_overview);
        }
    }
}
