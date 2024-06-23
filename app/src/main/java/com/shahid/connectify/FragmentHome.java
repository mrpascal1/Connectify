package com.shahid.connectify;

import com.shahid.connectify.R;
import com.shahid.connectify.databinding.FragmentHomeBinding;

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
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(requireActivity(), postList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postAdapter);
        setDummyData();

    }

    public void setDummyData() {
//        postList.add(
//                new Post("Test",
//                        "11-09-2025",
//                        "Test Desccription",
//                        "Post Title")
//        );
//        postList.add(
//                new Post("Test 2",
//                        "11-09-2024",
//                        "Test Desccription 2",
//                        "Post Title 2")
//        );
//        postList.add(
//                new Post("Test 3",
//                        "11-09-2023",
//                        "Test Desccription 3",
//                        "Post Title 3")
//        );
    }
}