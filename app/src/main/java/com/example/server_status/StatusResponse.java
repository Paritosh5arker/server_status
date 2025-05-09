package com.example.server_status;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StatusResponse {

    @SerializedName("cpu")
    public CpuInfo cpu;

    @SerializedName("memory")
    public MemoryInfo memory;

    @SerializedName("storage")
    public StorageInfo storage;

    @SerializedName("network")
    public NetworkInfo network;

    @SerializedName("host")
    public HostInfo host;

    public static class CpuInfo {
        @SerializedName("model")
        public String model;

        @SerializedName("utilisation")
        public float utilisation;

        @SerializedName("temperatures")
        public List<Float> temperatures;
    }

    public static class MemoryInfo {
        @SerializedName("total")
        public long total;

        @SerializedName("available")
        public long available;

        @SerializedName("cached")
        public long cached;

        @SerializedName("swap_total")
        public long swapTotal;

        @SerializedName("swap_available")
        public long swapAvailable;
    }

    public static class StorageInfo {
        @SerializedName("total")
        public long total;

        @SerializedName("available")
        public long available;

        @SerializedName("used")
        public long used;
    }

    public static class NetworkInfo {
        @SerializedName("interface")
        public String interfaceName;

        @SerializedName("rx")
        public long rx;

        @SerializedName("tx")
        public long tx;

        @SerializedName("speed")
        public int speed;
    }

    public static class HostInfo {
        @SerializedName("hostname")
        public String hostname;

        @SerializedName("os")
        public String os;

        @SerializedName("uptime")
        public long uptime;

        @SerializedName("app_memory")
        public long appMemory;

        @SerializedName("loadavg")
        public List<Float> loadavg;
    }
}