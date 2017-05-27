package de.waishon.kstlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import de.waishon.kstlogin.appintro.AppIntroASpiceFragment;
import de.waishon.kstlogin.appintro.AppIntroCustomBaseFragment;
import de.waishon.kstlogin.appintro.AppIntroKeyBoardFragment;

/**
 * Created by Sören on 10.03.2017.
 */

public class IntroActivity extends AppIntro {

    // UI
    private int backgroundColor = 0;

    // Fragments
    private AppIntroKeyBoardFragment appIntroKeyBoardFragment;

    // System
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.backgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);

        // System
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Erste Seite hinzufpügen
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.app_name),
                getResources().getString(R.string.activity_appintro_welcome_text),
                0,
                backgroundColor));

        // ASpice Slide hinzufügen
        //addSlide(AppIntroCustomBaseFragment.newInstance(AppIntroASpiceFragment.class, backgroundColor));

        // Keyboard Slide hinzufügen
        appIntroKeyBoardFragment = AppIntroCustomBaseFragment.newInstance(AppIntroKeyBoardFragment.class, backgroundColor);
        addSlide(appIntroKeyBoardFragment);

        // Ende Slide hinzufügen
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.appintro_fragment_finish_title),
                getString(R.string.appintro_fragment_finish_description),
                0,
                backgroundColor));

        // Keinen Skip Button anzeigen
        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        this.sharedPreferences.edit().putBoolean(MainActivity.PREF_FIRST_RUN, false).commit();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(appIntroKeyBoardFragment != null) {
            appIntroKeyBoardFragment.onFocusChanged();
        }
    }
}
