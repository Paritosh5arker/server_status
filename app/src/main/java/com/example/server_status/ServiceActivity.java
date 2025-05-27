package com.example.server_status;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;


public class ServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private StatusApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        recyclerView = findViewById(R.id.serviceRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ServiceAdapter(new ArrayList<>(), this::toggleService);
        recyclerView.setAdapter(adapter);

        apiService = RetrofitClient.getApiService();

        loadServices();
    }

    private void loadServices() {
        apiService.getServices().enqueue(new Callback<List<ServiceStatus>>() {
            @Override
            public void onResponse(Call<List<ServiceStatus>> call, Response<List<ServiceStatus>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ServiceStatus>> call, Throwable t) {
                Toast.makeText(ServiceActivity.this, "Failed to load services", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleService(ServiceStatus service, boolean enable) {
        String action = enable ? "start" : "stop";

        apiService.toggleService(service.getName(), new ToggleRequest(action)).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                adapter.getToggleLockedMap().put(service.getName(), false);
                int position = getPositionOf(service);
                if (position != -1) adapter.notifyItemChanged(position);

                Map<String, String> body = response.body();
                if (body != null && body.containsKey("status")) {
                    Toast.makeText(ServiceActivity.this, body.get("status"), Toast.LENGTH_SHORT).show(); // ✅ THIS LINE
                } else if (body != null && body.containsKey("error")) {
                    Toast.makeText(ServiceActivity.this, "Error: " + body.get("error"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ServiceActivity.this, "Unexpected response", Toast.LENGTH_SHORT).show();
                }

                // Optionally refresh
                // loadServices();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                adapter.getToggleLockedMap().put(service.getName(), false);
                int position = getPositionOf(service);
                if (position != -1) adapter.notifyItemChanged(position);

                Toast.makeText(ServiceActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private int getPositionOf(ServiceStatus service) {
        List<ServiceStatus> list = adapter.getCurrentList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(service.getName())) {
                return i;
            }
        }
        return -1;
    }
}