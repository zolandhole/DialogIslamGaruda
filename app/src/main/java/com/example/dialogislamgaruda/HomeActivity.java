package com.example.dialogislamgaruda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

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

public class HomeActivity extends AppCompatActivity{
    private CardView buttonUmrah, buttonHaji;

    private ProgressDialog progressDialog;
    private RecyclerView recyclerViewIklan;
    private ArrayList<ModelIklan> modelIklanArrayList;
    private LinearLayoutManager linearLayoutManager;
    private TextView textViewinfokajian;

    private FloatingActionButton floatingActionButtonStreaming;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarStreaming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        collapsingToolbar();
        loadingPage();
        mengambilDataIklan();

        initializeMediaPlayer();

        floatingActionButtonStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });
    }

    private void startPlaying() {

        mediaPlayer.prepareAsync();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

                mediaPlayer.start();

            }
        });
    }

    private void stopPlaying() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            initializeMediaPlayer();
        }

    }

    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(EndPoint.STREAMING_RADIO);
        } catch (IllegalArgumentException  e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.e("YARUD", "onBufferingUpdate: "+ percent);
            }
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mediaPlayer != null){
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }

    private void loadingPage() {
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Mohon Menunggu...");
        progressDialog.setCanceledOnTouchOutside(false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.show();
    }

    private void initView() {
        buttonUmrah = findViewById(R.id.cardViewUmrah);
        buttonHaji = findViewById(R.id.cardViewHaji);
        recyclerViewIklan = findViewById(R.id.recyclerviewIklan);
        textViewinfokajian = findViewById(R.id.textViewinfokajian);

        floatingActionButtonStreaming = findViewById(R.id.fabStreaming);
        seekBarStreaming = findViewById(R.id.seekBarStreaming);
    }

    private void collapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.colapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Dialog Islam Garuda");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void mengambilDataIklan() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoint.GETIKLAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                                    model.setIklanId(dataobj.getString("id"));
                                    modelIklanArrayList.add(model);
                                }
                                setupRecycler();
                            } else if (jsonObject.optString("error").equals("true")){
                                String tidakadadata = jsonObject.getString("message");
                                Log.e("YARUD", tidakadadata);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            textViewinfokajian.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textViewinfokajian.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }});
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupRecycler() {
        recyclerViewIklan.setLayoutManager(linearLayoutManager);
        recyclerViewIklan.setHasFixedSize(true);
        AdapterIklan adapterIklan = new AdapterIklan(this, modelIklanArrayList);
        recyclerViewIklan.setAdapter(adapterIklan);
    }

    private void keUmrah() {
                Intent intent = new Intent(HomeActivity.this, MainUmrahActivity.class);
                startActivity(intent);
                finish();
    }

    private void keHaji(){
                Intent intent = new Intent(HomeActivity.this, MainHajiActivity.class);
                startActivity(intent);
                finish();
    }

    @Override
    protected void onResume() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        buttonUmrah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keUmrah();
            }
        });
        buttonHaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keHaji();
            }
        });

        super.onResume();
    }
}
