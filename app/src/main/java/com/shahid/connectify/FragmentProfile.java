package com.shahid.connectify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahid.connectify.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentProfile extends Fragment {

    private FragmentProfileBinding binding;

    private PostAdapter postAdapter;
    private List<Post> postList;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        postList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        postAdapter = new PostAdapter(requireActivity(), postList, "");

        postAdapter.setiAdapterClick(new IAdapterClick() {
            @Override
            public void onLikeClick(int position, String postId) {
                setLike(postId);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postAdapter);

        binding.editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        setProfileData();
        getData();
    }



    private String getUID() {
        if (firebaseAuth.getCurrentUser() != null) {
            return firebaseAuth.getCurrentUser().getUid();
        }
        return "";
    }

    private void setLike(String postId) {
        DatabaseReference likeRef =  firebaseDatabase.getReference("Posts/" + postId + "/likes");
        DatabaseReference userRef =  firebaseDatabase.getReference("Posts/" + postId + "/likes/" + getUID());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userRef.removeValue();
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(getUID(), true);
                    likeRef.updateChildren(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setProfileData() {
        String uid = "";
        if (firebaseAuth.getCurrentUser() != null) {
            uid = firebaseAuth.getCurrentUser().getUid();
        }
        DatabaseReference reference = firebaseDatabase.getReference("Users/" + uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        binding.username.setText(user.getUsername());
                        if (user.getBio() != null) {
                            binding.bio.setText(user.getBio());
                        }
                        Glide.with(requireContext()).load(user.getImageUrl()).into(binding.profileIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getData() {
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
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}