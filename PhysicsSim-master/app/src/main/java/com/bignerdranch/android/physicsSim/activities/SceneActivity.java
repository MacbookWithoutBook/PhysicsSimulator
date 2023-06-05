package com.bignerdranch.android.physicsSim.activities;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.fragments.SceneFragment;

public class SceneActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SceneFragment();
    }
}