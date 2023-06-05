package com.bignerdranch.android.physicsSim.viewmodels;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bignerdranch.android.physicsSim.AppRepository;
import com.bignerdranch.android.physicsSim.entities.Simulation;

import java.util.List;
import java.util.Objects;

public class SimulationViewModel extends AndroidViewModel {
    private final AppRepository mRepository;

    private LiveData<List<Simulation>> mUserSimulations;

    public SimulationViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application);
        mUserSimulations = mRepository.getCurrentUserSimulations();
    }

    public int getUid() { return mRepository.getCurrentUid(); }

    public LiveData<List<Simulation>> getUserSimulations() { return mRepository.getCurrentUserSimulations(); }

    public boolean containsSimulation(Simulation simulation) {
        LiveData<Simulation> simulationLiveData = mRepository.findSimulationByUidAndName(simulation);
        Simulation theSimulation = simulationLiveData.getValue();

        if (simulation == null || theSimulation == null)
            return false;

        return theSimulation.mUid == simulation.mUid &&
                theSimulation.mSimName.equals(simulation.mSimName);
    }

    public boolean currentUserHasSimulation(String simname) {
        return mRepository.currentUserHasSimulation(simname);
    }

    public long insert(Simulation simulation) {
        long result = mRepository.insertSimulation(simulation);
        mUserSimulations = mRepository.getCurrentUserSimulations();
        return result;
    }

    public void deleteSimulation(Simulation simulation) {
        mRepository.deleteSimulation(simulation);
    }

    public void setSimid(int simid) {
        mRepository.setCurrentSimid(simid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}