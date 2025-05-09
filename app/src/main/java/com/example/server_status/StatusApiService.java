package com.example.statusapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.statusapp.model.StatusResponse;

public interface StatusApiService {

    @GET("/api/status")
    Call<StatusResponse> getStatus();
}
