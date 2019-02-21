package com.example.dialogislamgaruda.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.model.ModelIklan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterIklan extends RecyclerView.Adapter<AdapterIklan.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<ModelIklan> modelIklanArrayList;
    private Context context;
    private Dialog iklanDialog;
    private TextView textViewCaption;
    private Button buttonShare;
    private ImageView imageViewIklan, imageViewClose;
//    private int test=0;

    public AdapterIklan(Context ctx, ArrayList<ModelIklan> modelIklanArrayList) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.modelIklanArrayList = modelIklanArrayList;
        this.context = ctx;
    }

    @NonNull
    @Override
    public AdapterIklan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =layoutInflater.inflate(R.layout.list_iklan, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIklan.MyViewHolder holder, @SuppressLint("RecyclerView") final int test) {
        Picasso.get().load(modelIklanArrayList.get(test).getImgURL()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iklanDialog = new Dialog(context);
                iklanDialog.setContentView(R.layout.popup_iklan);
                imageViewClose = iklanDialog.findViewById(R.id.close_iklan);
                textViewCaption = iklanDialog.findViewById(R.id.tvCaption);
                imageViewIklan = iklanDialog.findViewById(R.id.ivIklan);
                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iklanDialog.dismiss();
                    }
                });
                ModelIklan model = modelIklanArrayList.get(test);
                Picasso.get().load(model.getImgURL()).into(imageViewIklan);
                textViewCaption.setText(model.getCaption());
//
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(iklanDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                iklanDialog.show();
            }
        });
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
