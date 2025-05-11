public class MainActivity extends AppCompatActivity {

    private TextView cpuModel, cpuTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cpuModel = findViewById(R.id.cpu_model);
        cpuTemp = findViewById(R.id.cpu_temp);

        fetchServerData();
    }

    private void fetchServerData() {
        StatusApiService apiService = RetrofitClient.getApiService();
        Call<StatusResponse> call = apiService.getStatus();

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StatusResponse status = response.body();

                    // Update UI
                    cpuModel.setText(String.format("%s", status.cpu.model));
                    cpuTemp.setText(String.format("%.2f°C", status.cpu.temperatures.get("temp1").get(0)));

                    // Update Memory, Storage, Network, Host similarly
                } else {
                    Log.e("API_ERROR", "Failed to get response: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("API_ERROR", "Network call failed: " + t.getMessage());
            }
        });
    }
}