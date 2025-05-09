package com.example.server_status;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView cpuInfo, memoryInfo, storageInfo, networkInfo, hostInfo;
    private Button retryButton, setIpButton;
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
        retryButton = findViewById(R.id.retry_button);
        setIpButton = findViewById(R.id.set_ip_button);

        statusViewModel = new StatusViewModel(this);

        statusViewModel.getStatusData().observe(this, new Observer<StatusResponse>() {
            @Override
            public void onChanged(StatusResponse status) {
                if (status != null) {
                    cpuInfo.setText("CPU Utilization: " + status.cpu.utilisation + "%");
                    memoryInfo.setText("Memory: " + status.memory.available / 1024 + " MB free of " + status.memory.total / 1024 + " MB");
                    networkInfo.setText("Network: RX=" + status.network.rx + " bytes, TX=" + status.network.tx + " bytes");
                    hostInfo.setText("Host: " + status.host.hostname + " (" + status.host.os + ")");
                }
            }
        });

        retryButton.setOnClickListener(v -> statusViewModel.fetchStatus());
        
        setIpButton.setOnClickListener(v -> showIpDialog());
    }

    private void showIpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set API Endpoint");

        final EditText input = new EditText(this);
        input.setHint("http://192.168.1.147:9090");
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String apiUrl = input.getText().toString();
                SharedPreferences prefs = getSharedPreferences("ServerPrefs", Context.MODE_PRIVATE);
                prefs.edit().putString("api_url", apiUrl).apply();
                Toast.makeText(MainActivity.this, "API URL Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
