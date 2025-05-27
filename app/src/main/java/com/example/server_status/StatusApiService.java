package com.example.server_status;

import com.example.statusapp.model.StatusResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Path;



public interface StatusApiService {

    @GET("/api/status") // Adjust if your endpoint is different
    Call<StatusResponse> getStatus();

    @GET("/api/services")
    Call<List<ServiceStatus>> getServices();

    @POST("/api/service/{name}")
    Call<Map<String, String>> toggleService(@Path("name") String name, @Body ToggleRequest action);

}