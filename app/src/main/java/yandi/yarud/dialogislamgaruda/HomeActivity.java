package yandi.yarud.dialogislamgaruda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    private ImageView imageViewUmrah, imageViewHaji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageViewUmrah = findViewById(R.id.imageViewPanduanUmrah);
        imageViewHaji = findViewById(R.id.imageViewPanduanHaji);

        animaation();
    }

    private void animaation() {
//        imageViewUmrah.setAlpha(0);
        imageViewUmrah.setTranslationX(-1000);
//        imageViewHaji.setAlpha(0);
        imageViewHaji.setTranslationX(-1000);

        imageViewUmrah.animate().translationX(10).setDuration(800).setStartDelay(500).translationX(0).start();
        imageViewHaji.animate().translationX(10).setDuration(800).setStartDelay(700).translationX(0).start();
    }
}
