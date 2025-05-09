package com.example.server_status;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView cpuInfo, memoryInfo, storageInfo, networkInfo, hostInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuInfo = findViewById(R.id.cpu_info);
        memoryInfo = findViewById(R.id.memory_info);
        storageInfo = findViewById(R.id.storage_info);
        networkInfo = findViewById(R.id.network_info);
        hostInfo = findViewById(R.id.host_info);
    }
}
