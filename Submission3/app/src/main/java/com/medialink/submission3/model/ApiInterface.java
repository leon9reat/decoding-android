package com.medialink.submission3.model;

import android.os.Build;

import com.medialink.submission3.BuildConfig;
import com.medialink.submission3.model.movie.MovieCreditRespon;
import com.medialink.submission3.model.movie.MovieDetailRespon;
import com.medialink.submission3.model.movie.MovieRespon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String API_KEY = BuildConfig.ApiKey;
    String BASE_URL = BuildConfig.BaseUrl;

    @GET("movie/popular")
    Call<MovieRespon> getMovie(@Query("page") int page,
                               @Query("language") String language);

    // https://api.themoviedb.org/3/movie/384018?api_key=API_KEY&language=en-US
    @GET("movie/{id}")
    Call<MovieDetailRespon> getMovieDetail(@Path("id") int id,
                                           @Query("language") String language);

    //https://api.themoviedb.org/3/movie/65533/credits?api_key=65533e43224cd1a4e69fb25527b5735e
    @GET("movie/{id}/credits")
    Call<MovieCreditRespon> getMovieCredit(@Path("id") int id);

    /*
    //https://api.themoviedb.org/3/tv/popular?api_key=65533e43224cd1a4e69fb25527b5735e&language=en-US&page=1
    @GET("tv/popular?api_key=" + API_KEY)
    Call<TvRespon> getTvShow(@Query("page") int page,
                             @Query("language") String language);

    //https://api.themoviedb.org/3/tv/{tv_id}/credits?api_key=<<api_key>>&language=en-US
    @GET("tv/{id}/credits?api_key=" + API_KEY)
    Call<CreditRespon> getTvCredit(@Path("id") int id);

    //https://api.themoviedb.org/3/tv/{tv_id}?api_key=<<api_key>>&language=en-US
    @GET("tv/{id}?api_key=" + API_KEY)
    Call<TvDetail> getTvDetail(@Path("id") int id,
                               @Query("language") String language);

     */
}
