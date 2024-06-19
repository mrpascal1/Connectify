package com.shahid.connectify;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.shahid.connectify.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView registerButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_registeration_page);

        // Initialize views
        registerButton = findViewById(R.id.register_button);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        usernameEditText = findViewById(R.id.username);

        // Set up the register button click listener
        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                postEmailPass(String.valueOf(usernameEditText),email, password);
            } else {
                Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postEmailPass(String username,String email, String pass) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Username",username);
        map.put("EmailID", email);
        map.put("PassWord", pass);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.push().setValue(map);
    }
}
