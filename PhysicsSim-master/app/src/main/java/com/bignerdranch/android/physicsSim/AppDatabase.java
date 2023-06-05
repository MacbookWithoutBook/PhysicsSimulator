package com.bignerdranch.android.physicsSim;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bignerdranch.android.physicsSim.daos.ObjectForceDao;
import com.bignerdranch.android.physicsSim.daos.SimObjectDao;
import com.bignerdranch.android.physicsSim.daos.SimpleSimObjectDao;
import com.bignerdranch.android.physicsSim.daos.SimulationDao;
import com.bignerdranch.android.physicsSim.daos.UserAccountDao;
import com.bignerdranch.android.physicsSim.entities.ObjectForce;
import com.bignerdranch.android.physicsSim.entities.SimObject;
import com.bignerdranch.android.physicsSim.entities.SimpleSimObject;
import com.bignerdranch.android.physicsSim.entities.Simulation;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Database class for UserAccount processing with Room.
 *
 * Source: <a href="https://developer.android.com/codelabs/android-room-with-a-view">...</a>
 *
 * Created by acc on 2021/08/04.
 */
@Database(entities = {UserAccount.class, Simulation.class, SimpleSimObject.class, SimObject.class, ObjectForce.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase  {
	public abstract UserAccountDao getUserAccountDao();
	public abstract SimulationDao getSimulationDao();
	public abstract SimpleSimObjectDao getSimpleSimObjectDao();
	public abstract SimObjectDao getSimObjectDao();
	public abstract ObjectForceDao getObjectForceDao();

	private static volatile AppDatabase sInstance;
	private static final int sNumberOfThreads = 2;
	static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(sNumberOfThreads);

	static AppDatabase getDatabase(final Context context) {
		if (sInstance == null) {
			synchronized (AppDatabase.class) {
				if (sInstance == null) {
					sInstance = Room.databaseBuilder(context.getApplicationContext(),
							AppDatabase.class, "useraccount_database").allowMainThreadQueries()
							.build();
				}
			}
		}

		return sInstance;
	}
}
