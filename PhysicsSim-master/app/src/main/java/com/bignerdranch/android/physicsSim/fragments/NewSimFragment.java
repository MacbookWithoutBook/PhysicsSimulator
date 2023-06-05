package com.bignerdranch.android.physicsSim.fragments;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.physicsSim.activities.SceneActivity;
import com.bignerdranch.android.physicsSim.databinding.FragmentNewSimBinding;
import com.bignerdranch.android.physicsSim.entities.SimpleSimObject;
import com.bignerdranch.android.physicsSim.entities.Simulation;
import com.bignerdranch.android.physicsSim.viewmodels.SimObjectViewModel;
import com.bignerdranch.android.physicsSim.viewmodels.SimulationViewModel;

public class NewSimFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private FragmentNewSimBinding binding;

    private SimulationViewModel mSimViewModel;

    private SimObjectViewModel mSimObjectViewModel;


    public static NewSimFragment newInstance() {
        return new NewSimFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) { super.onAttach(context); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        Activity activity = requireActivity();
        mSimViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SimulationViewModel.class);
        mSimObjectViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SimObjectViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewSimBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.createButton.setOnClickListener((v) -> createSimulation());

        return view;
    }

    private void createSimulation() {
        EditText mEtSimname = binding.simname;
        String simName = mEtSimname.getText().toString().trim();
        FragmentActivity activity = requireActivity();

        if (simName.isEmpty()) {
            Toast.makeText(activity,"Simulation name is blank.", Toast.LENGTH_SHORT).show();
            return;
        } else if (mSimViewModel.currentUserHasSimulation(simName)) {
            Toast.makeText(activity,"Simulation already exists.", Toast.LENGTH_SHORT).show();
            return;
        }

        Simulation simulation = new Simulation(mSimViewModel.getUid(), simName);

        if (mSimViewModel.containsSimulation(simulation)) {
            Toast.makeText(activity,"Simulation name already exists in your account.", Toast.LENGTH_SHORT).show();
            return;
        }
        int simid = (int) mSimViewModel.insert(simulation);
        // Create the default simple object associated with the simulation.
        SimpleSimObject simObject = new SimpleSimObject(simid, 50.0f, 0.0f, 50.0f, 1.0f);
        mSimObjectViewModel.insert(simObject);
        Toast.makeText(activity,"New Simulation added.", Toast.LENGTH_SHORT).show();

        mSimViewModel.setSimid(simid);
        startActivity(new Intent(activity, SceneActivity.class));
    }
}