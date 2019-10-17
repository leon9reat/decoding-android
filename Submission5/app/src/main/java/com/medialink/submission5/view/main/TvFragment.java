package com.medialink.submission5.view.main;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.MovieViewModel;
import com.medialink.submission5.model.tv.TvResult;
import com.medialink.submission5.presenter.MainPresenter;
import com.medialink.submission5.view.adapter.TvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment
        implements MainContract.TvInterface {

    private static final String TAG = "TvFragment";

    private CoordinatorLayout coordinatorTv;
    private ProgressBar tvProgress;
    private RecyclerView tvRecycler;

    private TvAdapter mAdapter;
    private MainContract.MainInterface mMainView;
    private MainContract.PresenterInterface mPresenter;
    private ArrayList<TvResult> mTvList = new ArrayList<>();
    private MovieViewModel model;

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

        model = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        model.getTvList(this).observe(TvFragment.this, new Observer<List<TvResult>>() {
            @Override
            public void onChanged(List<TvResult> tvResults) {
                showTv((ArrayList<TvResult>) tvResults);
            }
        });

        if (getContext() != null) {
            tvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            tvRecycler.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            tvRecycler.setAdapter(mAdapter);
        }

        return view;
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
        coordinatorTv = view.findViewById(R.id.coordinator_tv);
        tvProgress = view.findViewById(R.id.progress_tv);
        tvRecycler = view.findViewById(R.id.rv_main_tv);

        showLoading(false);
    }

    @Override
    public void getTv() {
        model.setTv();
    }

    @Override
    public void getTvFilter(String s) {
        model.setTvFilter(s);
    }

    @Override
    public void getTvRelease(String drTgl, String spTgl) {
        model.setTvRelease(drTgl, spTgl);
    }

    @Override
    public void showTv(ArrayList<TvResult> list) {
        mTvList.clear();
        mTvList.addAll(list);

        mAdapter.setData(mTvList);
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

    @Override
    public void setError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar snack = Snackbar.make(coordinatorTv, msg, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTv();                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light));
        snack.show();
    }
}
