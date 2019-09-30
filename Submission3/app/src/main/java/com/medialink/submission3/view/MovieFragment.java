package com.medialink.submission3.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medialink.submission3.R;
import com.medialink.submission3.model.MainViewModel;
import com.medialink.submission3.model.movie.MovieItem;
import com.medialink.submission3.view.adapter.MovieAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    @BindView(R.id.movie_progress)
    ProgressBar movieProgress;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    private Unbinder unbinder;
    private MovieAdapter mAdapter;
    private MainViewModel mModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, view);

        mModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mModel.getMovies().observe(MovieFragment.this, getMovies);

        showLoading(true);
        mModel.setMovies(1);

        //mAdapter = new MovieAdapter();

        //rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        //rvMovie.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        //rvMovie.setAdapter(mAdapter);
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

}
