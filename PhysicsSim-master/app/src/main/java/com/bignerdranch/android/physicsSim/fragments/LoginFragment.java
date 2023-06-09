package com.bignerdranch.android.physicsSim.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bignerdranch.android.physicsSim.activities.MainMenuActivity;
import com.bignerdranch.android.physicsSim.R;
import com.bignerdranch.android.physicsSim.StringUtils;
import com.bignerdranch.android.physicsSim.viewmodels.UserAccountViewModel;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import timber.log.Timber;

/**
 * Fragment for login screen.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private UserAccountViewModel mUserAccountViewModel;
	private final List<UserAccount> mUserAccountList = new CopyOnWriteArrayList<>();

	private final String TAG = getClass().getSimpleName();
	private final static String OPT_NAME = "name";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Timber.tag(TAG).d("onCreate()");

		Activity activity = requireActivity();

		mUserAccountViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserAccountViewModel.class);

		// Method used to delete all existing accounts
//		mUserAccountViewModel.deleteAllUsers();

		// Here's a dummy observer object that indicates when the UserAccounts change in the database.
		mUserAccountViewModel.getAllUserAccounts().observe((LifecycleOwner) activity, userAccounts -> {
			Timber.tag(TAG).d("The list of UserAccounts just changed; it has %s elements", userAccounts.size());
			mUserAccountList.clear();
			for (UserAccount account : userAccounts) {
				Log.d("account_name", account.getName().toString());
			}
			mUserAccountList.addAll(userAccounts);
		});
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v;
		Timber.tag(TAG).d("onCreateView()");
		Activity activity = requireActivity();

		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

		if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
			v = inflater.inflate(R.layout.fragment_login_land, container, false);
		} else {
			v = inflater.inflate(R.layout.fragment_login, container, false);
		}

		mUsernameEditText = v.findViewById(R.id.username_text);
		mPasswordEditText = v.findViewById(R.id.password_text);

		final Button loginButton = v.findViewById(R.id.login_button);
		if (loginButton != null) {
			loginButton.setOnClickListener(this);
		}
		final Button cancelButton = v.findViewById(R.id.cancel_button);
		if (cancelButton != null) {
			cancelButton.setOnClickListener(this);
		}

		final Button newUserButton = v.findViewById(R.id.new_user_button);
		if (newUserButton != null) {
			if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
				newUserButton.setOnClickListener(this);
			} else {
				newUserButton.setVisibility(View.GONE);
				newUserButton.invalidate();
			}
		}

		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Timber.tag(TAG).d("onDestroyView()");
		mUsernameEditText = null;
		mPasswordEditText = null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Timber.tag(TAG).d("onDestroy()");
		final Activity activity = requireActivity();
		mUserAccountViewModel.getAllUserAccounts().removeObservers((LifecycleOwner) activity);
	}

	private void checkLogin() {
		final String username = mUsernameEditText.getText().toString();
		final String password = mPasswordEditText.getText().toString();

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] sha256HashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			String sha256HashStr = StringUtils.bytesToHex(sha256HashBytes);

			Activity activity = requireActivity();

			UserAccount userAccount = new UserAccount(username, sha256HashStr);

			if (mUserAccountList.contains(userAccount)) {
				UserAccount instanceInList = mUserAccountList.get(mUserAccountList.indexOf(userAccount));
				// Save username as the name of the player
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(OPT_NAME, username);
				editor.apply();

				// Set the current user globally in repository.
				mUserAccountViewModel.setUid(instanceInList.mUid);

				// Bring up the GameOptions screen
				startActivity(new Intent(activity, MainMenuActivity.class));
				activity.finish();
			} else {
				Toast.makeText(activity, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private void showLoginErrorFragment() {
		FragmentManager manager = getParentFragmentManager();
		//LoginErrorDialogFragment fragment = new LoginErrorDialogFragment();
		//fragment.show(manager, "login_error");
	}

	@Override
	public void onClick(View view) {
		final Activity activity = requireActivity();
		final int viewId = view.getId();

		if (viewId == R.id.login_button) {
			checkLogin();
		} else if (viewId == R.id.cancel_button) {
			activity.finish();
			System.exit(0);
		} else if (viewId == R.id.new_user_button) {
			final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
			FragmentManager fm = getParentFragmentManager();
			Fragment fragment = new AccountFragment();
			if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
				fm.beginTransaction()
						.replace(R.id.fragment_container, fragment)
						.addToBackStack("account_fragment")
						.commit();
			}
		} else {
			Timber.tag(TAG).e("Invalid button click!");
		}
	}
}
