package com.example.dialogislamgaruda.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.model.ModelIklan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterIklan extends RecyclerView.Adapter<AdapterIklan.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<ModelIklan> modelIklanArrayList;

    public AdapterIklan(Context ctx, ArrayList<ModelIklan> modelIklanArrayList) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.modelIklanArrayList = modelIklanArrayList;
    }

    @NonNull
    @Override
    public AdapterIklan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =layoutInflater.inflate(R.layout.list_iklan, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIklan.MyViewHolder holder, int i) {
        Picasso.get().load(modelIklanArrayList.get(i).getImgURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelIklanArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        FrameLayout framePhoto;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPhoto);
            framePhoto = itemView.findViewById(R.id.framPhoto);
        }
    }
}
