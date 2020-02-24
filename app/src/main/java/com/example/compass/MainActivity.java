package com.example.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_mDegree;
    ImageView iv_mArrow;

    private static SensorManager mSensorManager;
    private Sensor mSensor;
    private float currentDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_mDegree=findViewById(R.id.tv_degree);
        iv_mArrow=findViewById(R.id.iv_arrow);

        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensor!=null){
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }else{
            Toast.makeText(MainActivity.this,"Sensor Not Supported!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        int degree= Math.round(event.values[0]);
        tv_mDegree.setText(Integer.toString(degree));
        RotateAnimation rotateAnimation = new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        iv_mArrow.setAnimation(rotateAnimation);
        currentDegree=degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }


}
