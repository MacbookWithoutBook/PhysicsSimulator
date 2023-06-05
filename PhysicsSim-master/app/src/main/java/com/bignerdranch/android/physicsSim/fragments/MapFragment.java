package com.bignerdranch.android.physicsSim.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import com.bignerdranch.android.physicsSim.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private final String TAG = getClass().getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap map;

    // Callback for requesting permission and enabling location if user consents.
    @SuppressLint("MissingPermission")
    private final ActivityResultLauncher<String[]> requestPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineResult = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseResult = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                if ((fineResult != null && fineResult) || (coarseResult != null && coarseResult)) {
                    // Show user's location
                    enableLocation();
                } else {
                    Log.e(TAG, "Error: Location permission denied.");
                    Toast.makeText(requireActivity(),
                            "Please change your permission settings to view your location.",
                            Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Add map markers at famous physics institution locations.
        LatLng cas = new LatLng(39.921749, 116.328527);
        map.addMarker(new MarkerOptions()
                .position(cas)
                .title("Chinese Academy of Sciences (CAS), Beijing, China"));
        LatLng maxPlanck = new LatLng(48.141155, 11.582078);
        map.addMarker(new MarkerOptions()
                .position(maxPlanck)
                .title("Max Planck Society, Munich, Germany"));
        LatLng cnrs = new LatLng(48.847618, 2.264010);
        map.addMarker(new MarkerOptions()
                .position(cnrs)
                .title("French National Centre for Scientific Research, Paris, France"));
        LatLng mit = new LatLng(42.359722, -71.091944);
        map.addMarker(new MarkerOptions()
                .position(mit)
                .title("Massachusetts Institute of Technology, Cambridge, Massachusetts, US"));
        LatLng stanford = new LatLng(37.428229, -122.168858);
        map.addMarker(new MarkerOptions()
                .position(stanford)
                .title("Stanford University, Stanford, California, US"));
        LatLng cern = new LatLng(46.233, 6.0557);
        map.addMarker(new MarkerOptions()
                .position(cern)
                .title("European Organization for Nuclear Research, Geneva, Switzerland"));

        map.getUiSettings().setZoomControlsEnabled(true);

        if (hasLocationPermission()) {
            enableLocation();
        } else {
            // Request permission for geolocation if not granted already.
            requestPermissionResult.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    @SuppressLint("MissingPermission")
    private void enableLocation() {
        assert hasLocationPermission();

        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
    }

    private boolean hasLocationPermission() {
        final Activity activity = requireActivity();
        int coarseResult = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineResult = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return fineResult == PackageManager.PERMISSION_GRANTED || coarseResult == PackageManager.PERMISSION_GRANTED;
    }
}
