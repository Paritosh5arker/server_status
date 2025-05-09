package com.example.server_status;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static StatusApiService getApiService(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            // Retrieve saved IP and API endpoint
            SharedPreferences prefs = context.getSharedPreferences("ServerPrefs", Context.MODE_PRIVATE);
            String baseUrl = prefs.getString("api_url", "http://192.168.1.147:9090");

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(StatusApiService.class);
    }
}
