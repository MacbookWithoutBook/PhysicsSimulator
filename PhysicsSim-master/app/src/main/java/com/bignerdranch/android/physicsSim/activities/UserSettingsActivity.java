package com.bignerdranch.android.physicsSim.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.physicsSim.fragments.UserSettingsFragment;

public class UserSettingsActivity extends SingleFragmentActivity implements SensorEventListener {
    private final String TAG = getClass().getSimpleName();
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    private static final float SENSOR_TIME_INTERVAL = 100;
    private static final float SPEED_THRESHOLD = 800;
    private float[] accelValues = new float[3];
    private float[] prevAccelValues = new float[3];
    private long lastUpdateTime;

    @Override
    protected Fragment createFragment() { return new UserSettingsFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdateTime > SENSOR_TIME_INTERVAL) {
            long timeDiff = currentTime - lastUpdateTime;

            accelValues[0] = sensorEvent.values[0];
            accelValues[1] = sensorEvent.values[1];
            accelValues[2] = sensorEvent.values[2];
            float accelSum = accelValues[0] + accelValues[1] + accelValues[2];
            float prevAccelSum = prevAccelValues[0] + prevAccelValues[1] + prevAccelValues[2];

            // Calculate the rate of change of device acceleration to detect shaking.
            float speed = 10000 * Math.abs(accelSum - prevAccelSum) / timeDiff;

            // Close out of user settings if phone shake detected.
            if (speed > SPEED_THRESHOLD) {
                Log.d(TAG, "Shake detected with speed: " + speed);
                Toast.makeText(this, "You shook your phone. Exiting settings...", Toast.LENGTH_SHORT).show();
                finish();
            }
            prevAccelValues[0] = accelValues[0];
            prevAccelValues[1] = accelValues[1];
            prevAccelValues[2] = accelValues[2];

            lastUpdateTime = currentTime;
        }
    }
}