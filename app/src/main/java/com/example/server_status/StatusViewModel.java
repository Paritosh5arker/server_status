package com.example.server_status;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusViewModel extends ViewModel {

    private final MutableLiveData<StatusResponse> statusData = new MutableLiveData<>();
    private static final String TAG = "RetrofitDebug";
    private Context context;

    public StatusViewModel(Context context) {
        this.context = context;
    }

    public LiveData<StatusResponse> getStatusData() {
        return statusData;
    }

    public void fetchStatus() {
        RetrofitClient.getApiService(context).getStatus().enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    statusData.postValue(response.body());
                } else {
                    Toast.makeText(context, "Response Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(context, "Connection Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
