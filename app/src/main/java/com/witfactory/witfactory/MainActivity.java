package com.witfactory.witfactory;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.witfactory.witfactory.data.classModel.Utils;
import com.witfactory.witfactory.databinding.ActivityMainBinding;
import com.witfactory.witfactory.esp32.activities.EspMainActivity;
import com.witfactory.witfactory.model.LocaleHelper;

public class MainActivity extends Activity  {

    private ActivityMainBinding binding;
    private boolean isFirstAnimation = false;
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    Context context;
    Resources resources;
    String locale = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        darkBlackEnable();

        locale = getPersistedLocale(getApplicationContext());

        //lenguage
        if (locale.equals(null) || locale.equals("")) {
            locale = "en";
        }

        context = LocaleHelper.setLocale(MainActivity.this, locale);
        resources = context.getResources();
        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);

        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isFirstAnimation) {
                    binding.imgLogo.clearAnimation();


                }

                isFirstAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.imgLogo.clearAnimation();
                binding.imgLogo.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.imgLogo.startAnimation(hold);


        validateInternet();


    }

    public static String getPersistedLocale(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("language", "");
    }

    public void darkBlackEnable() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                /* si esta activo el modo oscuro lo desactiva */
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }


    public void validateInternet() {
        if (!Utils.internetstaus(getApplicationContext())) {

            Utils.aletSinInternet(this);
        } else {
            //amplifyCognito.validarAuth();
            Intent intent = new Intent(this, EspMainActivity.class);
            startActivity(intent);
        }
    }
}