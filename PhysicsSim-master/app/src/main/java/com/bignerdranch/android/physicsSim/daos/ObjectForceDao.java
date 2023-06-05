package com.bignerdranch.android.physicsSim.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bignerdranch.android.physicsSim.entities.ObjectForce;
import com.bignerdranch.android.physicsSim.entities.SimObject;

import java.util.List;

@Dao
public interface ObjectForceDao {
    @Query("SELECT rowid, objid, label, forceX, forceY FROM objectForce WHERE objid = CAST(:objid AS INTEGER)")
    LiveData<List<ObjectForce>> getSimulationObjects(int objid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ObjectForce objectForce);

    @Update
    void update(ObjectForce... objectForces);

    @Delete
    void delete(ObjectForce... objectForces);
}
