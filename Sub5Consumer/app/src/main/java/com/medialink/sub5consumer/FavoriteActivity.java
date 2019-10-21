package com.medialink.sub5consumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.medialink.sub5consumer.contract.FavoriteContract;
import com.medialink.sub5consumer.presenter.FavoritePresenter;
import com.medialink.sub5consumer.view.adapter.FavoritePagerAdapter;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class FavoriteActivity extends AppCompatActivity
        implements FavoriteContract.FavoriteInterface{

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
    public void showDetail(int detailType, int id) {

    }
}
