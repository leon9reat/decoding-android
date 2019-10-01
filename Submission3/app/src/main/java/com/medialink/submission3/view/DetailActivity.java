package com.medialink.submission3.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission3.BuildConfig;
import com.medialink.submission3.Const;
import com.medialink.submission3.DetailContract;
import com.medialink.submission3.R;
import com.medialink.submission3.model.MainViewModel;
import com.medialink.submission3.model.movie.MovieCastItem;
import com.medialink.submission3.model.movie.MovieDetailRespon;
import com.medialink.submission3.model.tv.TvCastItem;
import com.medialink.submission3.model.tv.TvDetailRespon;
import com.medialink.submission3.presenter.DetailPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity
        implements DetailContract.ViewInterface {

    @BindView(R.id.tv_title_label)
    TextView tvTitleLabel;
    @BindView(R.id.img_cover)
    ImageView imgCover;
    @BindView(R.id.tv_status_label)
    TextView tvStatusLabel;
    @BindView(R.id.tv_release_date_label)
    TextView tvReleaseDateLabel;
    @BindView(R.id.tv_genre_label)
    TextView tvGenreLabel;
    @BindView(R.id.tv_runtime_label)
    TextView tvRuntimeLabel;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_genre)
    TextView tvGenre;
    @BindView(R.id.tv_runtime)
    TextView tvRuntime;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.img_cast1)
    ImageView imgCast1;
    @BindView(R.id.img_cast3)
    ImageView imgCast3;
    @BindView(R.id.img_cast5)
    ImageView imgCast5;
    @BindView(R.id.img_cast4)
    ImageView imgCast4;
    @BindView(R.id.img_cast2)
    ImageView imgCast2;
    @BindView(R.id.tv_cast1)
    TextView tvCast1;
    @BindView(R.id.tv_cast2)
    TextView tvCast2;
    @BindView(R.id.tv_cast3)
    TextView tvCast3;
    @BindView(R.id.tv_cast4)
    TextView tvCast4;
    @BindView(R.id.tv_cast5)
    TextView tvCast5;
    @BindView(R.id.tv_cast_label)
    TextView tvCastLabel;
    @BindView(R.id.tv_overview_label)
    TextView tvOverviewLabel;
    @BindView(R.id.tv_score_label)
    TextView tvScoreLabel;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.btn_favorite)
    Button btnFavorite;
    @BindView(R.id.btn_rate)
    Button btnRate;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.detail_progress)
    ProgressBar detailProgress;

    private MainViewModel mModel;
    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mModel = ViewModelProviders.of(this).get(MainViewModel.class);


        mPresenter = new DetailPresenter(this, this);


        int id = getIntent().getIntExtra(Const.KEY_ID, 0);
        int type = getIntent().getIntExtra(Const.KEY_TYPE, 0);

        if (type == Const.INTENT_MOVIE) {
            // movie
            mModel.getMovieDetail().observe(this, getMovieDetail);
            mModel.getMovieCast().observe(this, getMovieCast);

            mPresenter.getMovieDetail(id);
            mPresenter.getMovieCredit(id);
        } else {
            // tv show
            mModel.getTvDetail().observe(this, getTvDetail);
            mModel.getTvCast().observe(this, getTvCast);

            mPresenter.getTvDetail(id);
            mPresenter.getTvCredit(id);
        }

    }

    private Observer<MovieDetailRespon> getMovieDetail = new Observer<MovieDetailRespon>() {
        @Override
        public void onChanged(MovieDetailRespon movieDetailRespon) {
            setMovieView(movieDetailRespon);
        }
    };

    private Observer<ArrayList<MovieCastItem>> getMovieCast = new Observer<ArrayList<MovieCastItem>>() {
        @Override
        public void onChanged(ArrayList<MovieCastItem> castItems) {
            setCastView(castItems);
        }
    };

    private Observer<TvDetailRespon> getTvDetail = new Observer<TvDetailRespon>() {
        @Override
        public void onChanged(TvDetailRespon tvDetailRespon) {
            setTvView(tvDetailRespon);
        }
    };

    private Observer<ArrayList<TvCastItem>> getTvCast = new Observer<ArrayList<TvCastItem>>() {
        @Override
        public void onChanged(ArrayList<TvCastItem> castItems) {
            setTvCastView(castItems);
            Log.d("test", "onChanged: "+castItems.size());
        }
    };

    private void setCastView(ArrayList<MovieCastItem> list) {
        if (list != null) {
            if (list.get(0) != null) {
                tvCast1.setText(list.get(0).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(0).getProfilePath())
                        .circleCrop()
                        .into(imgCast1);
            }
            if (list.get(1) != null) {
                tvCast2.setText(list.get(1).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(1).getProfilePath())
                        .circleCrop()
                        .into(imgCast2);
            }
            if (list.get(2) != null) {
                tvCast3.setText(list.get(2).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(2).getProfilePath())
                        .circleCrop()
                        .into(imgCast3);
            }
            if (list.get(3) != null) {
                tvCast4.setText(list.get(3).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(3).getProfilePath())
                        .circleCrop()
                        .into(imgCast4);
            }
            if (list.get(4) != null) {
                tvCast5.setText(list.get(4).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(4).getProfilePath())
                        .circleCrop()
                        .into(imgCast5);
            }
        }
    }

    @Override
    public void setMovie(MovieDetailRespon movieDetail) {

    }

    @Override
    public void setError(String msg) {
        showLoading(false);
        showMessage("Error: " + msg);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content)
                , msg
                , Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.red));
        snack.show();
    }

    @Override
    public void showLoading(Boolean state) {
        if (state) {
            detailProgress.setVisibility(View.VISIBLE);
        } else {
            detailProgress.setVisibility(View.GONE);
        }
    }

    private void setMovieView(MovieDetailRespon movie) {
        tvTitleLabel.setText(movie.getTitle());
        tvReleaseDateLabel.setText(getString(R.string.label_release_date));
        tvReleaseDate.setText(movie.getReleaseDate());
        tvOverview.setText(movie.getOverview());
        tvScore.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + movie.getPosterPath())
                .fitCenter()
                .into(imgCover);

        String labelRuntime = getString(R.string.label_runtime_movie);
        tvRuntimeLabel.setText(labelRuntime);

        int min = movie.getRuntime();
        int hr = min / 60;
        int mn = min % 60;
        String text_hr = hr + " " + getResources().getString(R.string.jam);
        String text_mn = mn + " " + getResources().getString(R.string.menit);
        tvStatus.setText(movie.getStatus());
        tvRuntime.setText(text_hr + " " + text_mn);

        if (movie.getGenres() != null) {
            String genre = "";
            for (int i = 0; i < movie.getGenres().size(); i++) {
                if (genre.isEmpty()) {
                    genre = movie.getGenres().get(i).getName();
                } else {
                    genre = genre + ", " + movie.getGenres().get(i).getName();
                }
            }
            tvGenre.setText(genre);
        }
    }

    private void setTvView(TvDetailRespon tv) {
        tvTitleLabel.setText(tv.getName());
        tvReleaseDateLabel.setText(getString(R.string.label_release_date_tv));
        tvReleaseDate.setText(tv.getFirstAirDate());
        tvOverview.setText(tv.getOverview());
        tvScore.setText(String.valueOf(tv.getVoteAverage()));

        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + tv.getPosterPath())
                .fitCenter()
                .into(imgCover);

        int season = tv.getNumberOfSeasons();
        int episode = tv.getNumberOfEpisodes();
        String labelRuntime = getString(R.string.label_runtime_tv);
        tvRuntimeLabel.setText(labelRuntime);
        tvStatus.setText(tv.getStatus());
        tvRuntime.setText(season + " / " + episode);

        if (tv.getGenres() != null) {
            String genre = "";
            for (int i = 0; i < tv.getGenres().size(); i++) {
                if (genre.isEmpty()) {
                    genre = tv.getGenres().get(i).getName();
                } else {
                    genre = genre + ", " + tv.getGenres().get(i).getName();
                }
            }
            tvGenre.setText(genre);
        }
    }

    private void setTvCastView(ArrayList<TvCastItem> list) {
        if (list != null) {
            if (list.get(0) != null) {
                tvCast1.setText(list.get(0).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(0).getProfilePath())
                        .circleCrop()
                        .into(imgCast1);
            }
            if (list.get(1) != null) {
                tvCast2.setText(list.get(1).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(1).getProfilePath())
                        .circleCrop()
                        .into(imgCast2);
            }
            if (list.get(2) != null) {
                tvCast3.setText(list.get(2).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(2).getProfilePath())
                        .circleCrop()
                        .into(imgCast3);
            }
            if (list.get(3) != null) {
                tvCast4.setText(list.get(3).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(3).getProfilePath())
                        .circleCrop()
                        .into(imgCast4);
            }
            if (list.get(4) != null) {
                tvCast5.setText(list.get(4).getName());
                Glide.with(getApplicationContext())
                        .load(BuildConfig.ImageUrl + list.get(4).getProfilePath())
                        .circleCrop()
                        .into(imgCast5);
            }
        }
    }
}
