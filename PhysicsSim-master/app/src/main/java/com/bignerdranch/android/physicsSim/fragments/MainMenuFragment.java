package com.bignerdranch.android.physicsSim.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.physicsSim.AppRepository;
import com.bignerdranch.android.physicsSim.activities.LoadSimActivity;
import com.bignerdranch.android.physicsSim.activities.LoginActivity;
import com.bignerdranch.android.physicsSim.activities.MapActivity;
import com.bignerdranch.android.physicsSim.activities.NewSimActivity;
import com.bignerdranch.android.physicsSim.activities.SceneActivity;
import com.bignerdranch.android.physicsSim.activities.UserSettingsActivity;
import com.bignerdranch.android.physicsSim.databinding.FragmentMainMenuBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private FragmentMainMenuBinding binding;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();

        binding.buttonNewSimulation.setOnClickListener((v) -> {
            Log.d(TAG, "New Simulation.");
            final Activity activity = requireActivity();
            final Context appContext = activity.getApplicationContext();
            startActivity(new Intent(appContext, NewSimActivity.class));
        });

        binding.buttonSimulationList.setOnClickListener((v) -> {
            Log.d(TAG, "Simulation List.");
            final Activity activity = requireActivity();
            final Context appContext = activity.getApplicationContext();
            startActivity(new Intent(appContext, LoadSimActivity.class));
        });

        binding.buttonUserSettings.setOnClickListener((v) -> {
            Log.d(TAG, "Settings.");
            final Activity activity = requireActivity();
            final Context appContext = activity.getApplicationContext();
            startActivity(new Intent(appContext, UserSettingsActivity.class));
        });

        binding.buttonLabLocations.setOnClickListener((v) -> {
            Log.d(TAG, "Lab Locations.");
            final Activity activity = requireActivity();
            final Context appContext = activity.getApplicationContext();
            startActivity(new Intent(appContext, MapActivity.class));
        });

        binding.buttonLogout.setOnClickListener(v -> {
            final Activity activity = requireActivity();
            final Context appContext = activity.getApplicationContext();
            AppRepository.getInstance(activity.getApplication()).setCurrentUid(0);
            Intent resetAppIntent = new Intent(appContext, LoginActivity.class);
            // Don't let the user go back to the previous screen.
            resetAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(resetAppIntent);
        });

        binding.buttonExit.setOnClickListener((v) -> {
            Log.d(TAG, "Exit.");
            getActivity().finish();
            System.exit(0);
        });

        return view;
    }
}