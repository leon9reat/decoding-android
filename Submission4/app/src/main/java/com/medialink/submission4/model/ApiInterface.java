package com.medialink.submission4.model;

import com.medialink.submission4.model.movie.MovieCreditRespon;
import com.medialink.submission4.model.movie.MovieDetailRespon;
import com.medialink.submission4.model.movie.MovieRespon;
import com.medialink.submission4.model.tv.TvCreditRespon;
import com.medialink.submission4.model.tv.TvDetailRespon;
import com.medialink.submission4.model.tv.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/popular")
    Call<MovieRespon> getMovie(@Query("page") int page,
                               @Query("language") String language);

    // https://api.themoviedb.org/3/movie/384018?api_key=<API_KEY>&language=en-US
    @GET("movie/{id}")
    Call<MovieDetailRespon> getMovieDetail(@Path("id") int id,
                                           @Query("language") String language);

    //https://api.themoviedb.org/3/movie/65533/credits?api_key=<API_KEY>
    @GET("movie/{id}/credits")
    Call<MovieCreditRespon> getMovieCredit(@Path("id") int id);

    //https://api.themoviedb.org/3/tv/popular?api_key=<API_KEY>&language=en-US&page=1
    @GET("tv/popular")
    Call<TvResponse> getTvShow(@Query("page") int page,
                               @Query("language") String language);

    //https://api.themoviedb.org/3/tv/{tv_id}?api_key=<API_KEY>&language=en-US
    @GET("tv/{id}")
    Call<TvDetailRespon> getTvDetail(@Path("id") int id,
                                     @Query("language") String language);

    //https://api.themoviedb.org/3/tv/{tv_id}/credits?api_key=<<api_key>>&language=en-US
    @GET("tv/{id}/credits")
    Call<TvCreditRespon> getTvCredit(@Path("id") int id);

}
