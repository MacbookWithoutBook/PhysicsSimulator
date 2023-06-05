package com.bignerdranch.android.physicsSim.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Fts4
@Entity(tableName = "simObject")
public class SimObject {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mObjid;

    // Foreign key to Simulation
    @ColumnInfo(name = "simid")
    public int mSimid;

    @ColumnInfo(name = "objType")
    public ObjectType mObjType;

    @ColumnInfo(name = "mass")
    public int mMass;

    @ColumnInfo(name = "posX")
    public int mPosX;

    @ColumnInfo(name = "posY")
    public int mPosY;

    public SimObject(@NonNull int simid, @NonNull int mass, @NonNull int posX, @NonNull int posY) {
        mSimid = simid;
        mMass = mass;
        mPosX = posX;
        mPosY = posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimObject that = (SimObject) o;
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
                "; objType=" + mObjType +
                "; mass=" + mMass +
                "; posX=" + mPosX +
                "; posY=" + mPosY +
                '}';
    }
}
