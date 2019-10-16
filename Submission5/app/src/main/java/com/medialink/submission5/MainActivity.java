package com.medialink.submission5;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medialink.submission5.alarm.AlarmReceiver;
import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.MovieViewModel;
import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.notification.MovieNotif;
import com.medialink.submission5.notification.NotifItem;
import com.medialink.submission5.preference.PreferenceActivity;
import com.medialink.submission5.preference.PreferenceHelper;
import com.medialink.submission5.presenter.MainPresenter;
import com.medialink.submission5.view.adapter.MainPagerAdapter;

import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity
        implements MainContract.MainInterface {

    private static final String TAG = "MainActivity";
    private static final String KEY_SEARCH_TEXT = "SEARCH_TEXT";
    private String mSearchText;

    private SearchManager searchManager;
    private SearchView searchView;
    private TabLayout tabMain;
    private ViewPager pagerMain;
    private MainContract.PresenterInterface mPresenter;
    private AlarmReceiver alarmReceiver;
    private MovieNotif mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = MainPresenter.getInstance();
        mPresenter.setMainView(this);

        mNotification = MovieNotif.getInstance(this);

        initView();
        initAlarm();

        if (savedInstanceState != null) {
            mSearchText = savedInstanceState.getString(KEY_SEARCH_TEXT);
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras().containsKey("EXTRA")) {
            if (intent.getIntExtra("EXTRA", 0) == Const.NOTIFICATION_REQUEST_CODE) {
                mNotification.clearNotif();
                Log.d(TAG, "onNewIntent: intent dari notification");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCH_TEXT, mSearchText);
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
                tabMain.getTabCount(),
                mPresenter);
        pagerMain.setAdapter(adapter);
        pagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
    }

    private void initAlarm() {
        alarmReceiver = new AlarmReceiver();
        if (PreferenceHelper.getInstance(this).isDailyReminder()) {
            alarmReceiver.setRepeatingAlarm(this, Const.REQUEST_DAILY_REMINDER);
            Log.d(TAG, "onCreate: set daily reminder");
        } else {
            alarmReceiver.cancelAlarm(this, Const.REQUEST_DAILY_REMINDER);
        }
        if (PreferenceHelper.getInstance(this).isRelesedReminder()) {
            alarmReceiver.setRepeatingAlarm(this, Const.REQUEST_RELEASE_REMINDER);
            Log.d(TAG, "onCreate: set relese reminder");
        } else {
            alarmReceiver.cancelAlarm(this, Const.REQUEST_RELEASE_REMINDER);
        }

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
                    mSearchText = query;

                    int activeTab = tabMain.getSelectedTabPosition();
                    if (activeTab == 0) {
                        if (query.isEmpty()) {
                            mPresenter.getMovie();
                        } else {
                            mPresenter.getMovieFilter(mSearchText);
                        }

                    } else {
                        if (query.isEmpty()) {
                            mPresenter.getTv(1);
                        } else {
                            mPresenter.getTvFilter(mSearchText);
                        }

                    }
                    Log.d(TAG, "Search Text : " + mSearchText);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty() && !searchView.isIconified()) {
                        int activeTab = tabMain.getSelectedTabPosition();

                        if (activeTab == 0) {
                            mPresenter.getMovie();
                        } else {
                            mPresenter.getTv(1);
                        }
                        mSearchText = "";
                        Log.d(TAG, "Reset Searching Text : " + mSearchText);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSearchText != null) {
                        searchView.setQuery(mSearchText, false);
                    }
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_notif_setting:
                showSettings();
                break;
            case R.id.menu_favorite:
                showFavorite();
                break;
            case R.id.menu_change_language:
                changeLanguage();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSettings() {
        Intent settingIntent = new Intent(this, PreferenceActivity.class);
        startActivity(settingIntent);
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

    @Override
    public void showFavorite() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeLanguage() {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivityForResult(intent, Const.CHANGE_LANGUAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Const.CHANGE_LANGUAGE_REQUEST) {
            // setelah ganti bahasa, refresh lagi datanya
            //((MovieFragment) getSupportFragmentManager().getFragments().get(0)).refreshMovie();
            //((TvFragment)    getSupportFragmentManager().getFragments().get(1)).refreshTv();

            Log.d(TAG, "onActivityResult: Ubah Setting Bahasa");
        }

    }
}
