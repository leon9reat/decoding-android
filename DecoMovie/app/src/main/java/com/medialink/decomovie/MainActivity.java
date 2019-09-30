package com.medialink.decomovie;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medialink.decomovie.fragment.MovieFragment;
import com.medialink.decomovie.fragment.TvFragment;

public class MainActivity extends AppCompatActivity{
    public final static String URL_IMAGE = "https://image.tmdb.org/t/p/w185_and_h278_bestv2";
    public static final String TYPE_LIST = "TYPE_LIST";
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("The Movie DB");
        }

        tabLayout = findViewById(R.id.tab_layout);
        String movie = getResources().getString(R.string.tab_movie);
        String tv = getResources().getString(R.string.tab_tv);
        tabLayout.addTab(tabLayout.newTab().setText(movie).setIcon(R.drawable.ic_movie_black));
        tabLayout.addTab(tabLayout.newTab().setText(tv).setIcon(R.drawable.ic_tv_black));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new com.medialink.decomovie.adapter.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            ((MovieFragment) getSupportFragmentManager().getFragments().get(0)).addDataMovie();
            ((TvFragment)    getSupportFragmentManager().getFragments().get(1)).addDataTv();
        }
    }

}
