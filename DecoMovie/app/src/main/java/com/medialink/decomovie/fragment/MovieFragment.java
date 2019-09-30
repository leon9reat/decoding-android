package com.medialink.decomovie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medialink.decomovie.DetailActivity;
import com.medialink.decomovie.MainActivity;
import com.medialink.decomovie.R;
import com.medialink.decomovie.adapter.MovieAdapter;
import com.medialink.decomovie.adapter.RetrofitClient;
import com.medialink.decomovie.adapter.RetrofitInterface;
import com.medialink.decomovie.model.Movie;
import com.medialink.decomovie.model.MovieRespon;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final String STATE_LIST = "STATE_LIST";
    public static final String MOVIE = "MOVIE";

    private RecyclerView rvMovie;
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movie);

        if (savedInstanceState == null) {
            addDataMovie();
            Log.d("debug", "load data baru");
        } else {
            //title = savedInstanceState.getString(STATE_TITLE);
            ArrayList<Movie> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            //int stateMode = savedInstanceState.getInt(STATE_MODE);
            if (stateList != null) {
                listMovie.addAll(stateList);
                loadDataList(listMovie);
                Log.d("debug", "load data lama");
            }
            //setMode(stateMode);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(STATE_TITLE, title);
        outState.putParcelableArrayList(STATE_LIST, listMovie);
        //outState.putInt(STATE_MODE, mode);
    }

    public void addDataMovie() {
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        // setting bahasa berubah
        String lang =  Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id-ID";
        } else if (lang.equals("en")) {
            lang = "en-US";
        }
        Call<MovieRespon> call = service.getMovie(1, lang);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listMovie = response.body().getResults();
                        loadDataList(listMovie);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                Toast.makeText(getContext(), "Unable to load movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataList(ArrayList<Movie> list) {
        MovieAdapter movieAdapter = new MovieAdapter(list);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);
        movieAdapter.setMovieCallback(new MovieAdapter.MovieCallback() {
            @Override
            public void onItemClicked(Movie data) {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(MainActivity.TYPE_LIST, 101);
                intent.putExtra(MOVIE, data);
                startActivity(intent);
                //Toast.makeText(getContext(), data.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
