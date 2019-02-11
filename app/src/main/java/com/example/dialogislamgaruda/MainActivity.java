package com.example.dialogislamgaruda;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private int mLoading = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.imageViewLogoSplash);
        final ProgressBar progressBar = findViewById(R.id.progressBarSplash);

        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        imageView.setAnimation(fromTop);

        Drawable drawable = getResources().getDrawable(R.drawable.customprogressbar);
        progressBar.setProgressDrawable(drawable);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mLoading < 100){
                    mLoading++;
                    android.os.SystemClock.sleep(50);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mLoading);
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.animate().alpha(0.0f).setDuration(1000).translationY(imageView.getHeight()).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                String dariHalaman = "splash";
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("DARIHALAMAN", dariHalaman);
                                startActivity(intent);
                                finish();
                            }
                        });
                        progressBar.animate().alpha(0.0f).setDuration(800).translationY(imageView.getHeight());
                    }
                });
            }
        }).start();
    }
}
