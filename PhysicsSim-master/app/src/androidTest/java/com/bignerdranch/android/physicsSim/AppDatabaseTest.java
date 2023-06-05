package com.bignerdranch.android.physicsSim;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bignerdranch.android.physicsSim.daos.UserAccountDao;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private UserAccountDao userAccountDao;
    private AppDatabase testDb;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userAccountDao = testDb.getUserAccountDao();
    }

    @After
    public void tearDown() throws Exception {
        testDb.close();
    }

    @Test
    public void createAndRetrieveUser() {
        String name = "test";
        String password = "abcDEF";
        UserAccount userAccount = new UserAccount(name, password);
        userAccountDao.insert(userAccount);

        assertTrue(userAccountDao.hasUserName(name));
    }

    @Test
    public void createAndDeleteAllUsers() {
        String name = "test";
        String password = "abcDEF";
        UserAccount userAccount = new UserAccount(name, password);
        userAccountDao.insert(userAccount);

        userAccountDao.deleteAllUsers();

        assertFalse(userAccountDao.hasUserName(name));
    }
}