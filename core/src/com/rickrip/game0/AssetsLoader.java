package com.rickrip.game0;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import static com.badlogic.gdx.Application.ApplicationType.Android;

public class AssetsLoader {

    AssetManager assetManager;
    String pathPrefix;
    float progress=0.0f;
    boolean bool_loadingTextures=false,bool_loadingSounds=false,bool_loadingMusic=false;


    public AssetsLoader(){

        if (Gdx.app.getType() == Application.ApplicationType.Desktop){pathPrefix="android/assets/";}//remove this path, when compiling final desktop version
        if (Gdx.app.getType() == Android){pathPrefix="";}
        assetManager = new AssetManager();

    }

    public void loadStartupLogo(){
        Gdx.app.log("","Start Loading Logo... ");
        progress=0.0f;
        assetManager.load("VitekLoadingBackground.png",Texture.class);
        assetManager.load("Logo0Atlas.png",Texture.class);
        assetManager.load("sfx_onStart_0.mp3",Sound.class);
        assetManager.load("sfx_onStart_1.mp3",Sound.class);
    }

    public void loadSounds(){
        Gdx.app.log("","Start Loading Sounds... ");
        progress=0.0f;
        assetManager.load("sfx_collapse_0.mp3", Sound.class);
        assetManager.load("sfx_collapseBonus_0.mp3", Sound.class);
        assetManager.load("sfx_collapseBonus_1.mp3", Sound.class);
        assetManager.load("sfx_collapseBonus_2.wav", Sound.class);
        assetManager.load("sfx_collapseBonus_3.mp3", Sound.class);
        assetManager.load("sfx_collapseBonus_4.ogg", Sound.class);
        assetManager.load("sfx_gameOver_0.mp3", Sound.class);
        assetManager.load("sfx_gameOver_1.mp3", Sound.class);
        assetManager.load("sfx_gameStart_0.wav", Sound.class);
        assetManager.load("sfx_landed_0.mp3", Sound.class);
        assetManager.load("sfx_landed_1.mp3", Sound.class);
        assetManager.load("sfx_landed_2.mp3", Sound.class);
        assetManager.load("sfx_landed_3.mp3", Sound.class);
        assetManager.load("sfx_landed_4.mp3", Sound.class);
        assetManager.load("sfx_menuButton_0.mp3", Sound.class);
        assetManager.load("sfx_move_0.wav", Sound.class);
        assetManager.load("sfx_move_1.wav", Sound.class);
        assetManager.load("sfx_move_2.wav", Sound.class);
        assetManager.load("sfx_rockLanded_0.wav", Sound.class);
        assetManager.load("sfx_rockLanded_1.wav", Sound.class);
        assetManager.load("sfx_rockLanded_2.wav", Sound.class);
        assetManager.load("sfx_shuffle_0.mp3", Sound.class);
        assetManager.load("sfx_tick_0.mp3", Sound.class);
        assetManager.load("sfx_timer_0.ogg", Sound.class);
        assetManager.load("sfx_timerAfter_0.wav", Sound.class);
        assetManager.load("sfx_viktorSuperBonusStart.mp3", Sound.class);
        assetManager.load("sfx_warning_0.wav", Sound.class);
        assetManager.load("sfx_paintBomb_0.mp3", Sound.class);
        assetManager.load("sfx_laserCut_0.mp3", Sound.class);
        assetManager.load("sfx_lifeGet_0.mp3",Sound.class);
        assetManager.load("sfx_lifeRegen_0.mp3",Sound.class);
        assetManager.load("sfx_lifeDied_0.mp3",Sound.class);
        assetManager.load("sfx_applyingLife_0.mp3",Sound.class);
        assetManager.load("sfx_switch_0.mp3",Sound.class);
        assetManager.load("sfx_holyChoir_0.mp3",Sound.class);
        assetManager.load("sfx_smash_0.mp3",Sound.class);
        assetManager.load("sfx_switch_1.mp3",Sound.class);
        for(int i=0;i<9;i++){assetManager.load("combo_sounds/sfx_comboTimes_"+(i+2)+".ogg",Sound.class);}
/*
        while(!assetManager.update()){
            progress = assetManager.getProgress();
           // Gdx.app.log("","Loading Sounds... " + progress * 100 + "%");
        }*/
    }

    public void loadMusic(){
        Gdx.app.log("","Start Loading Music... ");
        progress=0.0f;
        assetManager.load("music_highScoreBeaten.mp3", Music.class);
        assetManager.load("music_menu_0.mp3", Music.class);
        assetManager.load("music_menu_1.mp3", Music.class);
        assetManager.load("music_menu_2.mp3", Music.class);
        assetManager.load("music_theme_0.ogg", Music.class);
        assetManager.load("music_theme_1.ogg", Music.class);
        assetManager.load("music_theme_2.mp3", Music.class);
/*
        while(!assetManager.update()){
            progress = assetManager.getProgress();
            // Gdx.app.log("","Loading Sounds... " + progress * 100 + "%");
        }*/

    }
    public void loadTextures(){
        Gdx.app.log("","Start Loading Textures... ");
        progress=0.0f;
        assetManager.load("MainField.png", Texture.class);
        assetManager.load("NextBrickField.png", Texture.class);
        assetManager.load("ButtonsAtlas.png", Texture.class);
        assetManager.load("ButtonsGlowAtlas.png", Texture.class);
        assetManager.load("BricksAtlas.png", Texture.class);
        assetManager.load("MenuBackground.png", Texture.class);
        assetManager.load("MenuButtonsAtlas_ENG.png", Texture.class);
        assetManager.load("MenuButtonsAtlas_RUS.png", Texture.class);
        assetManager.load("MenuLabelsAtlas_ENG.png", Texture.class);
        assetManager.load("MenuLabelsAtlas_RUS.png", Texture.class);
        assetManager.load("ScoreField_ENG.png", Texture.class);
        assetManager.load("ScoreField_RUS.png", Texture.class);
        assetManager.load("NumbersAtlas.png", Texture.class);
        assetManager.load("MainBackground.png", Texture.class);
        assetManager.load("MainBackgroundDisplays.png", Texture.class);
        assetManager.load("HiddenBricksField.png", Texture.class);
        assetManager.load("HiddenBrick.png", Texture.class);
        assetManager.load("TimerLight.png", Texture.class);
        assetManager.load("AfterExplosion.png", Texture.class);
        assetManager.load("AfterExplosionLight.png", Texture.class);
        assetManager.load("WarningIcon.png", Texture.class);
        assetManager.load("SwitchLight.png", Texture.class);
        assetManager.load("FlagsAtlas.png", Texture.class);
        assetManager.load("ProfileNumbersAtlas.png",Texture.class);
      //  assetManager.load("VitekLoadingBackground.png",Texture.class);
        assetManager.load("AlphabetAtlas_ENG_RUS.png",Texture.class);
        assetManager.load("ParticleMask.png", Texture.class);
        assetManager.load("LightningBoltAtlas.png",Texture.class);
        assetManager.load("LightningBoltLight.png",Texture.class);
        assetManager.load("DisplayForShader_1.png",Texture.class);
        assetManager.load("DisplayForShader_2.png",Texture.class);
        assetManager.load("DisplayForShader_3.png",Texture.class);
        assetManager.load("ShatteredGlassAtlas.png",Texture.class);
        assetManager.load("ShatteredGlassAfterExplosion_1.png",Texture.class);
        assetManager.load("ShadersTest.png",Texture.class);
        assetManager.load("PaintBombAtlas.png",Texture.class);
        assetManager.load("BricksAtlas2X2.png",Texture.class);
        assetManager.load("LaserCutter2X2.png",Texture.class);
        assetManager.load("BricksAtlas3X3.png",Texture.class);
        assetManager.load("LaserCutter3X3.png",Texture.class);
        assetManager.load("SideBorderL.png",Texture.class);
        assetManager.load("Light_0.png", Texture.class);
        assetManager.load("BigHeart_Atlas.png",Texture.class);
        assetManager.load("HelpBookAtlas_RUS.png",Texture.class);
        assetManager.load("HelpBookAtlas_ENG.png",Texture.class);
        assetManager.load("GameNameAtlas.png",Texture.class);
        if(GDX_ForgottenBrickGame.bool_testLoader){
            assetManager.load("TestField.png", Pixmap.class);}

        assetManager.load("Blood.png",Texture.class);
        assetManager.load("particles/particle0.prc", ParticleEffect.class);//bomb explosion
        assetManager.load("particles/particle1.prc",ParticleEffect.class);//remover
        assetManager.load("particles/particle3.prc",ParticleEffect.class);//remover
        assetManager.load("particles/particle4.prc",ParticleEffect.class);//bombFuse
        assetManager.load("particles/particle4copy.prc",ParticleEffect.class);//bombFuse Copy
        assetManager.load("particles/particle5.prc",ParticleEffect.class);//starEmission
        assetManager.load("particles/particle5copy.prc",ParticleEffect.class);//starEmission Copy
        assetManager.load("particles/particle6.prc",ParticleEffect.class);//viktorBombHalo
        assetManager.load("particles/particle6copy.prc",ParticleEffect.class);//viktorBombHalo Copy
        assetManager.load("particles/particle7.prc",ParticleEffect.class);//lightningBrickStasis
        assetManager.load("particles/particle7copy.prc",ParticleEffect.class);//lightningBrickStasis Copy
        assetManager.load("particles/particle8.prc",ParticleEffect.class);//remover
        assetManager.load("particles/particle9.prc",ParticleEffect.class);//virusEmission
        assetManager.load("particles/particle9copy.prc",ParticleEffect.class);//virusEmission Copy
        assetManager.load("particles/particle10.prc",ParticleEffect.class);//remover virus
        assetManager.load("particles/particle11.prc",ParticleEffect.class);//paint bomb explosion
        assetManager.load("particles/particle12.prc",ParticleEffect.class);//paint bomb drop
        assetManager.load("particles/particle12copy.prc",ParticleEffect.class);//paint bomb drop copy
        assetManager.load("particles/particle13.prc",ParticleEffect.class);//magic life brick damage regeneration
        assetManager.load("particles/particle14.prc",ParticleEffect.class);//magic life brick destroyed
        assetManager.load("particles/particle16_left.prc",ParticleEffect.class);//Big Heart Left Blood
        assetManager.load("particles/particle16_right.prc",ParticleEffect.class);//Big Heart Right Blood
/*
        while(!assetManager.update()){
            progress = assetManager.getProgress();
          //   Gdx.app.log("","Loading Pictures... " + progress * 100 + "%");
        }*/

    }
    public void dispose(){
        assetManager.dispose();
    }


}
