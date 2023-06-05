package com.bignerdranch.android.physicsSim;

import static org.junit.Assert.*;

import com.bignerdranch.android.physicsSim.fragments.AccountFragment;

import org.junit.Test;

public class AccountFragmentTest {

    @Test
    public void rejectInvalidPasswordLength() {
        String password = "abc";
        assertFalse(AccountFragment.passwordIsValid(password));
    }

    @Test
    public void rejectInvalidPasswordNoLetters() {
        String password = "$$#(!)%1233";
        assertFalse(AccountFragment.passwordIsValid(password));
    }

    @Test
    public void rejectInvalidPasswordNoUppercase() {
        String password = "abcdef";
        assertFalse(AccountFragment.passwordIsValid(password));
    }

    @Test
    public void rejectInvalidPasswordNoLowercase() {
        String password = "ABCDEF";
        assertFalse(AccountFragment.passwordIsValid(password));
    }

    @Test
    public void acceptValidPassword() {
        String password = "abcDefA";
        assertTrue(AccountFragment.passwordIsValid(password));
    }

    @Test
    public void acceptValidPassword2() {
        String password = "Ab$$$$$";
        assertTrue(AccountFragment.passwordIsValid(password));
    }
}