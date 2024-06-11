package com.shahid.connectify;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shahid.connectify.databinding.ActivityMainBinding;

import java.io.*;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        replaceFragment(new FragmentHome());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

          if (item.getItemId()==R.id.house){
              replaceFragment(new FragmentHome());
          } else if (item.getItemId()==R.id.profile) {
              replaceFragment(new FragmentProfile());
          } else if (item.getItemId()==R.id.dashboard) {
              replaceFragment(new FragmentDashboard());
          }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.Fragment, fragment);
        fragmentTransaction.commit();

    }

}
