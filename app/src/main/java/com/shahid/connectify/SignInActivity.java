package com.shahid.connectify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private TextView NewUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        NewUserTextView = findViewById(R.id.sign_in_note);

        NewUserTextView.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
