package com.medialink.submission4.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.medialink.submission4.view.MovieFragment;
import com.medialink.submission4.view.TvFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTab;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int numOfTab) {
        super(fm, behavior);
        this.numOfTab = numOfTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MovieFragment();
            case 1:
                return new TvFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
