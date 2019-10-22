package com.medialink.sub5consumer.view.favorite;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.medialink.sub5consumer.Const;
import com.medialink.sub5consumer.R;
import com.medialink.sub5consumer.contract.FavoriteContract;
import com.medialink.sub5consumer.model.local.FavoriteItem;
import com.medialink.sub5consumer.presenter.FavoritePresenter;
import com.medialink.sub5consumer.view.adapter.TvFavAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavFragment extends Fragment
        implements FavoriteContract.TvFavInterface{

    private static final String TAG = "TvFavFragment";
    private static final String KEY_LIST_TV = "LIST_TV";
    private static final String KEY_PAGE = "PAGE";

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
        mFavPresenter.setDatabase();

        mAdapter = new TvFavAdapter(getContext(), mListTv, this);

        if (getContext() != null) {
            tvFavRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            tvFavRecycler.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            tvFavRecycler.setAdapter(mAdapter);
        }

        mFavPresenter.getTvProvider(getContext());

        return view;
    }

    private void initView(View v) {
        tvFavProgress = v.findViewById(R.id.progress_favorite_tv);
        tvFavRecycler = v.findViewById(R.id.rv_favorite_tv);

        showLoading(false);
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
    public void listClick(FavoriteItem tv) {
        mFavView.showDetail(Const.DETAIL_TV, tv.getMovieId(), tv);
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
                        Uri uriWithId = Uri.parse(Const.URI_FAVORITE + "/" + tv.getId());
                        getContext().getContentResolver().delete(uriWithId, null, null);

                        mAdapter.removeItem(position);
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
