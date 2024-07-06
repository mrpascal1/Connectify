package com.shahid.connectify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.shahid.connectify.databinding.AnimationPageBinding;

public class AnimationPage extends AppCompatActivity {

    private static final String TAG = "AnimationPage";
    private FirebaseAuth auth;
    private AnimationPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AnimationPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        // Load the animations
        Animation riseUpAnimation1 = AnimationUtils.loadAnimation(this, R.anim.rise_up);
        Animation riseUpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.rise_up);
        Animation riseUpAnimation3 = AnimationUtils.loadAnimation(this, R.anim.rise_up);

        // Initially hide the views
        binding.imageView3.setVisibility(View.GONE);
        binding.imageView4.setVisibility(View.GONE);

        // Set the animation listeners to chain animations
        riseUpAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.imageView3.setVisibility(View.VISIBLE);
                binding.imageView3.startAnimation(riseUpAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        riseUpAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.imageView4.setVisibility(View.VISIBLE);
                binding.imageView4.startAnimation(riseUpAnimation3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        riseUpAnimation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent;
                if (isLoggedIn()) {
                    intent = new Intent(AnimationPage.this, MainActivity.class);
                } else {
                    intent = new Intent(AnimationPage.this, SignInActivity.class);
                }
                startActivity(intent);
                finish(); // Close the current activity
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        // Start the first animation
        binding.imageView2.startAnimation(riseUpAnimation1);
    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }
}
