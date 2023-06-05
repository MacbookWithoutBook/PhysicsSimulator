package com.bignerdranch.android.physicsSim.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bignerdranch.android.physicsSim.entities.Simulation;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.util.List;

@Dao
public interface SimulationDao {
    @Query("SELECT rowid, uid, simName FROM simulation WHERE uid = CAST(:uid AS INTEGER)")
    LiveData<List<Simulation>> getUserSimulations(int uid);

    @Query("SELECT rowid, uid, simName FROM simulation WHERE uid = CAST(:uid AS INTEGER) AND simName LIKE :simname LIMIT 1")
    LiveData<Simulation> findByUidAndName(int uid, String simname);

    @Query("SELECT EXISTS(SELECT * FROM simulation WHERE uid = CAST(:uid AS INTEGER) AND simName LIKE :simname)")
    boolean userHasSimulation(int uid, String simname);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Simulation simulation);

    @Update
    void update(Simulation... simulations);

    @Delete
    void delete(Simulation... simulations);
}
