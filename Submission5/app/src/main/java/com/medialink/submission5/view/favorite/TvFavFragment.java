package com.medialink.submission5.view.favorite;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medialink.submission5.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavFragment extends Fragment {


    public TvFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_fav, container, false);
    }

}