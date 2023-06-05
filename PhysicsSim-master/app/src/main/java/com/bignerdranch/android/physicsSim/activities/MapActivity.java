package com.bignerdranch.android.physicsSim.activities;

import android.app.Dialog;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.fragments.MapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MapActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;

    @Override
    protected Fragment createFragment() { return new MapFragment(); }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR,
                    dialogInterface -> {
                        // Quit the activity if Google Play services are not available.
                        finish();
                    });
            assert errorDialog != null;
            errorDialog.show();
        }
    }
}
