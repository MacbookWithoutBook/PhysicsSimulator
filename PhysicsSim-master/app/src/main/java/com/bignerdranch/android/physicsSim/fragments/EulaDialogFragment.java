package com.bignerdranch.android.physicsSim.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.bignerdranch.android.physicsSim.R;

public class EulaDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.about_app)
                .setMessage(Html.fromHtml(getString(R.string.eula), Html.FROM_HTML_MODE_LEGACY))
                .setPositiveButton(R.string.accept, (dialog, id) -> setEulaAccepted())
                .setNegativeButton(R.string.decline, (dialog, which) -> {
                    dialog.cancel();
                    requireActivity().finish();
                    System.exit(1);
                });
        return builder.create();
    }

    private void setEulaAccepted() {
        Activity activity = requireActivity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.eula_accepted_key), true).apply();
    }
}
