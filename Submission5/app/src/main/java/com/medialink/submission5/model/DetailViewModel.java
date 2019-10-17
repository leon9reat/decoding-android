package com.medialink.submission5.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.submission5.Const;
import com.medialink.submission5.contract.DetailContract;
import com.medialink.submission5.model.movie.CreditMovieRespon;
import com.medialink.submission5.model.movie.MovieDetailRespon;
import com.medialink.submission5.model.network.ApiInterface;
import com.medialink.submission5.model.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailViewModel extends ViewModel {

    private static final String TAG = "DetailViewModel";

    private MutableLiveData<DetailModel> detailModel;
    private DetailContract.DetailInterface mDetailView;

    public LiveData<DetailModel> getDetailData(int typeId,
                                               int movieId,
                                               final DetailContract.DetailInterface detailInterface) {
        if (detailModel == null) {
            this.mDetailView = detailInterface;
            detailModel = new MutableLiveData<>();
            if (typeId == Const.DETAIL_MOVIE) {
                loadMovie(movieId);
            } else {
                loadTv(movieId);
            }
        }

        return detailModel;
    }

    private void loadMovie(int movieId) {

        final DetailModel data = new DetailModel();
        mDetailView.showLoading(true);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieDetailRespon> callDetail = api.getMovieDetail(movieId, lang);
        callDetail.enqueue(new Callback<MovieDetailRespon>() {
            @Override
            public void onResponse(Call<MovieDetailRespon> call, Response<MovieDetailRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setTitle(response.body().getTitle());
                        data.setReleaseDate(response.body().getReleaseDate());
                        //data.setLabelReleaseDate();
                        data.setOverview(response.body().getOverview());
                        data.setVoteAverage(String.valueOf(response.body().getVoteAverage()));
                        data.setPosterPath(response.body().getPosterPath());
                        //data.setLabelRuntime();

                        int min = response.body().getRuntime();
                        int hr = min / 60;
                        int mn = min % 60;
                        data.setRuntime(min);
                        data.setHour_or_season(hr);
                        data.setMin_or_episode(mn);

                        data.setStatus(response.body().getStatus());

                        if (response.body().getGenres() != null) {
                            String genre = "";
                            for (int i = 0; i < response.body().getGenres().size(); i++) {
                                if (genre.isEmpty()) {
                                    genre = response.body().getGenres().get(i).getName();
                                } else {
                                    genre = genre + ", " + response.body().getGenres().get(i).getName();
                                }
                            }
                            data.setGenre(genre);

                        }


                        detailModel.setValue(data);
                        mDetailView.showLoading(false);
                    }
                } else {
                    mDetailView.showLoading(false);
                    mDetailView.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailRespon> call, Throwable t) {
                mDetailView.showLoading(false);
                mDetailView.setError("Fatal Error: " + t.getMessage());
            }
        });

        Call<CreditMovieRespon> callCredit = api.getMovieCredit(movieId);
        callCredit.enqueue(new Callback<CreditMovieRespon>() {
            @Override
            public void onResponse(Call<CreditMovieRespon> call, Response<CreditMovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<CastItem> list = (ArrayList<CastItem>) response.body().getCast();
                            if (list.size() > 0 && list.get(0) != null) {
                                data.setCastName1(list.get(0).getName());
                                data.setProfilePath1(list.get(0).getProfilePath());
                            }
                            if (list.size() > 1 && list.get(1) != null) {
                                data.setCastName2(list.get(1).getName());
                                data.setProfilePath2(list.get(1).getProfilePath());
                            }
                            if (list.size() > 2 && list.get(2) != null) {
                                data.setCastName3(list.get(2).getName());
                                data.setProfilePath3(list.get(2).getProfilePath());

                            }
                            if (list.size() > 3 && list.get(3) != null) {
                                data.setCastName4(list.get(3).getName());
                                data.setProfilePath4(list.get(3).getProfilePath());

                            }
                            if (list.size() > 4 && list.get(4) != null) {
                                data.setCastName5(list.get(4).getName());
                                data.setProfilePath5(list.get(4).getProfilePath());
                            }

                        detailModel.setValue(data);
                        mDetailView.showLoading(false);
                    }
                } else {
                    mDetailView.showLoading(false);
                    mDetailView.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CreditMovieRespon> call, Throwable t) {
                mDetailView.showLoading(false);
                mDetailView.setError("Fatal Error: " + t.getMessage());
            }
        });
    }

    private void loadTv(int tvId) {

    }


}
