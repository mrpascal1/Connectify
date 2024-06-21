package com.shahid.connectify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterationPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                post(username, email, password);
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
        if (isReservedWord(username)) {
            usernameEditText.setError("Username cannot be a reserved word");
            return false;
        }
        if (isUsernameTaken(username)) {
            usernameEditText.setError("Username is already taken");
            return false;
        }
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
        if (!isValidDomain(email)) {
            emailEditText.setError("Enter a valid email domain");
            return false;
        }
        if (isEmailRegistered(email)) {
            emailEditText.setError("Email is already registered");
            return false;
        }
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

    private boolean isUsernameTaken(String username) {
        // Placeholder for actual implementation
        // You need to implement this method to check if the username is taken
        return false;
    }

    private boolean isReservedWord(String username) {
        // Placeholder for actual implementation
        // You need to implement this method to check if the username is a reserved word
        return false;
    }

    private boolean isValidDomain(String email) {
        // Placeholder for actual implementation
        // You need to implement this method to check if the email domain is valid
        return true;
    }

    private boolean isEmailRegistered(String email) {
        // Placeholder for actual implementation
        // You need to implement this method to check if the email is already registered
        return false;
    }

    private String hashPassword(String password) {
        // Placeholder for password hashing
        // Implement a proper password hashing mechanism here (e.g., using BCrypt)
        return password; // Replace this with actual hashed password
    }

    public void post(String username, String email, String pass) {
        String hashedPassword = hashPassword(pass);

        HashMap<String, String> map = new HashMap<>();
        map.put("Username", username);
        map.put("EmailID", email);
        map.put("PassWord", hashedPassword);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.push().setValue(map)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Clear the input fields
                    emailEditText.setText("");
                    passwordEditText.setText("");
                    usernameEditText.setText("");
                    reenterPasswordEditText.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistrationActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
