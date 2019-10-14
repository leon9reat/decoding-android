package com.medialink.submission5.view.favorite;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.presenter.FavoritePresenter;
import com.medialink.submission5.view.adapter.MovieFavAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment
    implements FavoriteContract.MovieFavInterface {

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
        // Inflate the layout for this fragment
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

        mFavPresenter.getMovie();

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
        movieFavProgress = v.findViewById(R.id.progress_favorite_movie);
        movieFavRecycler = v.findViewById(R.id.rv_favorite_movie);

        showLoading(false);
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
        mFavView.showDetail(Const.DETAIL_MOVIE, movie.getMovieId());
    }

    @Override
    public void itemDelete(FavoriteItem movie, int position) {
        mFavPresenter.deleteMovie(movie);
        mAdapter.removeItem(position);
    }
}
