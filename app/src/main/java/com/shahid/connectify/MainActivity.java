package com.shahid.connectify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahid.connectify.databinding.ActivityMainBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MeowBottomNavigation bnv;

    private int lastSelected = 1;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth auth;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        sharedPref = this.getSharedPreferences(
                "com.shahid.connectify", Context.MODE_PRIVATE);

        bnv = findViewById(R.id.bnv);
        bnv.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_25));
        bnv.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_add_photo_alternate_24));
        bnv.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_supervised_user_circle_24));
        bnv.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_person_24));

        bnv.show(1, true);
        replaceFragment(new FragmentHome());

        fetchUserData();

        bnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if (model.getId() != 2) {
                    lastSelected = model.getId();
                }

                switch (model.getId()) {
                    case 1:
                        replaceFragment(new FragmentHome());
                        break;
                    case 2:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resetSelectionForAddPost();
                            }
                        }, 100);
                        Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        replaceFragment(new FragmentUsers());
                        break;
                    case 4:
                        replaceFragment(new FragmentProfile());
                        break;
                }
                return null;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment, fragment);
        fragmentTransaction.commit();
    }

    private void resetSelectionForAddPost() {
        bnv.show(lastSelected, true);
    }

    private String getUID() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        }
        return "";
    }

    private void saveImageUrl(String url) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("imageUrl", url);
        editor.apply();
        editor.commit();
    }

    private void fetchUserData() {
        DatabaseReference reference = firebaseDatabase.getReference("Users/" + getUID());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        saveImageUrl(user.getImageUrl());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
