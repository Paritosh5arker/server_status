package com.example.server_status;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    public interface OnToggleListener {
        void onToggle(ServiceStatus service, boolean enable);
    }

    private List<ServiceStatus> services;
    private OnToggleListener listener;

    // ✅ FIX: Declare inside the class
    private final Map<String, Boolean> toggleLocked = new HashMap<>();

    public ServiceAdapter(List<ServiceStatus> services, OnToggleListener listener) {
        this.services = services;
        this.listener = listener;
    }

    public void updateData(List<ServiceStatus> newServices) {
        this.services = newServices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceStatus service = services.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());

        holder.toggle.setOnCheckedChangeListener(null);
        holder.toggle.setChecked(service.isRunning());

        boolean locked = toggleLocked.getOrDefault(service.getName(), false);
        holder.toggle.setEnabled(!locked);

        holder.toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (locked) return;

            // ✅ Only trigger toggle if state changed
            if (isChecked != service.isRunning()) {
                toggleLocked.put(service.getName(), true);
                notifyItemChanged(holder.getAdapterPosition());
                listener.onToggle(service, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public Map<String, Boolean> getToggleLockedMap() {
        return toggleLocked;
    }

    public List<ServiceStatus> getCurrentList() {
        return services;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        Switch toggle;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.serviceName);
            description = itemView.findViewById(R.id.serviceDesc);
            toggle = itemView.findViewById(R.id.serviceToggle);
        }
    }
}