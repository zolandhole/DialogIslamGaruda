package com.example.dialogislamgaruda;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dialogislamgaruda.adapter.AdapterIklan;
import com.example.dialogislamgaruda.haji.MainHajiActivity;
import com.example.dialogislamgaruda.model.ModelIklan;
import com.example.dialogislamgaruda.umrah.MainUmrahActivity;
import com.example.dialogislamgaruda.utils.EndPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    private CardView buttonUmrah, buttonHaji;
    private ConstraintLayout constraintHajiUmrah;

    private ProgressDialog progressDialog;
    private RecyclerView recyclerViewIklan;
    private ArrayList<ModelIklan> modelIklanArrayList;
    private TextView textViewNoInternet;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonUmrah = findViewById(R.id.cardViewUmrah);
        buttonHaji = findViewById(R.id.cardViewHaji);
        constraintHajiUmrah = findViewById(R.id.constraintHajiUmrah);
        textViewNoInternet = findViewById(R.id.textViewNoInternet);
        recyclerViewIklan = findViewById(R.id.recyclerviewIklan);
        swipeRefresh = findViewById(R.id.swiperefresh);

        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Mohon Menunggu...");
        progressDialog.setCanceledOnTouchOutside(false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mengambilDataIklan();
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewIklan);

        progressDialog.show();

//        recyclerViewIklan.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                View view = snapHelper.findSnapView(linearLayoutManager);
//                assert view != null;
//                int pos = linearLayoutManager.getPosition(view);
//                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
//                assert viewHolder != null;
//                FrameLayout frameLayout = viewHolder.itemView.findViewById(R.id.framPhoto);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    frameLayout.animate().alpha(1).scaleX(1).scaleY(1).setDuration(50).start();
//                } else {
//                    frameLayout.animate().alpha(0.5f).scaleX(0.5f).scaleY(0.5f).setDuration(50).start();
//                }
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mengambilDataIklan();
            }
        });
    }

    private void mengambilDataIklan() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoint.GETIKLAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textViewNoInternet.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("error").equals("false")){
                                modelIklanArrayList = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("message");
                                for (int i=0; i < jsonArray.length(); i++){
                                    ModelIklan model = new ModelIklan();
                                    JSONObject dataobj =jsonArray.getJSONObject(i);
                                    model.setCaption(dataobj.getString("caption"));
                                    model.setTampilkansampai(dataobj.getString("tampilkansampai"));
                                    model.setStatusphoto(dataobj.getString("statusphoto"));
                                    model.setImgURL(dataobj.getString("photo"));
                                    modelIklanArrayList.add(model);
                                }
                                setupRecycler();
                                swipeRefresh.setRefreshing(false);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        swipeRefresh.setRefreshing(false);
                        Log.e("YARUD", error.getMessage());
                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        textViewNoInternet.setVisibility(View.VISIBLE);
                    }});
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupRecycler() {
        recyclerViewIklan.setLayoutManager(linearLayoutManager);
        recyclerViewIklan.setHasFixedSize(true);
        AdapterIklan adapterIklan = new AdapterIklan(this, modelIklanArrayList);
        recyclerViewIklan.setAdapter(adapterIklan);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                RecyclerView.ViewHolder viewHolderDefault = recyclerViewIklan.findViewHolderForAdapterPosition(0);
//                assert viewHolderDefault != null;
//                FrameLayout frameLayoutDefault = viewHolderDefault.itemView.findViewById(R.id.framPhoto);
//                frameLayoutDefault.animate().alpha(1).scaleX(1).scaleY(1).setDuration(150).start();
//            }
//        },100);
    }

    private void animation() {
        buttonUmrah.setTranslationX(-3000);
        buttonHaji.setTranslationX(-3000);
        buttonUmrah.animate().translationX(0).setDuration(800).setStartDelay(100).start();
        buttonHaji.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        constraintHajiUmrah.startAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fade_in));
    }

    private void backAnimationUmrah() {
        buttonHaji.setTranslationX(3000);
        buttonUmrah.setTranslationX(3000);
        buttonUmrah.animate().translationX(0).setDuration(800).start();
        buttonHaji.animate().translationX(0).setDuration(800).setStartDelay(100).start();
        constraintHajiUmrah.startAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fade_in));
    }

    private void backAnimationHaji(){
        buttonUmrah.setTranslationX(3000);
        buttonHaji.setTranslationX(3000);
        buttonHaji.animate().translationX(0).setDuration(800).start();
        buttonUmrah.animate().translationX(0).setDuration(800).setStartDelay(100).start();
        constraintHajiUmrah.startAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fade_in));
    }

    private void goAnimationUmrah(){
        buttonHaji.animate().translationX(3000).setDuration(800).start();
        buttonUmrah.animate().translationX(3000).setDuration(800).setStartDelay(200).start();
    }

    private void goAnimationHaji(){
        buttonUmrah.animate().translationX(3000).setDuration(800).start();
        buttonHaji.animate().translationX(3000).setDuration(800).setStartDelay(200).start();
    }

    private void keUmrah() {
        goAnimationUmrah();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, MainUmrahActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_act, R.anim.fadeout_act);
                finish();
            }
        }, 1000);
    }

    private void keHaji(){
        goAnimationHaji();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, MainHajiActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_act, R.anim.fadeout_act);
                finish();
            }
        },1000);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        String dariHalaman = Objects.requireNonNull(getIntent().getExtras()).getString("DARIHALAMAN");
        assert dariHalaman != null;
        switch (dariHalaman) {
            case "umrah":
                backAnimationUmrah();
                break;
            case "haji":
                backAnimationHaji();
                break;
            case "splash":
                animation();
                break;
        }

        buttonUmrah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoom = AnimationUtils.loadAnimation(HomeActivity.this,R.anim.zoom);
                buttonUmrah.startAnimation(zoom);
                keUmrah();
            }
        });
        buttonHaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoom = AnimationUtils.loadAnimation(HomeActivity.this,R.anim.zoom);
                buttonHaji.startAnimation(zoom);
                keHaji();
            }
        });
        super.onResume();
    }

}
