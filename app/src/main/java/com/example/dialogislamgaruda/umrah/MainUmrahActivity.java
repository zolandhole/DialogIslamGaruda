package com.example.dialogislamgaruda.umrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dialogislamgaruda.HomeActivity;
import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.adapter.AdapterUmrah;
import com.example.dialogislamgaruda.model.ModelUmrah;
import java.util.ArrayList;

public class MainUmrahActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterUmrah adapterUmrah;
    private ArrayList<ModelUmrah> modelUmrah;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_umrah);
        toolBar();

        recyclerView = findViewById(R.id.recyclerviewUmrah);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelUmrah = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        String judul = "Bersiap siap sebelum berihram";
        String title = "IHRAM";
        int gambar = R.drawable.umrah1;
        modelUmrah.add(new ModelUmrah(judul, title, gambar));

        String judul2 = "Memakai Pakaian Ihram";
        String title2 = "IHRAM";
        int gambar2 = R.drawable.haji;
        modelUmrah.add(new ModelUmrah(judul2, title2, gambar2));

        adapterUmrah = new AdapterUmrah(this, modelUmrah);
        recyclerView.setAdapter(adapterUmrah);
    }

    private void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Panduan Umrah");
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

    private void kembaliKeMenuUtama(){
        Intent intent = new Intent(MainUmrahActivity.this, HomeActivity.class);
        String dariHalaman = "umrah";
        intent.putExtra("DARIHALAMAN", dariHalaman);
        startActivity(intent);
        finish();
    }
}
