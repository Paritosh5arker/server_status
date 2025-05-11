package com.example.statusapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

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
        public double utilisation;

        // 🔄 Update here to handle the array of temperatures
        @SerializedName("temperatures")
        public Map<String, List<Double>> temperatures;

        @SerializedName("frequencies")
        public Map<String, CpuFrequency> frequencies;

        public static class CpuFrequency {
            public int now;
            public int min;
            public Integer base;
            public int max;
        }
    }

    public static class MemoryInfo {
        @SerializedName("total")
        public long total;

        @SerializedName("available")
        public long available;

        @SerializedName("cached")
        public long cached;
    }

    public static class StorageInfo {
        // ✅ Updated: Now it maps "OS" as a full object
        @SerializedName("OS")
        public OperatingSystem os;

        @SerializedName("total")
        public long total;

        @SerializedName("available")
        public long available;

        public static class OperatingSystem {
            @SerializedName("name")
            public String name;

            @SerializedName("version")
            public String version;
        }
    }

    public static class NetworkInfo {
        @SerializedName("interface")
        public String interfaceName;

        @SerializedName("speed")
        public int speed;

        @SerializedName("rx")
        public long rx;

        @SerializedName("tx")
        public long tx;
    }

    public static class HostInfo {
        @SerializedName("hostname")
        public String hostname;

        @SerializedName("os")
        public String os;

        @SerializedName("uptime")
        public double uptime;
    }
}