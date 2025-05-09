// package com.example.statusapp;

// import androidx.appcompat.app.AppCompatActivity;
// import androidx.lifecycle.ViewModelProvider;
// import android.os.Bundle;
// import android.widget.TextView;

// import com.example.statusapp.model.StatusResponse;
// import com.example.statusapp.viewmodel.StatusViewModel;

// public class MainActivity extends AppCompatActivity {

//     private StatusViewModel statusViewModel;
//     private TextView cpuInfo, memoryInfo, storageInfo, networkInfo, hostInfo;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_main);

//         cpuInfo = findViewById(R.id.cpu_info);
//         memoryInfo = findViewById(R.id.memory_info);
//         storageInfo = findViewById(R.id.storage_info);
//         networkInfo = findViewById(R.id.network_info);
//         hostInfo = findViewById(R.id.host_info);

//         statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        
//         statusViewModel.getStatusData().observe(this, this::updateUI);
//         statusViewModel.fetchStatus();
//     }

//     private void updateUI(StatusResponse status) {
//         cpuInfo.setText("CPU Cores: " + status.cpu.size());
//         memoryInfo.setText("Memory Available: " + status.memory.available);
//         storageInfo.setText("Storage Used: " + status.storage.get(0).used + " bytes");
//         networkInfo.setText("Network RX: " + status.network.rx + " bytes");
//         hostInfo.setText("Hostname: " + status.host.hostname);
//     }
// }
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
