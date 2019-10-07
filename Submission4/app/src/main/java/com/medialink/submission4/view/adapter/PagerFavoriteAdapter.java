package com.medialink.submission4.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.medialink.submission4.view.MovieFavoriteFragment;
import com.medialink.submission4.view.TvFavoriteFragment;

public class PagerFavoriteAdapter extends FragmentStatePagerAdapter {

    private int numOfTab;

    public PagerFavoriteAdapter(@NonNull FragmentManager fm, int behavior, int numOfTab) {
        super(fm, behavior);
        this.numOfTab = numOfTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MovieFavoriteFragment();
            case 1:
                return new TvFavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
