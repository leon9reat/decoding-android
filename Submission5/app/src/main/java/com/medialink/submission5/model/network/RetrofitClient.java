package com.medialink.submission5.model.network;

import com.medialink.submission5.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            HttpLoggingInterceptor logIntercept = new HttpLoggingInterceptor();
            logIntercept.level(HttpLoggingInterceptor.Level.BODY);

            Interceptor apiIntercept = new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request oriRequest = chain.request();
                    HttpUrl oriUrl = oriRequest.url();
                    HttpUrl url = oriUrl.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.ApiKey)
                            .build();

                    Request.Builder reqBuilder = oriRequest.newBuilder()
                            .url(url);
                    Request resultRequest = reqBuilder.build();
                    return chain.proceed(resultRequest);
                }
            };

            clientBuilder.addInterceptor(logIntercept)
                    .addInterceptor(apiIntercept);
            OkHttpClient client = clientBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
