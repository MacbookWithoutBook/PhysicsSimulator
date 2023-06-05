package com.bignerdranch.android.physicsSim.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bignerdranch.android.physicsSim.AppRepository;
import com.bignerdranch.android.physicsSim.StringUtils;
import com.bignerdranch.android.physicsSim.activities.LoginActivity;
import com.bignerdranch.android.physicsSim.databinding.FragmentUserSettingsBinding;
import com.bignerdranch.android.physicsSim.entities.UserAccount;
import com.bignerdranch.android.physicsSim.viewmodels.UserAccountViewModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserSettingsFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private FragmentUserSettingsBinding binding;
    private UserAccountViewModel mViewModel;
    private UserAccount mUserAccount;

    @Override
    public void onAttach(@NonNull Context context) { super.onAttach(context); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        Activity activity = requireActivity();
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserAccountViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mViewModel.getCurrentUser().observe((LifecycleOwner) requireActivity(), user -> {
            mUserAccount = user;
            binding.buttonDeleteAccount.setEnabled(true);
            binding.buttonUpdatePassword.setEnabled(true);

            binding.buttonUpdatePassword.setOnClickListener(v -> updatePassword());
            binding.buttonDeleteAccount.setOnClickListener((v) -> deleteAccount());
        });
        return view;
    }

    private void updatePassword() {
        Activity activity = requireActivity();
        final String password = binding.editTextPassword.getText().toString();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(activity.getApplicationContext(), "Password can't be empty", Toast.LENGTH_SHORT);
            return;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] sha256HashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String sha256HashStr = StringUtils.bytesToHex(sha256HashBytes);

            mUserAccount.mPassword = sha256HashStr;
            mViewModel.updateUser(mUserAccount);
            binding.editTextPassword.setText("");
            Toast.makeText(activity.getApplicationContext(), "Password updated.", Toast.LENGTH_SHORT).show();

        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(activity, "Error: No SHA-256 algorithm found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void deleteAccount() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete user account")
                .setMessage("Are you SURE you want to delete your account?")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                    mViewModel.deleteUser(mUserAccount);
                    final Activity activity = requireActivity();
                    final Context appContext = activity.getApplicationContext();
                    AppRepository.getInstance(activity.getApplication()).setCurrentUid(0);
                    Intent resetAppIntent = new Intent(appContext, LoginActivity.class);
                    // Don't let the user go back to the previous screen.
                    resetAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(resetAppIntent);
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}