package com.medialink.submission5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.AppDatabase;
import com.medialink.submission5.model.local.FavoriteDao;
import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.view.adapter.FavoritePagerAdapter;

import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class FavoriteActivity extends AppCompatActivity
    implements FavoriteContract.FavoriteInterface {

    private static final String TAG = "FavoriteActivity";

    private TabLayout tabFavorite;
    private ViewPager pagerFavorite;

    private AppDatabase database;
    private FavoriteDao favDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initView();

        database = Room.databaseBuilder(this, AppDatabase.class, "movie_db")
                .allowMainThreadQueries()
                .build();
        favDao = database.getFavoriteDao();
        List<FavoriteItem> items = favDao.getAllFavorite();
        if (items.size() < 1) {
            for (int i = 0; i < 5; i++) {
                FavoriteItem item = new FavoriteItem();
                item.setTypeId(Const.DETAIL_MOVIE);
                item.setMovieId(i);
                item.setTitle("Judul Movie "+i);
                item.setReleaseDate("0000-00-0"+i);
                item.setOverview(getString(R.string.example_overview));

                favDao.insert(item);

                FavoriteItem item2 = new FavoriteItem();
                item2.setTypeId(Const.DETAIL_TV);
                item2.setMovieId(i);
                item2.setTitle("Judul Film "+i);
                item2.setReleaseDate("0000-00-0"+i);
                item2.setOverview(getString(R.string.example_overview));

                favDao.insert(item2);
            }
        }
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
        startActivity(intent);
    }
}
