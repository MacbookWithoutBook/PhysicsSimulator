package com.bignerdranch.android.physicsSim.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bignerdranch.android.physicsSim.activities.MainMenuActivity;
import com.bignerdranch.android.physicsSim.R;
import com.bignerdranch.android.physicsSim.databinding.FragmentAddObjectBinding;
import com.bignerdranch.android.physicsSim.databinding.FragmentSceneBinding;
import com.bignerdranch.android.physicsSim.entities.SimpleSimObject;
import com.bignerdranch.android.physicsSim.viewmodels.SimObjectViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SceneFragment} factory method to
 * create an instance of this fragment.
 */
public class SceneFragment extends Fragment {

    // the fragment initialization parameters
    ImageView squareView;
    Button back_button, run_button, save_button, clear_button;

    private static final String TAG = SceneFragment.class.getSimpleName();
    private FragmentSceneBinding binding;

    public EditText editForce;
    public EditText editTime;
    public EditText editMass;
    public float distance;

    SimObjectViewModel mSimObjectViewModel;

    SimpleSimObject mSimObject;



    public SceneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate: Fragment");
        Activity activity = requireActivity();
        mSimObjectViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SimObjectViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateView: Fragment");
        binding = FragmentSceneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // get user input of force, double, and mass
        //View editView = inflater.inflate(R.layout.fragment_scene, container, false);
        editForce = binding.editForce;
        editTime = binding.editTime;
        editMass = binding.editMass;
        TextView distanceTextView = binding.distanceTextView;
        TextView totalDistanceTextView = binding.totalDistanceTextView;

        // Retrieve loaded SimObject info.
        mSimObjectViewModel.getSimulationSimpleObject().observe((LifecycleOwner) requireActivity(), simObject -> {
            mSimObject = simObject;
            editForce.setText(String.format("%.2f", mSimObject.mForce));
            editTime.setText(String.format("%.2f", mSimObject.mTime));
            editMass.setText(String.format("%.2f", mSimObject.mMass));
        });

        binding.backButton.setOnClickListener((v) -> {
            startActivity((new Intent(getContext(), MainMenuActivity.class)));
            Log.d(TAG, "Press Back.");
        });

        binding.runButton.setOnClickListener((v) -> {
            Log.d(TAG, "Press Run.");

            updateSimObjectToFields();

            // Do the calculation: if initial velocity is 0, distance = 1/2 * a * t^2 = 1/2 * (F/m) * t^2
            distance = mSimObjectViewModel.calculateDestinationX(mSimObject);
            Log.i("distance", Float.toString(distance));
            distanceTextView.setText("Distance: " + distance);


            // Get the scale that an object should move based on its value
            // e.g. if distance = 2 meters, scale = 2/10 = 0.2;
            // if distance = 780 meters, scale = 780/10/10/10 = 0.78
            float scale = distance;
            // totalDistance is the distance from the starting point to the wall
            float totalDistance = 1.0f;
            while (!(scale >= 0 && scale <= 1)) {
                scale = scale / 10.0f;
                totalDistance = totalDistance * 10;
            }

            totalDistanceTextView.setText("Total Distance(m): " + totalDistance);

            float fromXValue = 0.0f; // Start from the left edge of the view
            float toXValue = (float) scale; // Move halfway to the right of the view
            int fromXType = Animation.RELATIVE_TO_PARENT; // Use relative values for the X coordinate
            int toXType = Animation.RELATIVE_TO_PARENT;
            float fromYValue = 0.0f;
            float toYValue = 0.0f;
            int fromYType = Animation.RELATIVE_TO_PARENT;
            int toYType = Animation.RELATIVE_TO_PARENT;
            Animation translateAnim = new TranslateAnimation(
                    fromXType, fromXValue, toXType, toXValue,
                    fromYType, fromYValue, toYType, toYValue
            );
            translateAnim.setDuration((long) mSimObject.mTime * 1000);
            translateAnim.setInterpolator(new AccelerateInterpolator());
            translateAnim.setFillAfter(true);

            // TODO: get the value of squareView
            binding.square.startAnimation(translateAnim);
        });

        binding.saveButton.setOnClickListener((v) -> {
            Log.d(TAG, "Press Save.");

            if (mSimObject != null)
            {
                updateSimObjectToFields();
                mSimObjectViewModel.update(mSimObject);
                Toast.makeText(requireActivity().getApplicationContext(),
                        "SimObject values saved.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.clearButton.setOnClickListener((v) -> {
            Log.d(TAG, "Press Clear.");
            binding.square.clearAnimation();
        });

        binding.square.setOnClickListener((v) -> {
            // prompt out a window shows adding attribute
        });

        return view;
    }

    private void updateSimObjectToFields() {
        mSimObject.mForce = Float.parseFloat(editForce.getText().toString());
        Log.i("force", String.valueOf(mSimObject.mForce));
        mSimObject.mTime = Float.parseFloat(editTime.getText().toString());
        Log.i("time", String.valueOf(mSimObject.mTime));
        mSimObject.mMass = Float.parseFloat(editMass.getText().toString());
        Log.i("mass", String.valueOf(mSimObject.mMass));
    }

}