package yandi.yarud.dialogislamgaruda.umrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import yandi.yarud.dialogislamgaruda.HomeActivity;
import yandi.yarud.dialogislamgaruda.R;

public class MainUmrahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_umrah);
        toolBar();
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
