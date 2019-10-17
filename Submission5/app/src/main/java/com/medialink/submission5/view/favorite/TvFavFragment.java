package com.medialink.submission5.view.favorite;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.presenter.FavoritePresenter;
import com.medialink.submission5.view.adapter.TvFavAdapter;
import com.medialink.submission5.widget.MovieWidget;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavFragment extends Fragment
        implements FavoriteContract.TvFavInterface {

    private static final String TAG = "TvFavFragment";

    private ProgressBar tvFavProgress;
    private RecyclerView tvFavRecycler;

    private TvFavAdapter mAdapter;
    private FavoriteContract.FavoriteInterface mFavView;
    private FavoriteContract.PresenterFavInterface mFavPresenter;
    private List<FavoriteItem> mListTv;


    public TvFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_fav, container, false);

        initView(view);

        mFavPresenter = FavoritePresenter.getInstance();
        mFavPresenter.setFavTvView(this);
        mFavPresenter.setDatabase(getContext());

        mAdapter = new TvFavAdapter(getContext(), mListTv, this);

        if (getContext() != null) {
            tvFavRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            tvFavRecycler.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            tvFavRecycler.setAdapter(mAdapter);
        }

        mFavPresenter.getTvProvider(getContext());

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FavoriteContract.FavoriteInterface) {
            mFavView = (FavoriteContract.FavoriteInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFavView = null;
    }

    private void initView(View v) {
        tvFavProgress = v.findViewById(R.id.progress_favorite_tv);
        tvFavRecycler = v.findViewById(R.id.rv_favorite_tv);

        showLoading(false);
    }

    @Override
    public void showLoading(Boolean state) {
        if (state) {
            tvFavProgress.setVisibility(View.VISIBLE);
        } else {
            tvFavProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTv(List<FavoriteItem> list) {
        mAdapter.setData(list);
        showLoading(false);
    }

    @Override
    public void listClick(FavoriteItem tv, int position) {
        mFavView.showDetail(Const.DETAIL_TV, tv.getMovieId());
    }

    @Override
    public void itemDelete(final FavoriteItem tv, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(getString(R.string.alert_delete_tv_title));
        alertDialogBuilder
                .setMessage(getString(R.string.alert_delete_tv_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.label_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        mFavPresenter.deleteTv(tv);
                        mAdapter.removeItem(position);

                        mFavPresenter.refreshWidget();
                    }
                })
                .setNegativeButton(getString(R.string.label_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
