package com.bignerdranch.android.physicsSim.activities;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.fragments.LoadSimFragment;
import com.bignerdranch.android.physicsSim.fragments.NewSimFragment;

public class LoadSimActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new LoadSimFragment(); }
}