package com.bignerdranch.android.physicsSim.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Fts4
@Entity(tableName = "objectForce")
public class ObjectForce {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mForceid;

    // Foreign key to SimObject
    @ColumnInfo(name = "objid")
    public int mObjid;

    @ColumnInfo(name = "label")
    public String mLabel;

    @ColumnInfo(name = "forceX")
    public int mForceX;

    @ColumnInfo(name = "forceY")
    public int mForceY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectForce that = (ObjectForce) o;
        return mForceid == that.mForceid;
    }

    @Override
    public int hashCode() { return Objects.hash(mForceid); }

    @NonNull
    @Override
    public String toString() {
        return "ObjectForce{" +
                "forceid =" + mForceid +
                "; objid" + mObjid +
                "; label='" + mLabel + '\'' +
                "; forceX=" + mForceX +
                "; forceY=" + mForceY +
                '}';
    }
}
