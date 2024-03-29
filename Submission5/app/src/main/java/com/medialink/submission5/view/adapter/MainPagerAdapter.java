package com.medialink.submission5.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.view.main.MovieFragment;
import com.medialink.submission5.view.main.TvFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private MainContract.PresenterInterface mPresenter;

    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior, int numOfTabs,
                            MainContract.PresenterInterface presenter) {
        super(fm, behavior);
        this.numOfTabs = numOfTabs;
        this.mPresenter = presenter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TvFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
