package com.medialink.submission3;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medialink.submission3.view.MovieFragment;
import com.medialink.submission3.view.adapter.PagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.pager_main)
    ViewPager pagerMain;
    @BindView(R.id.tab_main)
    TabLayout tabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_title));
        }

        // tab layout
        TabLayout.Tab tabMovie = tabMain.newTab()
                .setText(getResources().getString(R.string.tab_title_movie))
                .setIcon(R.drawable.ic_movie_black_24dp);
        TabLayout.Tab tabTv = tabMain.newTab()
                .setText(getResources().getString(R.string.tab_title_tv))
                .setIcon(R.drawable.ic_tv_black_24dp);
        tabMain.addTab(tabMovie);
        tabMain.addTab(tabTv);
        tabMain.setTabGravity(TabLayout.GRAVITY_FILL);
        tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // pager
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager()
                , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                , tabMain.getTabCount());
        pagerMain.setAdapter(adapter);
        pagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_change_locale:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivityForResult(intent, 101);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            // setelah ganti bahasa, refresh lagi datanya
            Log.d(TAG, "onActivityResult: Ubah Setting Bahasa");
            ((MovieFragment) getSupportFragmentManager().getFragments().get(0)).refreshMovie();
            //((TvFragment)    getSupportFragmentManager().getFragments().get(1)).addDataTv();
        }
    }
}
