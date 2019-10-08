package com.medialink.submission4.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission4.Const;
import com.medialink.submission4.FavoriteMovieContract;
import com.medialink.submission4.FavoriteTvContract;
import com.medialink.submission4.R;
import com.medialink.submission4.database.FavoriteHelper;
import com.medialink.submission4.helper.MappingHelper;
import com.medialink.submission4.model.FavoriteItem;
import com.medialink.submission4.model.movie.MovieItem;
import com.medialink.submission4.presenter.FavoritePresenter;
import com.medialink.submission4.view.adapter.TvFavoriteAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavoriteFragment extends Fragment
    implements LoadTvFavoriteCallback, FavoriteTvContract.ViewInterface {

    private static final String TAG = "TvFavoriteFragment";
    @BindView(R.id.favorite_progress_tv)
    ProgressBar favoriteProgressTv;
    @BindView(R.id.favorite_rv_tv)
    RecyclerView favoriteRvTv;
    @BindView(R.id.favorite_tv_layout)
    CoordinatorLayout favoriteTvLayout;

    Unbinder unbinder;
    private FavoriteMovieContract.PresenterInterface mPresenter;
    private TvFavoriteAdapter mAdapter;
    private FavoriteHelper favoriteHelper;
    private static final String EXTRA_STATE = "extra_state";


    public TvFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new FavoritePresenter(this, getContext());

        favoriteRvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteRvTv.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        favoriteRvTv.setHasFixedSize(true);
        mAdapter = new TvFavoriteAdapter(getContext(), this);
        favoriteRvTv.setAdapter(mAdapter);

        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();

        if (savedInstanceState == null) {
            // proses ambil data
            new LoadFavoriteAsync(favoriteHelper, this).execute();
        } else {
            ArrayList<FavoriteItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                mAdapter.setFavItem(list);
            }
            favoriteProgressTv.setVisibility(View.GONE);
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, mAdapter.getFavItem());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setMovie(ArrayList<MovieItem> list) {

    }

    @Override
    public void itemFavoriteClick(FavoriteItem tv, int position) {
        Intent i = new Intent(getContext(), DetailActivity.class);
        i.putExtra(Const.KEY_ID, tv.getMovie_id());
        i.putExtra(Const.KEY_TYPE, Const.INTENT_TV);
        i.putExtra("POSITION", position);
        startActivityForResult(i, 103);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 103) {
                if (resultCode == 1031) {
                    int position = data.getIntExtra("POSITION", 0);
                    mAdapter.removeItem(position);
                    showSnackbarMessage("Satu item berhasil dihapus");
                }
            }
        }
    }

    @Override
    public void itemDelete(String id, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Hapus Favorite");
        alertDialogBuilder
                .setMessage("Anda Yakin Ingin Hapus Favorite Ini?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        mPresenter.itemDelete(id, position);
                        Log.d(TAG, "itemDelete: hapus disini");
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void setError(String msg) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void refreshAddItem(FavoriteItem favItem) {

    }

    @Override
    public void refreshUpdateItem(int position, FavoriteItem favItem) {

    }

    @Override
    public void refreshRemoveItem(int position) {
        mAdapter.removeItem(position);
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                favoriteProgressTv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<FavoriteItem> listFav) {
        favoriteProgressTv.setVisibility(View.GONE);
        if (listFav.size() > 0) {
            mAdapter.setFavItem(listFav);
        } else {
            mAdapter.setFavItem(new ArrayList<>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<FavoriteItem>> {
        private final WeakReference<FavoriteHelper> weakFavoriteHelper;
        private final WeakReference<LoadTvFavoriteCallback> weakCallback;

        public LoadFavoriteAsync(FavoriteHelper favHelper, LoadTvFavoriteCallback callback) {
            this.weakFavoriteHelper = new WeakReference<>(favHelper);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<FavoriteItem> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavoriteHelper.get().queryAll(Const.TV_TYPE);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteItem> favoriteItems) {
            super.onPostExecute(favoriteItems);
            weakCallback.get().postExecute(favoriteItems);
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(favoriteTvLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}

interface LoadTvFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<FavoriteItem> notes);
}
