package com.example.server_status;

import com.example.statusapp.model.StatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatusApiService {

    @GET("/api/status") // Adjust if your endpoint is different
    Call<StatusResponse> getStatus();
}