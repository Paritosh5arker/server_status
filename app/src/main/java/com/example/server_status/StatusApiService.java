package com.example.server_status;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatusApiService {
    @GET("/api/status")
    Call<StatusResponse> getStatus();
}