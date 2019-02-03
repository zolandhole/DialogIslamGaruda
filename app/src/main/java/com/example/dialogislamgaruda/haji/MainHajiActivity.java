package com.example.dialogislamgaruda.haji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dialogislamgaruda.HomeActivity;
import com.example.dialogislamgaruda.R;

public class MainHajiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_haji);
        Toast.makeText(this, "ini halaman Haji", Toast.LENGTH_SHORT).show();
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
