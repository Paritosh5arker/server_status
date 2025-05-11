package com.example.server_status;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.statusapp.model.StatusResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CPUDetailActivity extends AppCompatActivity {

    private LineChart lineChart;
    private final Handler handler = new Handler();
    private final int refreshInterval = 5000; // 5 seconds
    private final ArrayList<Entry> entries = new ArrayList<>();
    private int updateCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_detail);

        lineChart = findViewById(R.id.line_chart);
        setupChart();
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
            fetchLatestTemperature();
            handler.postDelayed(this, refreshInterval);
        }
    };

    private void setupChart() {
        Description description = new Description();
        description.setText("Live CPU Temperature");
        lineChart.setDescription(description);
        lineChart.setData(new LineData());
    }

    private void fetchLatestTemperature() {
        StatusApiService apiService = RetrofitClient.getApiService();
        Call<StatusResponse> call = apiService.getStatus();

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StatusResponse status = response.body();

                    if (status.cpu.temperatures != null && status.cpu.temperatures.containsKey("temp1")) {
                        List<Double> tempList = status.cpu.temperatures.get("temp1");
                        if (tempList != null && !tempList.isEmpty()) {
                            float latestTemp = tempList.get(0).floatValue();
                            updateChart(latestTemp);
                        }
                    }
                } else {
                    Log.e("CPUChart", "API error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("CPUChart", "Network failure: " + t.getMessage());
            }
        });
    }

    private void updateChart(float newTemp) {
        entries.add(new Entry(updateCount++, newTemp));

        LineDataSet dataSet = new LineDataSet(entries, "CPU Temp (°C)");
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(true);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh chart
    }
}