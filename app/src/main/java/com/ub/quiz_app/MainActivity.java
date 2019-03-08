package com.ub.quiz_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Content_frag.OnFragmentInteractionListener
{
    ImageView img;
    Timer timer;
    Content_frag CF;
    FragmentManager FM;
    FragmentTransaction FT;
    int onStartCount = 0;
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView)findViewById(R.id.my_image);
        Animation animation1=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        img.startAnimation(animation1);
        timer=new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                CF=new Content_frag();
                FM = getSupportFragmentManager();
                FT = FM.beginTransaction();
                FT.setCustomAnimations(R.anim.enter,R.anim.exit);
                //overridePendingTransition(R.anim.enter,R.anim.exit);
                FT.replace(R.id.container,CF);
                FT.addToBackStack(null);
                FT.commit();
                img.setVisibility(View.INVISIBLE);
            }
        },4000);

    }


    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.container);
        fragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed()
    {
        this.finish();
    }
}
