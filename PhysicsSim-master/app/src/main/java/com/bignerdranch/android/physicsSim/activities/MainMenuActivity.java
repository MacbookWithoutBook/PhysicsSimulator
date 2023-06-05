package com.bignerdranch.android.physicsSim.activities;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.fragments.MainMenuFragment;

public class MainMenuActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainMenuFragment();
    }
}