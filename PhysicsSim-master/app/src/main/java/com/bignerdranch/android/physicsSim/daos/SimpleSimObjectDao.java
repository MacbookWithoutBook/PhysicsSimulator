package com.bignerdranch.android.physicsSim.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bignerdranch.android.physicsSim.entities.SimpleSimObject;

@Dao
public interface SimpleSimObjectDao {
    // Assume that simulations only have one simpleSimObject.
    @Query("SELECT rowid, simid, mass, posX, force, time FROM simpleSimObject WHERE simid = CAST(:simid AS INTEGER) LIMIT 1")
    LiveData<SimpleSimObject> getSimpleSimObjectBySimulation(int simid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SimpleSimObject simpleSimObject);

    @Update
    void update(SimpleSimObject... simpleSimObjects);

    @Delete
    void delete(SimpleSimObject... simpleSimObjects);
}
