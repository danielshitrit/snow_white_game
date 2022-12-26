package com.example.hw1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw1.Interfaces.StepCallBack;

public class StepDetector {

    private StepCallBack stepCallBack;
    private SensorManager sensorManager;
    private Sensor sensor;

    private long timestemp = 0;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallBack _stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallBack = _stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateStep(x);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    private void calculateStep(float x) {
        if (System.currentTimeMillis() - timestemp > 500) {
            timestemp = System.currentTimeMillis();
            if (x < -6.0) {
                if (stepCallBack != null)
                    stepCallBack.stepRight();
            }
            if (x > 6.0) {
                if (stepCallBack != null)
                    stepCallBack.stepLeft();
            }
        }
    }

    public void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener, sensor);
        }

}
