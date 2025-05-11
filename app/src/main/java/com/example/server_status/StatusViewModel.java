package com.example.server_status;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.statusapp.model.StatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusViewModel extends ViewModel {

    private final MutableLiveData<StatusResponse> statusData = new MutableLiveData<>();

    public LiveData<StatusResponse> getStatusData() {
        return statusData;
    }

    public void loadStatus() {
        RetrofitClient.getApiService().getStatus().enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    statusData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                statusData.setValue(null);
            }
        });
    }
}