package com.example.dialogislamgaruda.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.model.ModelUmrah;

import java.util.ArrayList;

public class AdapterUmrah extends RecyclerView.Adapter<AdapterUmrah.UmrahViewHolder> {
    private Context mContext;
    private ArrayList<ModelUmrah> modelUmrah;

    public AdapterUmrah(Context mContext, ArrayList<ModelUmrah> modelUmrah) {
        this.mContext = mContext;
        this.modelUmrah = modelUmrah;
    }

    @NonNull
    @Override
    public AdapterUmrah.UmrahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_umrah, parent, false);
        return new AdapterUmrah.UmrahViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUmrah.UmrahViewHolder holder, int position) {
        ModelUmrah model = modelUmrah.get(position);
        holder.textViewJudul.setText(model.getJudul());
        holder.textViewTitle.setText(model.getTitle());
        holder.imageView.setImageResource(model.getGambar());
    }

    @Override
    public int getItemCount() {
        return modelUmrah.size();
    }

    class UmrahViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewJudul, textViewTitle;

        UmrahViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageUmrah);
            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
