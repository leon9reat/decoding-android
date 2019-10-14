package com.medialink.submission5.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.medialink.submission5.view.favorite.MovieFavFragment;
import com.medialink.submission5.view.favorite.TvFavFragment;

public class FavoritePagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public FavoritePagerAdapter(@NonNull FragmentManager fm, int behavior, int numOfTabs) {
        super(fm, behavior);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFavFragment();
                break;
            case 1:
                fragment = new TvFavFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
