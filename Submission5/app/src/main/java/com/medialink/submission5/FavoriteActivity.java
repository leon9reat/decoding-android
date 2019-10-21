package com.medialink.submission5;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.presenter.FavoritePresenter;
import com.medialink.submission5.presenter.MainPresenter;
import com.medialink.submission5.view.adapter.FavoritePagerAdapter;
import com.medialink.submission5.widget.MovieWidget;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class FavoriteActivity extends AppCompatActivity
        implements FavoriteContract.FavoriteInterface {

    private static final String TAG = "FavoriteActivity";
    private static final int REQUEST_FROM_FAVORITE = 3;

    private TabLayout tabFavorite;
    private ViewPager pagerFavorite;
    private FavoriteContract.PresenterFavInterface mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mPresenter = FavoritePresenter.getInstance();
        mPresenter.setFavView(this);

        initView();
    }

    private void initView() {
        tabFavorite = findViewById(R.id.tab_favorite);
        pagerFavorite = findViewById(R.id.pager_favorite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.favorite_caption));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TabLayout.Tab tabMovie = tabFavorite.newTab()
                .setText(getString(R.string.label_movie))
                .setIcon(R.drawable.ic_movie_black_24dp);
        TabLayout.Tab tabTv = tabFavorite.newTab()
                .setText(getString(R.string.label_tv))
                .setIcon(R.drawable.ic_tv_black_24dp);
        tabFavorite.addTab(tabMovie);
        tabFavorite.addTab(tabTv);
        tabFavorite.setTabGravity(TabLayout.GRAVITY_FILL);
        tabFavorite.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerFavorite.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // pager
        PagerAdapter adapter = new FavoritePagerAdapter(getSupportFragmentManager(),
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                tabFavorite.getTabCount());
        pagerFavorite.setAdapter(adapter);
        pagerFavorite.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabFavorite));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDetail(int detailType, int id) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle args = new Bundle();
        args.putInt(Const.KEY_DETAIL_TYPE, detailType);
        args.putInt(Const.KEY_ID, id);
        intent.putExtras(args);
        startActivityForResult(intent, REQUEST_FROM_FAVORITE);
    }

    @Override
    public void refreshWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplication(), MovieWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FROM_FAVORITE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    FavoriteItem item = data.getParcelableExtra("FAVORITE");
                    if (item.getTypeId() == Const.DETAIL_MOVIE) {
                        mPresenter.getMovie();
                    } else {
                        mPresenter.getTv();
                    }

                    Log.d(TAG, "onActivityResult: return item " + item.getTitle());
                }
            }
        }
    }
}
