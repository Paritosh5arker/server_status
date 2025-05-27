package com.example.server_status;

public class ToggleRequest {
    private String action;

    public ToggleRequest(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}