package com.ajit.whatsappdirect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class splashScreen extends AppCompatActivity {

    TextView tv1 ;
    ImageView img1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splash);
        this.tv1 = (TextView) findViewById(R.id.splashtext1);
        this.img1 = (ImageView) findViewById(R.id.splashimg1);
        this.tv1.setAnimation(AnimationUtils.loadAnimation(this, R.anim.downtoupanimation));
        this.img1.setAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
    }
