package com.bignerdranch.android.physicsSim;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.bignerdranch.android.physicsSim.daos.ObjectForceDao;
import com.bignerdranch.android.physicsSim.daos.SimObjectDao;
import com.bignerdranch.android.physicsSim.daos.SimpleSimObjectDao;
import com.bignerdranch.android.physicsSim.daos.SimulationDao;
import com.bignerdranch.android.physicsSim.daos.UserAccountDao;
import com.bignerdranch.android.physicsSim.entities.SimpleSimObject;
import com.bignerdranch.android.physicsSim.entities.Simulation;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Single point of accessing UserAccount data in the app.
 *
 * Source: <a href="https://developer.android.com/codelabs/android-room-with-a-view">...</a>
 *
 * Created by acc on 2021/08/04.
 */
public class AppRepository {

	private int currentUid;
	private int currentSimid;

	private final UserAccountDao mUserAccountDao;
	private final SimulationDao mSimulationDao;
	private final SimpleSimObjectDao mSimpleSimObjectDao;
	private final SimObjectDao mSimObjectDao;
	private final ObjectForceDao mObjectForceDao;

	private final String TAG = getClass().getSimpleName();

	//region Singleton
	private static volatile AppRepository sInstance;

	public static AppRepository getInstance(Application application) {
		if (sInstance == null) {
			synchronized (AppRepository.class) {
				if (sInstance == null) {
					sInstance = new AppRepository(application);
				}
			}
		}
		return sInstance;
	}
	//endregion

	public AppRepository(Application application) {
		AppDatabase db = AppDatabase.getDatabase(application);
		mUserAccountDao = db.getUserAccountDao();
		mSimulationDao = db.getSimulationDao();
		mSimpleSimObjectDao = db.getSimpleSimObjectDao();
		mSimObjectDao = db.getSimObjectDao();
		mObjectForceDao = db.getObjectForceDao();
	}

	public int getCurrentUid() { return currentUid; }
	public void setCurrentUid(int uid) { currentUid = uid; }

	public int getCurrentSimid() { return currentSimid; }
	public void setCurrentSimid(int simid) { currentSimid = simid; }


	//region User Accounts

	// Room executes all queries on a separate thread.
	// Observed LiveData notify the observer upon data change.
	public LiveData<List<UserAccount>> getAllUserAccounts() {
		return mUserAccountDao.getAllUserAccounts();
	}

	public LiveData<UserAccount> findUserAccountByName(UserAccount account) {
		return mUserAccountDao.findByName(account.getName(), account.getPassword());
	}

	public LiveData<UserAccount> getCurrentUserAccount() {
		return mUserAccountDao.findById(currentUid);
	}

	// You MUST call this on a non-UI thread or the app will throw an exception.
	// I'm passing a Runnable object to the database.
	public void insertUserAccount(UserAccount account) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mUserAccountDao.insert(account));
	}

	// Similarly, I'm calling update() on a non-UI thread.
	public void updateUserAccount(UserAccount... accounts) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mUserAccountDao.update(accounts));
	}

	// Similarly, I'm calling delete() on a non-UI thread.
	public void deleteUserAccount(UserAccount... accounts) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mUserAccountDao.delete(accounts));
	}

	public boolean hasUserName(String name) {
		return mUserAccountDao.hasUserName(name);
	}

	public void deleteAllUsers() {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mUserAccountDao.deleteAllUsers());

	}
	//endregion


	//region Simulations
	public LiveData<List<Simulation>> getCurrentUserSimulations() {
		return mSimulationDao.getUserSimulations(currentUid);
	}

	public LiveData<Simulation> findSimulationByUidAndName(Simulation simulation) {
		return mSimulationDao.findByUidAndName(simulation.mUid, simulation.mSimName);
	}

	public long insertSimulation(Simulation simulation) {
		// Retrieve back the simid of the inserted simulation.
		Future<Long> future = AppDatabase.databaseWriteExecutor.submit(() -> mSimulationDao.insert(simulation));
		long insertedId = 0;
		try {
			insertedId = future.get(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.e(TAG, "Exception while getting insertedId.");
			Log.e(TAG, e.getLocalizedMessage());
		}
		return insertedId;
	}

	public void updateSimulation(Simulation... simulations) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mSimulationDao.update(simulations));
	}

	public void deleteSimulation(Simulation... simulations) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mSimulationDao.delete(simulations));
	}

	public boolean currentUserHasSimulation(String simname) {
		return mSimulationDao.userHasSimulation(currentUid, simname);
	}
	//endregion

	//region Simple Simulation Objects
	public LiveData<SimpleSimObject> getCurrentSimulationSimpleObject() {
		return mSimpleSimObjectDao.getSimpleSimObjectBySimulation(currentSimid);
	}

	public void insertSimpleSimObject(SimpleSimObject simpleSimObject) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mSimpleSimObjectDao.insert(simpleSimObject));
	}

	public void updateSimpleSimObject(SimpleSimObject... simpleSimObjects) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mSimpleSimObjectDao.update(simpleSimObjects));
	}

	public void deleteSimpleSimObject(SimpleSimObject... simpleSimObjects) {
		AppDatabase.databaseWriteExecutor.execute(() ->
				mSimpleSimObjectDao.delete(simpleSimObjects));
	}

	//endregion
}
