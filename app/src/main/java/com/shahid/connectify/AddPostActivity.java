package com.shahid.connectify;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahid.connectify.databinding.AddPostActivityBinding;

import java.util.HashMap;

public class AddPostActivity extends AppCompatActivity {

    private AddPostActivityBinding binding;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddPostActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initProgressDialog();

        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.addPostTitle.getText().toString();
                String description = binding.addPostDescription.getText().toString();

                if (!title.equals("") && !description.equals("")) {
                    uploadPost(title, description);
                } else {
                    Toast.makeText(AddPostActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding post...");
        progressDialog.setTitle("Connectify");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void uploadPost(String title, String description) {
        progressDialog.show();
        String timestamp = String.valueOf(System.currentTimeMillis());

        DatabaseReference databaseReference = firebaseDatabase.getReference("Posts");
        String uniqueKey = databaseReference.push().getKey();

        Post post = new Post(uniqueKey, "Alice", timestamp, description, title);

        HashMap<String, Object> map = new HashMap<>();
        map.put(uniqueKey, post);

        databaseReference.updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AddPostActivity.this, "Post Added...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddPostActivity.this, "Failed to add post", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
