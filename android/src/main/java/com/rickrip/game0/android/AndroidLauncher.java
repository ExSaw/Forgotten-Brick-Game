package com.rickrip.game0.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rickrip.game0.GDX_ForgottenBrickGame;
import com.rickrip.game0.Main;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true; // Recommended, but not required.
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
     //   config.hideStatusBar = true; // TODO: STATUS BAR
        //config.maxSimultaneousSounds = 128;
        initialize(new GDX_ForgottenBrickGame(), config);
    }
}
