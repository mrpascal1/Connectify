package com.shahid.connectify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.shahid.connectify.databinding.ActivityMainBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MeowBottomNavigation bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bnv = findViewById(R.id.bnv);
        bnv.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_25));
        bnv.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_add_photo_alternate_24));
        bnv.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_supervised_user_circle_24));
        bnv.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_person_24));

        bnv.show(1, true);
        replaceFragment(new FragmentHome());

        bnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replaceFragment(new FragmentHome());
                        break;
                    case 2:
                        replaceFragment(new FragmentDashboard());
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
}
