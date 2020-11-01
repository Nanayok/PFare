package com.pfare.pfarepassenger.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pfare.pfarepassenger.R;

import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {

//    /** Duration of wait **/
    private static int SPLASH_DISPLAY_LENGTH = 8000;
//
    private ParticleView mParticleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mParticleView = (ParticleView) findViewById(R.id.particle_logo);

        mParticleView.startAnim();

        SystemClock.sleep(200); //ms


        SystemClock.sleep(200); //ms

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(i);

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
//                Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
//                startActivity(i);

                // makeCircularRevealAnimation(relativeLayout);

//                session = new SessionManager(getApplicationContext());

                //Session class instance
//                session = new SessionManager(getApplicationContext());
////
//                HashMap<String,String> userDetails = session.getUserDetails();
//                firstName = userDetails.get(SessionManager.KEY_FIRST_NAME);
//
//                HashMap<String,String> vehicleDetails = session.getVehicleSession();
//                vehiclePlate = vehicleDetails.get(SessionManager.KEY_VEHICLE_PLATE);
//
//                if(zip != null){
//
//                if(firstName != null){
//                    Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
//                    startActivity(i);
//                }else{
//                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(i);
//                }
//
//                }else{
////                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
////                    startActivity(i);
//
//                    Intent i = new Intent(SplashActivity.this, SignInActivity.class);
//                    startActivity(i);
//                    overridePendingTransition(R.anim.in, R.anim.out);
//
//                    //startActivity(intent);
////                    overridePendingTransition(R.anim.opening_anim, R.anim.closing_anim);
//                    //overridePendingTransition(R.anim.opening_anim, R.anim.closing_anim);
//                }

                // close this activity

                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
