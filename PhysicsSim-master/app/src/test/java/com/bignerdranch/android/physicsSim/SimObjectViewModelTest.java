package com.bignerdranch.android.physicsSim;

import static org.junit.Assert.*;

import com.bignerdranch.android.physicsSim.viewmodels.SimObjectViewModel;

import org.junit.Test;

public class SimObjectViewModelTest {

    @Test
    public void calculateDestinationX() {
        float force = 50f;
        float mass = 50f;
        float time = 1f;

        float destinationX = SimObjectViewModel.calculateDestinationX(force, mass, time);

        assertEquals(0.5f, destinationX, 0.01f);
    }

    @Test
    public void calculateDestinationXExtraForce() {
        float force = 500f;
        float mass = 50f;
        float time = 1f;

        float destinationX = SimObjectViewModel.calculateDestinationX(force, mass, time);

        assertEquals(5.0f, destinationX, 0.01f);
    }

    @Test
    public void calculateDestinationXSmallMass() {
        float force = 50f;
        float mass = 0.01f;
        float time = 1f;

        float destinationX = SimObjectViewModel.calculateDestinationX(force, mass, time);

        assertEquals(2500f, destinationX, 0.01f);
    }

    @Test
    public void calculateDestinationXSmallTime() {
        float force = 50f;
        float mass = 50f;
        float time = 0.00001f;

        float destinationX = SimObjectViewModel.calculateDestinationX(force, mass, time);

        assertEquals(0.00000000001f, destinationX, 0.00000000005f);
    }

    @Test
    public void calculateDestinationXSmallDisplacement() {
        float force = 0.01f;
        float mass = 1000f;
        float time = 1f;

        float destinationX = SimObjectViewModel.calculateDestinationX(force, mass, time);

        assertEquals(0.00005f, destinationX, 0.00005f);
    }
}