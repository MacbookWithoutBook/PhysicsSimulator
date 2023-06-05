package com.bignerdranch.android.physicsSim.activities;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.fragments.NewSimFragment;

public class NewSimActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new NewSimFragment(); }
}