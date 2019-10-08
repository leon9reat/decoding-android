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
import android.widget.Toast;

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
import com.medialink.submission4.R;
import com.medialink.submission4.database.FavoriteHelper;
import com.medialink.submission4.helper.MappingHelper;
import com.medialink.submission4.model.FavoriteItem;
import com.medialink.submission4.model.movie.MovieItem;
import com.medialink.submission4.presenter.FavoritePresenter;
import com.medialink.submission4.view.adapter.MovieFavoriteAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment
        implements LoadMovieFavCallback, FavoriteMovieContract.ViewInterface {


    private Unbinder unbinder;
    private final static String TAG = MovieFavoriteFragment.class.getSimpleName();

    @BindView(R.id.progress_fav_movie)
    ProgressBar progressFavMovie;
    @BindView(R.id.rv_fav_movie)
    RecyclerView rvFavMovie;
    @BindView(R.id.movie_layout)
    CoordinatorLayout movieLayout;

    private FavoritePresenter mPresenter;
    private MovieFavoriteAdapter adapter;
    private FavoriteHelper favoriteHelper;
    private static final String EXTRA_STATE = "extra_state";


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new FavoritePresenter(this, getContext());

        rvFavMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavMovie.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        rvFavMovie.setHasFixedSize(true);
        adapter = new MovieFavoriteAdapter(getContext(), this);
        rvFavMovie.setAdapter(adapter);

        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();

        // proses ambil data
        if (savedInstanceState == null) {
            // proses ambil data
            new LoadFavoriteAsync(favoriteHelper, this).execute();
        } else {
            ArrayList<FavoriteItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setFavItem(list);
            }
            progressFavMovie.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getFavItem());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressFavMovie.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<FavoriteItem> favList) {
        progressFavMovie.setVisibility(View.GONE);
        if (favList.size() > 0) {
            adapter.setFavItem(favList);
        } else {
            adapter.setFavItem(new ArrayList<FavoriteItem>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    @Override
    public void setMovie(ArrayList<MovieItem> list) {

    }

    @Override
    public void itemFavoriteClick(FavoriteItem movie, int position) {
        Intent i = new Intent(getContext(), DetailActivity.class);
        i.putExtra(Const.KEY_ID, movie.getMovie_id());
        i.putExtra(Const.KEY_TYPE, Const.INTENT_MOVIE);
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
                    adapter.removeItem(position);
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshAddItem(FavoriteItem favItem) {
        adapter.addItem(favItem);
    }

    @Override
    public void refreshUpdateItem(int position, FavoriteItem favItem) {
        adapter.updateItem(position, favItem);
    }

    @Override
    public void refreshRemoveItem(int position) {
        adapter.removeItem(position);
    }

    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<FavoriteItem>> {

        private final WeakReference<FavoriteHelper> weakFavoriteHelper;
        private final WeakReference<LoadMovieFavCallback> weakCallback;

        public LoadFavoriteAsync(FavoriteHelper favHelper, LoadMovieFavCallback callback) {
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
            Cursor dataCursor = weakFavoriteHelper.get().queryAll(Const.MOVIE_TYPE);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteItem> favoriteItems) {
            super.onPostExecute(favoriteItems);
            weakCallback.get().postExecute(favoriteItems);
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavMovie, message, Snackbar.LENGTH_SHORT).show();
    }

}

interface LoadMovieFavCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteItem> notes);
}


