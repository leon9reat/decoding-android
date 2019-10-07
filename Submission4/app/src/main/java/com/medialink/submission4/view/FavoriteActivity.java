package com.medialink.submission4.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medialink.submission4.R;
import com.medialink.submission4.view.adapter.PagerFavoriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.tab_favorite)
    TabLayout tabFavorite;
    @BindView(R.id.pager_favorite)
    ViewPager pagerFavorite;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        unbinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.favorite_title));
        }

        // tab layout
        TabLayout.Tab tabMovie = tabFavorite.newTab()
                .setText(getResources().getString(R.string.tab_title_movie))
                .setIcon(R.drawable.ic_movie_black_24dp);
        TabLayout.Tab tabTv = tabFavorite.newTab()
                .setText(getResources().getString(R.string.tab_title_tv))
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
        PagerFavoriteAdapter adapter = new PagerFavoriteAdapter(getSupportFragmentManager()
                , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                , tabFavorite.getTabCount());
        pagerFavorite.setAdapter(adapter);
        pagerFavorite.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabFavorite));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
