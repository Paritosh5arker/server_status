package com.example.server_status;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.statusapp.model.StatusResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView cpuInfo, memoryInfo, storageInfo, networkInfo, hostInfo;
    private ProgressBar loadingSpinner;
    private TextView errorText;

    private final Handler handler = new Handler();
    private final int refreshInterval = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuInfo = findViewById(R.id.cpu_info);
        memoryInfo = findViewById(R.id.memory_info);
        storageInfo = findViewById(R.id.storage_info);
        networkInfo = findViewById(R.id.network_info);
        hostInfo = findViewById(R.id.host_info);
        loadingSpinner = findViewById(R.id.loading_spinner);
        errorText = findViewById(R.id.error_text);

        fetchServerData(); // initial fetch
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(refreshRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(refreshRunnable);
    }

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            fetchServerData();
            handler.postDelayed(this, refreshInterval);
        }
    };

    private void fetchServerData() {
        loadingSpinner.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);

        StatusApiService apiService = RetrofitClient.getApiService();
        Call<StatusResponse> call = apiService.getStatus();

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                loadingSpinner.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    StatusResponse status = response.body();
                    errorText.setVisibility(View.GONE);

                    float cpuUsage = (float) status.cpu.utilisation;
                    long memAvailable = status.memory.available / 1024;
                    long memTotal = status.memory.total / 1024;

                    // ✅ Handle storage (sum across all drives)
                    long storageAvailable = 0;
                    long storageTotal = 0;
                    if (status.storage != null) {
                        for (Map.Entry<String, StatusResponse.StorageInfo> entry : status.storage.entrySet()) {
                            storageAvailable += (long) entry.getValue().available;
                            storageTotal += (long) entry.getValue().total;
                        }
                    }


                    // ✅ Handle CPU Temp
                    double temp = 0;
                    ArrayList<Double> tempListToSend = new ArrayList<>();
                    if (status.cpu.temperatures != null && status.cpu.temperatures.containsKey("temp1")) {
                        List<Double> rawTemps = status.cpu.temperatures.get("temp1");
                        if (rawTemps != null && !rawTemps.isEmpty()) {
                            temp = rawTemps.get(0);
                            tempListToSend.addAll(rawTemps);
                        }
                    }

                    // ✅ Set formatted display values
                    cpuInfo.setText(String.format("CPU Usage: %.2f%%, Temp: %.1f°C", cpuUsage, temp));
                    memoryInfo.setText(String.format("Memory: %d / %d MB", memAvailable, memTotal));
                    storageInfo.setText(String.format("Storage: %s / %s",
                            formatBytes(storageAvailable), formatBytes(storageTotal)));
                    networkInfo.setText(String.format("Network RX: %s | TX: %s",
                            formatBytes(status.network.rx), formatBytes(status.network.tx)));
                    hostInfo.setText(String.format("Host: %s | OS: %s", status.host.hostname, status.host.os));

                    // ✅ Click to view chart
                    cpuInfo.setOnClickListener(v -> {
                        Intent intent = new Intent(MainActivity.this, CPUDetailActivity.class);
                        intent.putExtra("temps", tempListToSend);
                        startActivity(intent);
                    });

                } else {
                    errorText.setVisibility(View.VISIBLE);
                    Log.e("API_ERROR", "Failed to get response: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                loadingSpinner.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
                Log.e("API_ERROR", "Network call failed: " + t.getMessage());
            }
        });
    }

    // ✅ Format bytes into human-readable format
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
