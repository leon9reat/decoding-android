package com.medialink.submission4.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medialink.submission4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavoriteFragment extends Fragment {


    public TvFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_favorite, container, false);
    }

}
