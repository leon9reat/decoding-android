package com.medialink.submission5;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medialink.submission5.preference.PreferenceHelper;
import com.medialink.submission5.preference.PreferenceActivity;
import com.medialink.submission5.view.adapter.MainPagerAdapter;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SearchManager searchManager;
    private SearchView searchView;
    private TabLayout tabMain;
    private ViewPager pagerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        PreferenceHelper pm = new PreferenceHelper(this);
        if (pm.isRelesedReminder()) {
            Toast.makeText(this, "released on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "released off", Toast.LENGTH_SHORT).show();
        } 

    }

    private void initView() {
        tabMain = findViewById(R.id.tab_main);
        pagerMain = findViewById(R.id.pager_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Submission 5");
        }
        TabLayout.Tab tabMovie = tabMain.newTab()
                .setText(getString(R.string.label_movie))
                .setIcon(R.drawable.ic_movie_black_24dp);
        TabLayout.Tab tabTv = tabMain.newTab()
                .setText(getString(R.string.label_tv))
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
        PagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(),
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                tabMain.getTabCount());
        pagerMain.setAdapter(adapter);
        pagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);

        searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            searchView = (SearchView) menu.findItem(R.id.menu_main_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    int activeTab = tabMain.getSelectedTabPosition();
                    if (activeTab == 0) {
                        Log.d(TAG, "cari movie: " + query);
                    } else {
                        Log.d(TAG, "cari tv: " + query);
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notif_setting:
                Intent settingIntent = new Intent(this, PreferenceActivity.class);
                startActivity(settingIntent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
