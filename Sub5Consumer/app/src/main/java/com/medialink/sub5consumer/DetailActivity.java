package com.medialink.sub5consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.medialink.sub5consumer.contract.DetailContract;
import com.medialink.sub5consumer.model.local.FavoriteItem;
import com.medialink.sub5consumer.presenter.DetailPresenter;

public class DetailActivity extends AppCompatActivity
        implements DetailContract.DetailInterface{

    private static final String TAG = "DetailActivity";
    private static final String PARCELABLE_DETAIL = "PARCELABEL_DETAIL";
    private int detailType = 0;
    private int movieId = 0;

    private ProgressBar detailProgress;

    private TextView tvTitle, tvRelaseDate,
            tvScore, tvOverview,
            tvLabelRelease;
    private ImageView imgPoster;
    private Button btnFavorite, btnRate;

    private DetailContract.PresenterDetailInterface mPresenter;
    private FavoriteItem localItemMovie;
    private String uriId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        mPresenter = DetailPresenter.getInstance();
        mPresenter.setDetailView();

        // saved instance
        if (savedInstanceState == null) {
            Bundle args = getIntent().getExtras();
            if (args != null) {
                detailType = args.getInt(Const.KEY_DETAIL_TYPE, 0);
                movieId = args.getInt(Const.KEY_ID, 0);
                localItemMovie = args.getParcelable(Const.TABLE_FAVORITE);
            }
        } else {
            detailType = savedInstanceState.getInt(Const.KEY_DETAIL_TYPE);
            movieId = savedInstanceState.getInt(Const.KEY_ID);
            localItemMovie = savedInstanceState.getParcelable(PARCELABLE_DETAIL);
        }

        showData(localItemMovie);
        setFavoriteButtonColor(localItemMovie.getId() != null);

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

        // ubah warna favorite
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localItemMovie.getId() == null) {
                    ContentValues values = new ContentValues();
                    values.put(Const.FIELD_FAVORITE_MOVIE_ID, localItemMovie.getMovieId());
                    values.put(Const.FIELD_FAVORITE_TYPE_ID, localItemMovie.getTypeId());
                    values.put(Const.FIELD_FAVORITE_TITLE, localItemMovie.getTitle());
                    values.put(Const.FIELD_FAVORITE_RELEASE_DATE, localItemMovie.getReleaseDate());
                    values.put(Const.FIELD_FAVORITE_OVERVIEW, localItemMovie.getOverview());
                    values.put(Const.FIELD_FAVORITE_POSTER_PATH, localItemMovie.getPosterPath());

                    Uri uri = getContentResolver().insert(Const.URI_FAVORITE, values);

                    Log.d(TAG, "onClick: ID Inserted "+Integer.valueOf(uri.getLastPathSegment()));
                } else {
                    Uri uriWithId = Uri.parse(Const.URI_FAVORITE + "/" + localItemMovie.getId());
                    getContentResolver().delete(uriWithId, null, null);
                    localItemMovie.setId(null);

                    Log.d(TAG, "onClick: Record Deleted ");
                }
                setFavoriteButtonColor(localItemMovie.getId() != null);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Const.KEY_DETAIL_TYPE, detailType);
        outState.putInt(Const.KEY_ID, movieId);
        outState.putParcelable(PARCELABLE_DETAIL, localItemMovie);
    }

    private void initView() {
        detailProgress = findViewById(R.id.detail_progress);

        tvTitle = findViewById(R.id.tv_title_label);
        tvRelaseDate = findViewById(R.id.tv_release_date);
        tvScore = findViewById(R.id.tv_score);
        tvOverview = findViewById(R.id.tv_overview);
        tvLabelRelease = findViewById(R.id.tv_release_date_label);

        imgPoster = findViewById(R.id.img_cover);

        btnFavorite = findViewById(R.id.btn_favorite);
        btnRate = findViewById(R.id.btn_rate);

        showLoading(false);
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
    public void showData(FavoriteItem data) {
        tvTitle.setText(data.getTitle());
        tvRelaseDate.setText(data.getReleaseDate());

        tvScore.setText("0%");
        tvOverview.setText(data.getOverview());

        if (detailType == Const.DETAIL_MOVIE) {
            tvLabelRelease.setText(getString(R.string.label_release_date));
        } else {
            tvLabelRelease.setText(getString(R.string.label_airing_date));
        }

        Glide.with(getApplicationContext())
                .load(BuildConfig.ImageUrl + data.getPosterPath())
                .fitCenter()
                .into(imgPoster);
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
