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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.model.ModelIklan;
import com.example.dialogislamgaruda.utils.EndPoint;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterIklan extends RecyclerView.Adapter<AdapterIklan.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<ModelIklan> modelIklanArrayList;
    private Context context;
    private Dialog iklanDialog;
    private TextView textViewCaption;
    private Button btnLike;
    private ImageView imageViewIklan, imageViewClose;
    private String iklan_id, pesan;


    public AdapterIklan(Context ctx, ArrayList<ModelIklan> modelIklanArrayList) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.modelIklanArrayList = modelIklanArrayList;
        this.context = ctx;
    }

    @NonNull
    @Override
    public AdapterIklan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.list_iklan, parent, false);
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
                btnLike = iklanDialog.findViewById(R.id.btnLike);
                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iklanDialog.dismiss();
                    }
                });
                ModelIklan model = modelIklanArrayList.get(test);
                Picasso.get().load(model.getImgURL()).into(imageViewIklan);
                textViewCaption.setText(model.getCaption());
                iklan_id = model.getIklanId();

                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kirimData();
                        Toast.makeText(context, iklan_id, Toast.LENGTH_SHORT).show();
                    }
                });

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(iklanDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                iklanDialog.show();
            }
        });
    }

    private void kirimData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint.POSTLIKE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("error").equals("false")){
                                pesan = jsonObject.getString("message");
                            } else if (jsonObject.optString("error").equals("true")){
                                pesan = jsonObject.getString("message");
                            }
                            Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("posisi", "POLISI");
                params.put("status_suka", "1");
                params.put("iklan_id", iklan_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
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
