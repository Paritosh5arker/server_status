package com.example.server_status;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView cpuInfo, memoryInfo, storageInfo, networkInfo, hostInfo;
    private StatusViewModel statusViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuInfo = findViewById(R.id.cpu_info);
        memoryInfo = findViewById(R.id.memory_info);
        storageInfo = findViewById(R.id.storage_info);
        networkInfo = findViewById(R.id.network_info);
        hostInfo = findViewById(R.id.host_info);

        statusViewModel = new StatusViewModel();
        statusViewModel.getStatusData().observe(this, new Observer<StatusResponse>() {
            @Override
            public void onChanged(StatusResponse status) {
                if (status != null) {
                    float temp = status.cpu.temperatures.get("temp1").get(0);
                    cpuInfo.setText("CPU Utilization: " + status.cpu.utilisation + "%, Temp: " + temp + "°C");

                    memoryInfo.setText("Memory: " + status.memory.available / 1024 + " MB free of " + status.memory.total / 1024 + " MB");

                    // Displaying only "OS" storage info for now
                    if (status.storage.containsKey("OS")) {
                        StatusResponse.Storage osStorage = status.storage.get("OS");
                        storageInfo.setText("Storage: " + osStorage.available / (1024 * 1024 * 1024) + " GB free of " + osStorage.total / (1024 * 1024 * 1024) + " GB");
                    }

                    networkInfo.setText("Network: RX=" + status.network.rx + " bytes, TX=" + status.network.tx + " bytes");

                    hostInfo.setText("Host: " + status.host.hostname + " (" + status.host.os + ")");
                }
            }
        });

        statusViewModel.fetchStatus();
    }
}
