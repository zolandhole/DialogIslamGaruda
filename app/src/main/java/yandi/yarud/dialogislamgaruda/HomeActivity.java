package yandi.yarud.dialogislamgaruda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Objects;

import yandi.yarud.dialogislamgaruda.umrah.MainUmrahActivity;

public class HomeActivity extends AppCompatActivity {
    private CardView buttonUmrah, buttonHaji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonUmrah = findViewById(R.id.cardViewUmrah);
        buttonHaji = findViewById(R.id.cardViewHaji);

    }

    private void animation() {
        buttonUmrah.setTranslationX(-4000);
        buttonHaji.setTranslationX(-4000);
        buttonUmrah.animate().translationX(0).setDuration(800).setStartDelay(100).start();
        buttonHaji.animate().translationX(0).setDuration(800).setStartDelay(300).start();
    }

    private void backAnimation() {
        buttonHaji.setTranslationX(3000);
        buttonUmrah.setTranslationX(3000);
        buttonUmrah.animate().translationX(0).setDuration(800).start();
        buttonHaji.animate().translationX(0).setDuration(800).setStartDelay(100).start();
    }

    private void goAnimation(){
        buttonHaji.animate().translationX(3000).setDuration(800).start();
        buttonUmrah.animate().translationX(3000).setDuration(800).setStartDelay(200).start();
    }

    private void keUmrah() {
        goAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, MainUmrahActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        String dariHalaman = Objects.requireNonNull(getIntent().getExtras()).getString("DARIHALAMAN");
        assert dariHalaman != null;
        if (dariHalaman.equals("umrah")){
            backAnimation();
        }else if (dariHalaman.equals("splash")){
            animation();
        }
        buttonUmrah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoom = AnimationUtils.loadAnimation(HomeActivity.this,R.anim.zoom);
                buttonUmrah.startAnimation(zoom);
                keUmrah();
            }
        });
        super.onResume();
    }
}
