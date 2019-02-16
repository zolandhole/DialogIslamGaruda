package com.example.dialogislamgaruda.haji;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.dialogislamgaruda.HomeActivity;
import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.adapter.AdapterHaji;
import com.example.dialogislamgaruda.model.ModelHaji;

import java.util.ArrayList;

public class MainHajiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_haji);
        toolBar();

        ArrayList<ModelHaji> list = new ArrayList<>();
//        list.add(new ModelHaji(ModelHaji.TEXT_TYPE,"PANDUAN HAJI",0));
        list.add(new ModelHaji(ModelHaji.IMAGE_TYPE,"Bersiap siap sebelum berihram","IHRAM",R.drawable.umrah1));
        list.add(new ModelHaji(ModelHaji.AUDIO_TYPE,"Memakai Pakaian Ihram","IHRAM", R.raw.lagu));
        list.add(new ModelHaji(ModelHaji.VIDEO_TYPE,"Berihram / Berniat dari miqat","IHRAM", R.raw.umrah));
        list.add(new ModelHaji(ModelHaji.IMAGE_TYPE,"Hal hal yang dilarang ketika berihram","IHRAM",R.drawable.haji));
        list.add(new ModelHaji(ModelHaji.AUDIO_TYPE,"Hal hal yang diperbolehkan ketika berihram","IHRAM",R.raw.srigala));

        AdapterHaji adapter = new AdapterHaji(list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewHaji);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    private void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Panduan Haji");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("YDIG");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKeMenuUtama();
            }
        });
    }

    @Override
    public void onBackPressed() {
        kembaliKeMenuUtama();
        super.onBackPressed();
    }

    private void kembaliKeMenuUtama() {
        Intent intent = new Intent(MainHajiActivity.this, HomeActivity.class);
        intent.putExtra("DARIHALAMAN","haji");
        startActivity(intent);
        finish();
    }
}
