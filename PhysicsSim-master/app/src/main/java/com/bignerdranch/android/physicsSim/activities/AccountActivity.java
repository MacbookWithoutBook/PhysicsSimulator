package com.bignerdranch.android.physicsSim.activities;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.R;
import com.bignerdranch.android.physicsSim.fragments.AccountFragment;

public class AccountActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new AccountFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
