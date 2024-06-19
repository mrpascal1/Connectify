package com.shahid.connectify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class main_animation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_animation);

        // Get the ImageViews
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);

        // Load the animations
        Animation riseUpAnimation1 = AnimationUtils.loadAnimation(this, R.anim.rise_up);
        Animation riseUpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.rise_up);
        Animation riseUpAnimation3 = AnimationUtils.loadAnimation(this, R.anim.rise_up);

        // Initially hide the views
        imageView3.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);

        // Set the animation listeners to chain animations
        riseUpAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView3.setVisibility(View.VISIBLE);
                imageView3.startAnimation(riseUpAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        riseUpAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView4.setVisibility(View.VISIBLE);
                imageView4.startAnimation(riseUpAnimation3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        riseUpAnimation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(main_animation.this, RegistrationActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        // Start the first animation
        imageView2.startAnimation(riseUpAnimation1);
    }
}
