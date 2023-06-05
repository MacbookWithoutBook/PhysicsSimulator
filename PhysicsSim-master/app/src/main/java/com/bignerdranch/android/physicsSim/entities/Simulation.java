package com.bignerdranch.android.physicsSim.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "simulation", indices = @Index(value = {"uid", "simName"}, unique = true))
public class Simulation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mSimid;

    // Foreign key to UserAccount
    @ColumnInfo(name = "uid")
    public int mUid;

    @ColumnInfo(name = "simName")
    public String mSimName;

    public Simulation(@NonNull int uid, @NonNull String simName) {
        mUid= uid;
        mSimName = simName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simulation that = (Simulation) o;
        return mSimid == that.mSimid;
    }

    @Override
    public int hashCode() { return Objects.hash(mSimid); }

    @NonNull
    @Override
    public String toString() {
        return "Simulation{" +
                "simid=" + mSimid +
                "; uid=" + mUid +
                "; simName='" + mSimName + '\'' +
                '}';
    }
}
