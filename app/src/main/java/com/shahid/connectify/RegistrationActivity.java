package com.shahid.connectify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shahid.connectify.databinding.ActivityRegisterationPageBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegisterationPageBinding binding;
    private TextView registerButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private EditText reenterPasswordEditText;
    private TextView signInShift;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            /*
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

             */
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterationPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // Initialize views using View Binding
        registerButton = binding.registerButton;
        emailEditText = binding.email;
        passwordEditText = binding.password;
        usernameEditText = binding.username;
        reenterPasswordEditText = binding.reenterPassword;
        signInShift = binding.loginLink;

        // Set up the register button click listener
        registerButton.setOnClickListener(view -> {
            if (validateInputs()) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                createFirebaseUser(username, email, password);
            }
        });

        // Set up the sign in link click listener
        signInShift.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String reenterPassword = reenterPasswordEditText.getText().toString().trim();

        return validateUsername(username) && validateEmail(email) && validatePassword(password) && validateReenterPassword(password, reenterPassword);
    }

    private boolean validateUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username is required");
            return false;
        }
        if (username.length() < 3 || username.length() > 20) {
            usernameEditText.setError("Username must be between 3 and 20 characters");
            return false;
        }
        if (!Pattern.matches("[a-zA-Z0-9._]+", username) || username.startsWith(".") || username.endsWith(".") || username.endsWith("_")) {
            usernameEditText.setError("Username can only contain alphanumeric characters, underscores, and periods, and cannot start or end with special characters");
            return false;
        }
        if (username.contains(" ")) {
            usernameEditText.setError("Username cannot contain spaces");
            return false;
        }
        // Implement actual checks for reserved words and existing usernames in your database
        return true;
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return false;
        }
        // Implement actual check for existing email in your database
        return true;
    }

    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (password.length() < 8 || password.length() > 64) {
            passwordEditText.setError("Password must be between 8 and 64 characters");
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            passwordEditText.setError("Password must contain at least one uppercase letter");
            return false;
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            passwordEditText.setError("Password must contain at least one lowercase letter");
            return false;
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passwordEditText.setError("Password must contain at least one digit");
            return false;
        }
        if (!Pattern.compile("[!@#$%^&*]").matcher(password).find()) {
            passwordEditText.setError("Password must contain at least one special character");
            return false;
        }
        return true;
    }

    private boolean validateReenterPassword(String password, String reenterPassword) {
        if (!password.equals(reenterPassword)) {
            reenterPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void createFirebaseUser(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                saveUserToDatabase(username, email);
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToDatabase(String username, String email) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        String uniqueKey = databaseReference.push().getKey();
        String userId = mAuth.getCurrentUser().getUid();
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("Username", username);
        userMap.put("EmailID", email);

        databaseReference.child(userId).setValue(userMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Clear the input fields
                    emailEditText.setText("");
                    passwordEditText.setText("");
                    usernameEditText.setText("");
                    reenterPasswordEditText.setText("");

                    /*
                    // Navigate to MainActivity or any other activity
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                     */
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistrationActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
