package com.medialink.submission3.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission3.Const;
import com.medialink.submission3.MovieContract;
import com.medialink.submission3.R;
import com.medialink.submission3.model.MainViewModel;
import com.medialink.submission3.model.movie.MovieItem;
import com.medialink.submission3.presenter.MoviePresenter;
import com.medialink.submission3.view.adapter.MovieAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment
        implements MovieContract.ViewInterface {

    @BindView(R.id.movie_progress)
    ProgressBar movieProgress;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.movie_layout)
    CoordinatorLayout layoutMovie;

    private final static String TAG = MovieFragment.class.getSimpleName();
    private Unbinder unbinder;
    private MovieAdapter mAdapter;
    private MainViewModel mModel;
    private MovieContract.PresenterInterface mPresenter;
    private int mPage = 1;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new MoviePresenter(this);

        if (getActivity() != null ) {
            mModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            mModel.getMovies().observe(MovieFragment.this, getMovies);

            if (mModel.getMovies().getValue() == null) {
                refreshMovie();
            }
        }

        mAdapter = new MovieAdapter(this);
        mAdapter.notifyDataSetChanged();

        if (getContext() != null) {
            rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            rvMovie.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            rvMovie.setAdapter(mAdapter);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    private Observer<ArrayList<MovieItem>> getMovies = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(ArrayList<MovieItem> movieItems) {
            if (movieItems != null) {
                mAdapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            movieProgress.setVisibility(View.VISIBLE);
        } else {
            movieProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setMovie(ArrayList<MovieItem> list) {
        mModel.setListMovies(list);
    }

    @Override
    public void itemClick(MovieItem movie, int position) {
        Intent i = new Intent(getContext(), DetailActivity.class);
        i.putExtra(Const.KEY_ID, movie.getId());
        i.putExtra(Const.KEY_TYPE, Const.INTENT_MOVIE);
        startActivity(i);
        Log.d(TAG, "itemClick: " + movie.getTitle());
    }

    @Override
    public void setError(String msg) {
        showLoading(false);
        showMessage("Error: " + msg);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar snack = Snackbar.make(layoutMovie, msg, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshMovie();                    }
                })
                .setActionTextColor(getResources().getColor(R.color.red));
        snack.show();
    }

    /**
     * biar bisa refresh dari activity
     */
    public void refreshMovie() {
        showLoading(true);
        mPage = 1;
        mPresenter.getMovies(mPage);
    }
}
