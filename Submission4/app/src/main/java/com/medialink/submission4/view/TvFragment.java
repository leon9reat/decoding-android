package com.medialink.submission4.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.medialink.submission4.Const;
import com.medialink.submission4.R;
import com.medialink.submission4.TvContract;
import com.medialink.submission4.model.MainViewModel;
import com.medialink.submission4.model.tv.TvItem;
import com.medialink.submission4.presenter.TvPresenter;
import com.medialink.submission4.view.adapter.TvAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment implements TvContract.ViewInterface {

    private final static String TAG = TvFragment.class.getSimpleName();

    @BindView(R.id.tv_progress)
    ProgressBar tvProgress;
    @BindView(R.id.rv_tv)
    RecyclerView rvTv;
    @BindView(R.id.tv_layout)

    CoordinatorLayout tvLayout;
    private Unbinder unbinder;
    private TvAdapter mAdapter;
    private MainViewModel mModel;
    private TvContract.PresenterInterface mPresenter;
    private int mPage = 1;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new TvPresenter(getActivity(), this);

        if (getActivity() != null ) {
            mModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            mModel.getTv().observe(TvFragment.this, getTv);

            showLoading(false);
            if (mModel.getTv().getValue() == null) {
                refreshTv();
            }
        }

        mAdapter = new TvAdapter(getContext(), this);
        mAdapter.notifyDataSetChanged();

        if (getContext() != null) {
            rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTv.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
            rvTv.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private Observer<ArrayList<TvItem>> getTv = new Observer<ArrayList<TvItem>>() {
        @Override
        public void onChanged(ArrayList<TvItem> tvItems) {
            if (tvItems != null) {
                mAdapter.setData(tvItems);
            }
        }
    };

    @Override
    public void itemClick(TvItem tv, int position) {
        Intent i = new Intent(getContext(), DetailActivity.class);
        i.putExtra(Const.KEY_ID, tv.getId());
        i.putExtra(Const.KEY_TYPE, Const.INTENT_TV);
        startActivity(i);
        Log.d(TAG, "itemFavoriteClick: " + tv.getName());
    }

    @Override
    public void setError(String msg) {
        showMessage("Error: " + msg);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar snack = Snackbar.make(tvLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshTv();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.red));
        snack.show();
    }

    @Override
    public void showLoading(Boolean state) {
        if (state) {
            tvProgress.setVisibility(View.VISIBLE);
        } else {
            tvProgress.setVisibility(View.GONE);
        }
    }

    public void refreshTv() {
        mPage = 1;
        mPresenter.getTv(mPage);
    }
}
