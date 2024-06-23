package com.shahid.connectify;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahid.connectify.R;
import com.shahid.connectify.databinding.FragmentHomeBinding;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHome extends Fragment {

    private FragmentHomeBinding binding;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private FirebaseDatabase firebaseDatabase;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProgressDialog();
        postList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        postAdapter = new PostAdapter(requireActivity(), postList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postAdapter);

        getData();
    }

    private void getData() {
        progressDialog.show();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Post post = ds.getValue(Post.class);
                            if (post != null) {
                                postList.add(post);
                            }
                        }
                    }
                }
                progressDialog.dismiss();
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }


    private void initProgressDialog() {
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Connectify");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }
}