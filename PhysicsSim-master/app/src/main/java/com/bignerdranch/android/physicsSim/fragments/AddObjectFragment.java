package com.bignerdranch.android.physicsSim.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bignerdranch.android.physicsSim.R;
import com.bignerdranch.android.physicsSim.databinding.FragmentAddObjectBinding;
import com.bignerdranch.android.physicsSim.entities.SimObject;

public class AddObjectFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private FragmentAddObjectBinding binding;

    public static AddObjectFragment newInstance() { return new AddObjectFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddObjectBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.addButton.setOnClickListener((v) -> addObject());

        return view;
    }

    private void addObject() {
        EditText mEtMass = binding.inputMass;
        EditText mEtPosX = binding.inputPositionX;

        SimObject simObject = new SimObject(0,0,0,0);

    }
}
