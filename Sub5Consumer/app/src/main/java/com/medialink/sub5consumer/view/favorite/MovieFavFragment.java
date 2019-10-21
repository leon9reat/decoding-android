package com.medialink.sub5consumer.view.favorite;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.medialink.sub5consumer.R;
import com.medialink.sub5consumer.contract.FavoriteContract;
import com.medialink.sub5consumer.model.local.FavoriteItem;
import com.medialink.sub5consumer.presenter.FavoritePresenter;
import com.medialink.sub5consumer.view.adapter.MovieFavAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment
        implements FavoriteContract.MovieFavInterface{

    private static final String TAG = "MovieFavFragment";
    private static final String KEY_LIST_MOVIE = "LIST_MOVIE";
    private static final String KEY_PAGE = "PAGE";

    private ProgressBar movieFavProgress;
    private RecyclerView movieFavRecycler;

    private MovieFavAdapter mAdapter;
    private FavoriteContract.FavoriteInterface mFavView;
    private FavoriteContract.PresenterFavInterface mFavPresenter;
    private List<FavoriteItem> mListMovie;


    public MovieFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);

        initView(view);

        mFavPresenter = FavoritePresenter.getInstance();
        mFavPresenter.setFavMovieView(this);
        mFavPresenter.setDatabase(getContext());

        mAdapter = new MovieFavAdapter(getContext(), mListMovie, this);

        if (getContext() != null) {
            movieFavRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            movieFavRecycler.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            movieFavRecycler.setAdapter(mAdapter);
        }

        //mFavPresenter.getMovieProvider(getContext());
        mFavPresenter.getMovie();

        return view;
    }

    private void initView(View v) {
        movieFavProgress = v.findViewById(R.id.progress_favorite_movie);
        movieFavRecycler = v.findViewById(R.id.rv_favorite_movie);

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
            movieFavProgress.setVisibility(View.VISIBLE);
        } else {
            movieFavProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMovie(List<FavoriteItem> list) {
        mAdapter.setData(list);
        showLoading(false);
        Log.d(TAG, "showMovie: "+list.size());
    }

    @Override
    public void listClick(FavoriteItem movie, int position) {

    }

    @Override
    public void itemDelete(final FavoriteItem movie, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(getString(R.string.alert_delete_movie_title));
        alertDialogBuilder
                .setMessage(getString(R.string.alert_delete_movie_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.label_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        mFavPresenter.deleteMovie(movie);
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
