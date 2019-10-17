package com.medialink.submission5.view.main;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.MovieViewModel;
import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.presenter.MainPresenter;
import com.medialink.submission5.view.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment
    implements MainContract.MovieInterface {

    private static final String TAG = "MovieFragment";
    private static final String KEY_LIST_MOVIE = "LIST_MOVIE";

    private CoordinatorLayout coordinatorMovie;
    private ProgressBar movieProgress;
    private RecyclerView movieRecycler;

    private MovieAdapter mAdapter;
    private MainContract.MainInterface mMainView;
    private MainContract.PresenterInterface mPresenter;
    private ArrayList<MovieResult> mListMovie = new ArrayList<>();
    private MovieViewModel model;

    public MovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        initView(view);

        mPresenter = MainPresenter.getInstance();
        mPresenter.setMovieView(this);

        mAdapter = new MovieAdapter(getContext(), this);

        model = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        model.getMovieList(this).observe(MovieFragment.this, new Observer<List<MovieResult>>() {
            @Override
            public void onChanged(List<MovieResult> movieResults) {
                showMovie((ArrayList<MovieResult>) movieResults);
            }
        });

        if (getContext() != null) {
            movieRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            movieRecycler.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            movieRecycler.setAdapter(mAdapter);
        }

        /*
        if (savedInstanceState == null) {
            // loading pertama
            mPresenter.setMovie(mPage);
            Log.d(TAG, "onCreateView: pertama");
        } else {
            // loading berikut
            ArrayList<MovieResult> list = savedInstanceState.getParcelableArrayList(KEY_LIST_MOVIE);
            mPage = savedInstanceState.getInt(KEY_PAGE);

            showMovie(list);
        }
         */
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LIST_MOVIE, mListMovie);

        Log.d(TAG, "onSaveInstanceState: "+mListMovie.size());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainContract.MainInterface) {
            mMainView = (MainContract.MainInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainView = null;
    }

    private void initView(View view) {
        coordinatorMovie = view.findViewById(R.id.coordinator_movie);
        movieProgress = view.findViewById(R.id.progress_movie);
        movieRecycler = view.findViewById(R.id.rv_main_movie);

        showLoading(false);
    }

    @Override
    public void getMovie() {
        model.setMovie();
    }

    @Override
    public void getMovieFilter(String s) {
        model.setMovieFilter(s);
    }

    @Override
    public void getMovieRelease(String drTgl, String spTgl) {
        model.setMovieRelease(drTgl, spTgl);
    }

    @Override
    public void showMovie(ArrayList<MovieResult> list) {
        mListMovie.clear();
        mListMovie.addAll(list);

        mAdapter.setData(mListMovie);
    }

    @Override
    public void listClick(MovieResult movie, int position) {
        mMainView.showDetail(Const.DETAIL_MOVIE, movie.getId());
    }

    @Override
    public void showLoading(Boolean state) {
        if (state) {
            movieProgress.setVisibility(View.VISIBLE);
        } else {
            movieProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar snack = Snackbar.make(coordinatorMovie, msg, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMovie();                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light));
        snack.show();
    }
}
