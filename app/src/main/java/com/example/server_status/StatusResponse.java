package com.example.statusapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StatusResponse {
    
    @SerializedName("cpu")
    public List<CpuInfo> cpu;

    @SerializedName("memory")
    public MemoryInfo memory;

    @SerializedName("storage")
    public List<StorageInfo> storage;

    @SerializedName("network")
    public NetworkInfo network;

    @SerializedName("host")
    public HostInfo host;

    public static class CpuInfo {
        public String core;
        public String temperature;
        public String frequency;
    }

    public static class MemoryInfo {
        public int total;
        public int available;
        public int cached;
        public int swapTotal;
        public int swapAvailable;
    }

    public static class StorageInfo {
        public String filesystem;
        public long total;
        public long used;
        public long free;
    }

    public static class NetworkInfo {
        public String interfaceName;
        public long rx;
        public long tx;
        public int speed;
    }

    public static class HostInfo {
        public String hostname;
        public String operatingSystem;
        public String uptime;
        public String appMemory;
        public String loadAverage;
    }
}
