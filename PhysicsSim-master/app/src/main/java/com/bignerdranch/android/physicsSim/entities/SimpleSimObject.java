package com.bignerdranch.android.physicsSim.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "simpleSimObject", indices = @Index(value = "simid", unique = true))
public class SimpleSimObject {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mObjid;

    // Foreign key to Simulation
    @ColumnInfo(name = "simid")
    public int mSimid;

    @ColumnInfo(name = "mass")
    public float mMass;

    @ColumnInfo(name = "posX")
    public float mPosX;

    @ColumnInfo(name = "force")
    public float mForce;

    @ColumnInfo(name = "time")
    public float mTime;


    public SimpleSimObject(@NonNull int simid, @NonNull float mass, @NonNull float posX,
                           @NonNull float force, @NonNull float time) {
        mSimid = simid;
        mMass  = mass;
        mPosX  = posX;
        mForce = force;
        mTime  = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleSimObject that = (SimpleSimObject) o;
        return mObjid == that.mObjid;
    }

    @Override
    public int hashCode() { return Objects.hash(mObjid); }

    @NonNull
    @Override
    public String toString() {
        return "SimObject{" +
                "objid=" + mObjid +
                "; simid=" + mSimid +
                "; mass=" + mMass +
                "; posX=" + mPosX +
                "; force=" + mForce +
                "; time=" + mTime +
                '}';
    }
}
