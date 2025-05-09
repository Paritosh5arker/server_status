package com.example.server_status;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class StatusResponse {

    @SerializedName("cpu")
    public CPU cpu;

    @SerializedName("memory")
    public Memory memory;

    @SerializedName("storage")
    public Map<String, Storage> storage;

    @SerializedName("network")
    public Network network;

    @SerializedName("host")
    public Host host;

    public static class CPU {
        @SerializedName("utilisation")
        public float utilisation;

        @SerializedName("temperatures")
        public Map<String, List<Float>> temperatures;

        @SerializedName("frequencies")
        public Map<String, Frequency> frequencies;
    }

    public static class Frequency {
        @SerializedName("now")
        public int now;

        @SerializedName("min")
        public int min;

        @SerializedName("max")
        public int max;
    }

    public static class Memory {
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

    public static class Storage {
        @SerializedName("total")
        public long total;

        @SerializedName("available")
        public long available;
    }

    public static class Network {
        @SerializedName("interface")
        public String interfaceName;

        @SerializedName("speed")
        public int speed;

        @SerializedName("rx")
        public long rx;

        @SerializedName("tx")
        public long tx;
    }

    public static class Host {
        @SerializedName("hostname")
        public String hostname;

        @SerializedName("os")
        public String os;

        @SerializedName("uptime")
        public double uptime;

        @SerializedName("app_memory")
        public long appMemory;

        @SerializedName("loadavg")
        public List<Float> loadavg;
    }
}
