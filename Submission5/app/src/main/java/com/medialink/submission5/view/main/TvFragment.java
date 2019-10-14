package com.medialink.submission5.view.main;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.model.tv.TvResult;
import com.medialink.submission5.presenter.MainPresenter;
import com.medialink.submission5.view.adapter.TvAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment
 implements MainContract.TvInterface {

    private static final String TAG = "TvFragment";
    private static final String KEY_LIST_TV = "LIST_TV";
    private static final String KEY_PAGE = "PAGE";
    private int mPage = 1;

    private ProgressBar tvProgress;
    private RecyclerView tvRecycler;

    private TvAdapter mAdapter;
    private MainContract.MainInterface mMainView;
    private MainContract.PresenterInterface mPresenter;
    private ArrayList<TvResult> mTvList = new ArrayList<>();

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        initView(view);

        mPresenter = MainPresenter.getInstance();
        mPresenter.setTvView(this);

        mAdapter = new TvAdapter(getContext(), this);

        if (getContext() != null) {
            tvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            tvRecycler.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            tvRecycler.setAdapter(mAdapter);
        }

        if (savedInstanceState == null) {
            // loading pertama
            mPresenter.getTv(mPage);
            Log.d(TAG, "onCreateView: tv pertama");
        } else {
            // loading berikut
            ArrayList<TvResult> list = savedInstanceState.getParcelableArrayList(KEY_LIST_TV);
            mPage = savedInstanceState.getInt(KEY_PAGE);

            showTv(list);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LIST_TV, mTvList);
        outState.putInt(KEY_PAGE, mPage);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainContract.MainInterface) {
            mMainView = (MainContract.MainInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainView = null;
    }

    private void initView(View view) {
        tvProgress = view.findViewById(R.id.progress_tv);
        tvRecycler = view.findViewById(R.id.rv_main_tv);

        showLoading(false);
    }

    @Override
    public void showTv(ArrayList<TvResult> list) {
        mTvList.clear();
        mTvList.addAll(list);

        mAdapter.setData(mTvList);
        showLoading(false);
    }

    @Override
    public void listClick(TvResult tv, int position) {
        mMainView.showDetail(Const.DETAIL_TV, tv.getId());
    }

    @Override
    public void showLoading(Boolean state) {
        if (state) {
            tvProgress.setVisibility(View.VISIBLE);
        } else {
            tvProgress.setVisibility(View.GONE);
        }
    }
}
