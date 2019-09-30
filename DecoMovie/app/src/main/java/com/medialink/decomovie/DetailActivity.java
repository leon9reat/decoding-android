package com.medialink.decomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.medialink.decomovie.adapter.RetrofitClient;
import com.medialink.decomovie.adapter.RetrofitInterface;
import com.medialink.decomovie.fragment.MovieFragment;
import com.medialink.decomovie.fragment.TvFragment;
import com.medialink.decomovie.model.Cast;
import com.medialink.decomovie.model.CreditRespon;
import com.medialink.decomovie.model.Movie;
import com.medialink.decomovie.model.MovieDetail;
import com.medialink.decomovie.model.TvDetail;
import com.medialink.decomovie.model.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private static final String DATA = "DATA";
    private static final String DATA_DETAIL = "DATA_DETAIL";
    private static final String CAST_LIST = "cast_list";

    TextView tvStatus, tvReleaseDate, tvGenre, tvRuntime,
            tvCast1, tvCast2, tvCast3, tvCast4, tvCast5,
            tvScore, tvOverview, tvTitle;
    ImageView imgCover, imgCast1, imgCast2, imgCast3, imgCast4, imgCast5;
    List<Cast> listCast;
    Movie mMovie;
    TvShow mTv;
    MovieDetail movieDetail;
    TvDetail tvDetail;
    int listType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();


        if (savedInstanceState == null) {
            listType = getIntent().getIntExtra(MainActivity.TYPE_LIST, 101);

            if (listType == 101) {
                // movie
                Movie movie = getIntent().getParcelableExtra(MovieFragment.MOVIE);
                if (movie != null) {
                    mMovie = movie;

                    loadMovie(mMovie);
                    addDataMovieCredit(movie.getId());
                    addDetailMovie(movie.getId());
                }
            } else if (listType == 102) {
                // tv show
                TvShow tv = getIntent().getParcelableExtra(TvFragment.TV);
                if (tv != null) {
                    mTv = tv;

                    loadTv(mTv);
                    addDataTvCredit(tv.getId());
                    addDetailTv(tv.getId());
                }

            }
        } else {
            listType = savedInstanceState.getInt(MainActivity.TYPE_LIST);

            if (listType == 101) {
                // movie
                listCast = savedInstanceState.getParcelableArrayList(CAST_LIST);
                mMovie = savedInstanceState.getParcelable(DATA);
                movieDetail = savedInstanceState.getParcelable(DATA_DETAIL);
                if (movieDetail != null) {
                    loadDetailMovie(movieDetail);
                }
                if (mMovie != null) {
                    loadMovie(mMovie);
                }
                if (listCast != null) {
                    loadDataCast(listCast);
                }
            } else if (listType == 102) {
                // tv
                mTv = savedInstanceState.getParcelable(DATA);
                listCast = savedInstanceState.getParcelableArrayList(CAST_LIST);
                tvDetail = savedInstanceState.getParcelable(DATA_DETAIL);
                if (mTv != null) {
                    loadTv(mTv);
                }
                if (tvDetail != null) {
                    loadDetailTv(tvDetail);
                }
                if (listCast != null) {
                    loadDataCast(listCast);
                }
            }
        }
    }

    private void addDataTvCredit(int id) {
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        Call<CreditRespon> call = service.getCredit(id);
        call.enqueue(new Callback<CreditRespon>() {
            @Override
            public void onResponse(Call<CreditRespon> call, Response<CreditRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listCast = response.body().getCast();
                        loadDataCast(listCast);
                    }
                }
            }

            @Override
            public void onFailure(Call<CreditRespon> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load cast", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDetailTv(int id) {
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        // setting bahasa berubah
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id-ID";
        } else if (lang.equals("en")) {
            lang = "en-US";
        }
        Call<TvDetail> call = service.getTvDetail(id, lang);
        call.enqueue(new Callback<TvDetail>() {
            @Override
            public void onResponse(Call<TvDetail> call, Response<TvDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tvDetail = response.body();
                        loadDetailTv(tvDetail);
                    }
                }
            }

            @Override
            public void onFailure(Call<TvDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load detail movie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDetailTv(TvDetail tvDetail) {
        int min = tvDetail.getEpisodeRunTime().get(0);
        int hr = min / 60;
        int mn = min % 60;
        String text_hr = hr + " " + getResources().getString(R.string.jam);
        String text_mn = mn + " " + getResources().getString(R.string.menit);
        tvStatus.setText(tvDetail.getStatus());
        tvRuntime.setText(text_hr + " " + text_mn);
    }

    private void loadTv(TvShow tv) {
        tvTitle.setText(tv.getName());
        tvReleaseDate.setText(tv.getFirstAirDate());
        tvOverview.setText(tv.getOverview());
        tvScore.setText(String.valueOf(tv.getVoteAverage()));

        Glide.with(getApplicationContext())
                .load(MainActivity.URL_IMAGE + tv.getPosterPath())
                .into(imgCover);
    }


    private void initView() {
        tvTitle = findViewById(R.id.tv_title_label);
        tvStatus = findViewById(R.id.tv_status);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvGenre = findViewById(R.id.tv_genre);
        tvRuntime = findViewById(R.id.tv_runtime);
        tvCast1 = findViewById(R.id.tv_cast1);
        tvCast2 = findViewById(R.id.tv_cast2);
        tvCast3 = findViewById(R.id.tv_cast3);
        tvCast4 = findViewById(R.id.tv_cast4);
        tvCast5 = findViewById(R.id.tv_cast5);
        tvScore = findViewById(R.id.tv_score);
        tvOverview = findViewById(R.id.tv_overview);
        imgCover = findViewById(R.id.img_cover);
        imgCast1 = findViewById(R.id.img_cast1);
        imgCast2 = findViewById(R.id.img_cast2);
        imgCast3 = findViewById(R.id.img_cast3);
        imgCast4 = findViewById(R.id.img_cast4);
        imgCast5 = findViewById(R.id.img_cast5);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MainActivity.TYPE_LIST, listType);
        outState.putParcelableArrayList(CAST_LIST, (ArrayList<? extends Parcelable>) listCast);

        if (listType == 101) {
            // movie
            outState.putParcelable(DATA, mMovie);
            outState.putParcelable(DATA_DETAIL, movieDetail);
        } else if (listType == 102) {
            // tv show
            outState.putParcelable(DATA, mTv);
            outState.putParcelable(DATA_DETAIL, tvDetail);
        }
    }

    private void loadMovie(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvOverview.setText(movie.getOverview());
        tvScore.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(getApplicationContext())
                .load(MainActivity.URL_IMAGE + movie.getPosterPath())
                .into(imgCover);
    }

    public void addDataMovieCredit(int id) {
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        Call<CreditRespon> call = service.getCredit(id);
        call.enqueue(new Callback<CreditRespon>() {
            @Override
            public void onResponse(Call<CreditRespon> call, Response<CreditRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listCast = response.body().getCast();
                        loadDataCast(listCast);

                    }
                }
            }

            @Override
            public void onFailure(Call<CreditRespon> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load cast", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataCast(List<Cast> list) {
        if (list != null) {
            if (list.size() > 6) {
                tvCast1.setText(list.get(0).getName());
                tvCast2.setText(list.get(1).getName());
                tvCast3.setText(list.get(2).getName());
                tvCast4.setText(list.get(3).getName());
                tvCast5.setText(list.get(4).getName());

                Glide.with(getApplicationContext())
                        .load(MainActivity.URL_IMAGE + list.get(0).getProfilePath())
                        .circleCrop()
                        .into(imgCast1);
                Glide.with(getApplicationContext())
                        .load(MainActivity.URL_IMAGE + list.get(1).getProfilePath())
                        .circleCrop()
                        .into(imgCast2);
                Glide.with(getApplicationContext())
                        .load(MainActivity.URL_IMAGE + list.get(2).getProfilePath())
                        .circleCrop()
                        .into(imgCast3);
                Glide.with(getApplicationContext())
                        .load(MainActivity.URL_IMAGE + list.get(3).getProfilePath())
                        .circleCrop()
                        .into(imgCast4);
                Glide.with(getApplicationContext())
                        .load(MainActivity.URL_IMAGE + list.get(4).getProfilePath())
                        .circleCrop()
                        .into(imgCast5);
            }
        }
    }

    public void addDetailMovie(int id) {
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        // setting bahasa berubah
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id-ID";
        } else if (lang.equals("en")) {
            lang = "en-US";
        }
        Call<MovieDetail> call = service.getMovieDetail(id, lang);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieDetail = response.body();
                        loadDetailMovie(movieDetail);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load detail movie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDetailMovie(MovieDetail movieDetail) {
        int min = movieDetail.getRuntime();
        int hr = min / 60;
        int mn = min % 60;
        String text_hr = hr + " " + getResources().getString(R.string.jam);
        String text_mn = mn + " " + getResources().getString(R.string.menit);
        tvStatus.setText(movieDetail.getStatus());
        tvRuntime.setText(text_hr + " " + text_mn);
    }
}
