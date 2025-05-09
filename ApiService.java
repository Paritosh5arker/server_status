package com.example.statusmonitor.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/status")
    Call<String> getStatus();
}
