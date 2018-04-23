package to.masn.wings.testui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {
    ;//getWindow().getDecorView();


    float adjustX = 150.0f;
    float adjustY = 150.0f;

    boolean flg = false;

    View containerView;
    View childView;
    Animation inAnimation;
    Animation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View flickView = getWindow().getDecorView();
        containerView = findViewById(R.id.container);
        childView = findViewById(R.id.childview);
        inAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.in_animation);
        outAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.out_animation);

    }

}