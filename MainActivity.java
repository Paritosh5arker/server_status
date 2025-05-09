package com.example.statusmonitor;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.statusmonitor.network.ApiClient;
import com.example.statusmonitor.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusView = findViewById(R.id.textView);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<String> call = apiService.getStatus();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    statusView.setText(response.body());
                } else {
                    statusView.setText("Failed to fetch status");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                statusView.setText("Error: " + t.getMessage());
            }
        });
    }
}
