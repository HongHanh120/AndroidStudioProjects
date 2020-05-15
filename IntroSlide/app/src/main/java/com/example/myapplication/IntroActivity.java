package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button buttonNext;
    int position = 0;
    Button buttonGetStarted;
    Animation buttonAnimation;
    TextView tvSkip;
//    Button buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //when this activity is about to be launch
//        if(restorePrefData()){
//            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(mainActivity);
//            finish();
//        }

        setContentView(R.layout.activity_intro);

        //hide the action bar
        getSupportActionBar().hide();

        //Init views
        buttonNext = findViewById(R.id.button_next);
        buttonGetStarted = findViewById(R.id.button_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        buttonAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = (TextView) findViewById(R.id.tv_skip);

        //List screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Fresh Food", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", R.drawable.image1));
        mList.add(new ScreenItem("Fast Delivery", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", R.drawable.image2));
        mList.add(new ScreenItem("Easy Payment", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", R.drawable.image3));

        // setup view pager
        screenPager =findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //next button click listener
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if(position < mList.size() ){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position == mList.size() - 1){
                    //Show Get started button and hide all the indicator and the next button
                    LoadLastScreen();
                }
            }
        });

        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size() - 1){
                    LoadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//      Skip button click listener
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                screenPager.setCurrentItem(mList.size());
                LoadLastScreen();
            }
        });

        //Get started button click listener
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", true);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    private void LoadLastScreen() {
        buttonNext.setVisibility(View.INVISIBLE);
        buttonGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        //Add an animation the get started button
        //setup animation
        buttonGetStarted.setAnimation(buttonAnimation);
    }
}
