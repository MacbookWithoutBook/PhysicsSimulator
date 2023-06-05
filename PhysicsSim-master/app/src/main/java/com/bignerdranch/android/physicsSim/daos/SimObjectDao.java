package com.bignerdranch.android.physicsSim.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bignerdranch.android.physicsSim.entities.SimObject;
import com.bignerdranch.android.physicsSim.entities.Simulation;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.util.List;

@Dao
public interface SimObjectDao {
    @Query("SELECT rowid, simid, objType, mass, posX, posY FROM simObject WHERE simid = CAST(:simid AS INTEGER)")
    LiveData<List<SimObject>> getSimulationObjects(int simid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SimObject simObject);

    @Update
    void update(SimObject... simObjects);

    @Delete
    void delete(SimObject... simObjects);
}
