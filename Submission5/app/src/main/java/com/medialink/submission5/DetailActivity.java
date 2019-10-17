package com.medialink.submission5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission5.contract.DetailContract;
import com.medialink.submission5.model.DetailModel;
import com.medialink.submission5.model.DetailViewModel;
import com.medialink.submission5.model.local.AppDatabase;
import com.medialink.submission5.model.local.FavoriteDao;
import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.presenter.DetailPresenter;

public class DetailActivity extends AppCompatActivity
        implements DetailContract.DetailInterface {

    private static final String TAG = "DetailActivity";
    private static final String PARCELABLE_DETAIL = "PARCELABEL_DETAIL";
    private int detailType = 0;
    private int movieId = 0;

    private ProgressBar detailProgress;

    private TextView tvTitle, tvStatus, tvRelaseDate, tvGenre, tvRuntime,
            tvCast1, tvCast2, tvCast3, tvCast4, tvCast5,
            tvScore, tvOverview,
            tvLabelRelease, tvLabelRuntime;
    private ImageView imgPoster, imgCast1, imgCast2, imgCast3, imgCast4, imgCast5;
    private Button btnFavorite, btnRate;

    private DetailContract.PresenterDetailInterface mPresenter;
    private DetailViewModel model;
    private AppDatabase database;
    private FavoriteDao favDao;
    private FavoriteItem localItemMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        mPresenter = DetailPresenter.getInstance();
        mPresenter.setDetailView(this);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Const.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        favDao = database.getFavoriteDao();

        // saved instance
        if (savedInstanceState == null) {
            Bundle args = getIntent().getExtras();
            if (args != null) {
                detailType = args.getInt(Const.KEY_DETAIL_TYPE, 0);
                movieId = args.getInt(Const.KEY_ID, 0);
            }

            // cek ada difavorite atau tidak
            localItemMovie = favDao.getFavoriteByMovieId(detailType, movieId);
        } else {
            detailType = savedInstanceState.getInt(Const.KEY_DETAIL_TYPE);
            movieId = savedInstanceState.getInt(Const.KEY_ID);
            localItemMovie = savedInstanceState.getParcelable(PARCELABLE_DETAIL);
        }

        String title;
        if (detailType == Const.DETAIL_MOVIE) {
            title = getString(R.string.label_movie);
        } else {
            title = getString(R.string.label_tv);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        model = ViewModelProviders.of(this).get(DetailViewModel.class);
        model.getDetailData(detailType, movieId, this).observe(this, new Observer<DetailModel>() {
            @Override
            public void onChanged(DetailModel detailModel) {
                showData(detailModel);

                if (localItemMovie == null) localItemMovie = new FavoriteItem();
                localItemMovie.setTypeId(detailType);
                localItemMovie.setMovieId(movieId);
                localItemMovie.setPosterPath(detailModel.getPosterPath());
                localItemMovie.setTitle(detailModel.getTitle());
                localItemMovie.setReleaseDate(detailModel.getReleaseDate());
                localItemMovie.setOverview(detailModel.getOverview());

                setFavoriteButtonColor(localItemMovie.getId() != null);
            }
        });

        // ubah warna favorite
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localItemMovie.getId() == null) {
                    Long id = favDao.insert(localItemMovie);
                    localItemMovie.setId(id);
                    Log.d(TAG, "onClick: ID Inserted "+id);
                } else {
                    int count = favDao.delete(localItemMovie);
                    localItemMovie.setId(null);
                    Log.d(TAG, "onClick: Record Deleted "+count);
                }
                setFavoriteButtonColor(localItemMovie.getId() != null);
            }
        });
    }

    private void setFavoriteButtonColor(boolean isFavorite) {
        Drawable img = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
        btnFavorite.setTag(isFavorite);
        img.setBounds(0, 0, 32, 32);
        if (isFavorite) {
            img.setTint(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            img.setTint(getResources().getColor(android.R.color.black));
        }
        btnFavorite.setCompoundDrawables(img, null, null, null);
    }

    private void initView() {
        detailProgress = findViewById(R.id.detail_progress);

        tvTitle = findViewById(R.id.tv_title_label);
        tvStatus = findViewById(R.id.tv_status);
        tvRelaseDate = findViewById(R.id.tv_release_date);
        tvGenre = findViewById(R.id.tv_genre);
        tvRuntime = findViewById(R.id.tv_runtime);
        tvCast1 = findViewById(R.id.tv_cast1);
        tvCast2 = findViewById(R.id.tv_cast2);
        tvCast3 = findViewById(R.id.tv_cast3);
        tvCast4 = findViewById(R.id.tv_cast4);
        tvCast5 = findViewById(R.id.tv_cast5);
        tvScore = findViewById(R.id.tv_score);
        tvOverview = findViewById(R.id.tv_overview);
        tvLabelRelease = findViewById(R.id.tv_release_date_label);
        tvLabelRuntime = findViewById(R.id.tv_runtime_label);

        imgPoster = findViewById(R.id.img_cover);
        imgCast1 = findViewById(R.id.img_cast1);
        imgCast2 = findViewById(R.id.img_cast2);
        imgCast3 = findViewById(R.id.img_cast3);
        imgCast4 = findViewById(R.id.img_cast4);
        imgCast5 = findViewById(R.id.img_cast5);

        btnFavorite = findViewById(R.id.btn_favorite);
        btnRate = findViewById(R.id.btn_rate);

        showLoading(false);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Const.KEY_DETAIL_TYPE, detailType);
        outState.putInt(Const.KEY_ID, movieId);
        outState.putParcelable(PARCELABLE_DETAIL, localItemMovie);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("FAVORITE", localItemMovie);
                if (localItemMovie.getId() == null) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    setResult(Activity.RESULT_CANCELED, intent);
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getMovie() {

    }

    @Override
    public void getTv() {

    }

    @Override
    public void showData(DetailModel data) {
        tvTitle.setText(data.getTitle());
        tvStatus.setText(data.getStatus());
        tvRelaseDate.setText(data.getReleaseDate());
        tvGenre.setText(data.getGenre());

        tvCast1.setText(data.getCastName1());
        tvCast2.setText(data.getCastName2());
        tvCast3.setText(data.getCastName3());
        tvCast4.setText(data.getCastName4());
        tvCast5.setText(data.getCastName5());
        tvScore.setText(data.getVoteAverage());
        tvOverview.setText(data.getOverview());

        if (detailType == Const.DETAIL_MOVIE) {
            tvLabelRuntime.setText(getString(R.string.label_runtime_movie));
            tvLabelRelease.setText(getString(R.string.label_release_date));

            String text_hr = data.getHour_or_season() + " " + getResources().getString(R.string.jam);
            String text_mn = data.getMin_or_episode() + " " + getResources().getString(R.string.menit);
            tvRuntime.setText(String.format("%s %s", text_hr, text_mn));

        } else {
            tvLabelRuntime.setText(getString(R.string.label_runtime_tv));
            tvLabelRelease.setText(getString(R.string.label_airing_date));

            tvRuntime.setText(String.format("%s / %s", data.getHour_or_season(), data.getMin_or_episode()));
        }

        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getPosterPath())
                .fitCenter()
                .into(imgPoster);

        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getProfilePath1())
                .circleCrop()
                .into(imgCast1);
        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getProfilePath2())
                .circleCrop()
                .into(imgCast2);
        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getProfilePath3())
                .circleCrop()
                .into(imgCast3);
        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getProfilePath4())
                .circleCrop()
                .into(imgCast4);
        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getProfilePath5())
                .circleCrop()
                .into(imgCast5);
    }


    @Override
    public void showLoading(Boolean state) {
        if (state) {
            detailProgress.setVisibility(View.VISIBLE);
        } else {
            detailProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content)
                , msg
                , Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light));
        snack.show();
    }
}
