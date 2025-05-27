package com.example.server_status;

public class ServiceStatus {
    private String name;
    private String description;
    private String status;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public boolean isRunning() {
        return "running".equalsIgnoreCase(status);
    }
}