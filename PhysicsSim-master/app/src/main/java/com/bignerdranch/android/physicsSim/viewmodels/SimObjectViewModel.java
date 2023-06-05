package com.bignerdranch.android.physicsSim.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bignerdranch.android.physicsSim.AppRepository;
import com.bignerdranch.android.physicsSim.entities.SimpleSimObject;

public class SimObjectViewModel extends AndroidViewModel {
    private final AppRepository mRepository;

    private LiveData<SimpleSimObject> mSimulationSimpleSimObject;

    public SimObjectViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application);
        mSimulationSimpleSimObject = mRepository.getCurrentSimulationSimpleObject();
    }

    public LiveData<SimpleSimObject> getSimulationSimpleObject() { return mRepository.getCurrentSimulationSimpleObject(); }

    public void insert(SimpleSimObject simpleSimObject) {
        mRepository.insertSimpleSimObject(simpleSimObject);
        mSimulationSimpleSimObject = mRepository.getCurrentSimulationSimpleObject();
    }

    public void update(SimpleSimObject simpleSimObject) {
        mRepository.updateSimpleSimObject(simpleSimObject);
        mSimulationSimpleSimObject = mRepository.getCurrentSimulationSimpleObject();
    }

    public void delete(SimpleSimObject simpleSimObject) {
        mRepository.deleteSimpleSimObject(simpleSimObject);
    }


    public float calculateDestinationX(SimpleSimObject simObject) {
        float result = 0.0f;

        if (simObject != null) {
            result = calculateDestinationX(simObject.mForce, simObject.mMass, simObject.mTime);
        }
        return result;
    }

    public static float calculateDestinationX(float force, float mass, float time) {
        float acceleration = force / mass;
        return 0.5f * acceleration * (time * time);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}