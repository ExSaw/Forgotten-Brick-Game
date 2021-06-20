 package com.rickrip.game0;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.badlogic.gdx.Application.ApplicationType.Android;


public class GDX_ForgottenBrickGame extends ApplicationAdapter implements GestureDetector.GestureListener,InputProcessor,ApplicationListener {

    AssetsLoader myAssetsLoader;
    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    ShaderProgram shaderProgramDefault,shaderProgramInvert, shaderProgramVignette, shaderProgramRainbowWave, shaderProgramTest,
            shaderProgramRainbow, shaderProgramNoise_1, shaderProgramNoise_2, shaderProgramCrop, shaderProgramRGB2HSV;
    ParticleEffect prc_explosion_0, prc_remover_0, prc_remover_1, prc_remover_2, prc_remover_3,
            prc_bombFuse, prc_bombFuseNextField, prc_starEmission, prc_starEmissionNextField, prc_lightningBrickStasis, prc_lightningBrickStasisNextField,
            prc_virusEmission, prc_virusEmissionNextField, prc_paintBombSpray,prc_viktorBombHalo,prc_viktorBombHaloNextField,prc_paintBombDrop,prc_paintBombDropNextFiled,
            prc_lifeBrickRegen,prc_lifeBrickDestroyed,prc_bigHeartBlood_left,prc_bigHeartBlood_right;
    ParticleEffectPool particleEffectPool_0,particleEffectPool_1,particleEffectPool_2,particleEffectPool_3,particleEffectPool_4,particleEffectPool_5;
    ParticleEffectPool.PooledEffect pooledEffect_0,pooledEffect_1,pooledEffect_2,pooledEffect_3,pooledEffect_4,pooledEffect_5;
    Array<ParticleEffectPool.PooledEffect> array_prc_effects_0, array_prc_effects_1,array_prc_effects_2,array_prc_effects_3,array_prc_effects_4,array_prc_effects_5;
    Array<Color> array_prc_removeEffects_0,array_prc_removeEffects_1,array_prc_removeEffects_2,array_prc_removeEffects_3;
    Animation<TextureRegion> animation_lightningBolt,animation_positiveBrickFallingSpeedDown,animation_negativeBrickFallingSpeedUp,animation_neutral3XStonesTo4XStones,
            animation_positiveTimerSpeedDown,animation_negativeTimerSpeedUp,animation_magicPlusLife, animation_GameName;
    Texture tex_MainField,tex_NextBrickField,tex_BricksAtlas,tex_ButtonsAtlas,tex_ButtonsGlowAtlas,tex_MenuBackground,tex_MenuButtonsAtlas,tex_MenuLabelsAtlas,
            tex_ScoreField,tex_NumbersAtlas,tex_MainBackground, tex_MainBackgroundDisplays,tex_HiddenBricksField,tex_HiddenBrick,tex_TimerLight,tex_AfterExplosion,
            tex_AfterExplosionLight, tex_WarningIcon,tex_SwitchLight, tex_FlagsAtlas, tex_ProfileNumbersAtlas, tex_OnStartLogoVitek, tex_ParticleMask, tex_BloodBackground,
            tex_LightningBoltAtlas, tex_LightningBoltLight, tex_ShatteredGlassAtlas, tex_ShatteredGlassAfterExplosion_1, tex_PaintBombAtlas, tex_BricksAtlas2X2,
            tex_LaserCutter2X2, tex_BricksAtlas3X3, tex_LaserCutter3X3, tex_SideBorderLight, tex_OnStartLogo0, tex_Light_0, tex_Help, tex_GameNameAtlas;
    Texture tex_DisplayForShader_1,tex_DisplayForShader_2,tex_DisplayForShader_3, tex_ShadersTest, tex_BigHeartAtlas;
    TextureRegion[] texRegion_Bricks,texRegion_Buttons,texRegion_ButtonsGlow,texRegion_MenuButtons,texRegion_MenuLabels,texRegion_NumbersAtlas, texRegion_FlagsAtlas,
                    texRegion_ProfileNumbersAtlas, texRegion_LightningBolt, texRegion_ShatteredGlass, textRegion_PaintBomb, texRegion_BricksAtlas2X2, texRegion_LaserCutter2X2,
            texRegion_BricksAtlas3X3, texRegion_LaserCutter3X3, textureRegion_OnStartLogo0, textureRegion_BigHeart, textureRegion_Help;
    TextureRegion[] textureRegion_positiveBrickFallingSpeedDown,textureRegion_negativeBrickFallingSpeedUp,textureRegion_neutral3XStonesTo4XStones,
            textureRegion_positiveTimerSpeedDown, textureRegion_negativeTimerSpeedUp,textureRegion_magicPlusLife,textureRegion_GameName;
    int[][] int_bricksArray2D,int_nextBricksArray2D,int_beforeShuffle;
    int[] int_shuffleOrder;
    int int_numOfBricks=0,int_brickStone=0,int_noPhysics=0, int_curScore=0, int_highScore=0,int_comboTimesMultiplier=0, int_scoreAdded=0, int_scoreAddedRendering=0,
            int_buttonsGlowChance=300,int_comboTimesMultiplierForRender=0, int_profileNumber=1, int_language=0, int_rainbowBrickChance=18, int_lightningRow=-1,
            int_time=0, int_paintBombColor=0,int_paintBombNextColor=0, int_brick2X2stage=0, int_brick3X3stage=0,int_specialBricksChance=10, int_numOfLives=0,
            int_lifeScore=0, int_lifeBrickDamage=2, int_numOfRemovedBricksByLifeBonus=0,int_numOfAllBricksForLifeBonus=0;
    int int_bricksCount=0; //число разных бриков в атласе, вычисляется на стадии создания спрайта бриков
    final int int_const_stoneBrickNumber=7;//отсчет от 1, номер брика камня опорный --- до него обычные брики --- #12 rainbow ---- остальные бонусные
    // int_const_stoneBrickNumber+7 - paint bomb
    float  float_bonusChance=1.0f, float_LightningBoltAnimTime=0.0f, float_LightingBoltAlpha=1.0f, float_BloodBackgroundAlphaFresh=0.0f,float_deltaTimer=0.0f,
        float_varForHiddenTimer=0.0f, float_time=0.0f, float_gameOverShaderAlpha=1.0f, float_saturation=0.0f, float_lifeBrickSaturation=0.0f, float_lifeApplyingPower=0.35f,
            float_touch_accel=0.0f;
    final byte[][] byte_shuffleMask = new byte[][]{
            {1,2,3},
            {9,5,4},
            {8,6,7}
    };
    int[][][] int_fallingBrickGPS,int_fallingBrickGPSghost,int_matchedBricksArray2D;
    Sprite spr_mainField,spr_nextBrickField,spr_MenuBackground,spr_ScoreField,spr_MainBackground,spr_MainMenuBackgroundDisplays,spr_hiddenBricksField,spr_hiddenBrick,
            spr_timerLight,spr_afterExplosion,spr_afterExplosionLight,spr_warningIcon,spr_switchLight, spr_OnStartLogoVitek, spr_ParticleMask, spr_BloodBackground, spr_LightningBolt,
            spr_LightingBoltLight, spr_Light0_0, spr_GameName;
    Sprite spr_positiveBrickFallingSpeedDown, spr_negativeBrickFallingSpeedUp, spr_neutral3XStonesTo4XStones,spr_positiveTimerSpeedDown,spr_negativeTimerSpeedUp,
            spr_magicPlusLife;
    Sprite spr_DisplayForShader_1,spr_DisplayForShader_2,spr_DisplayForShader_3, spr_ShadersTest,spr_ShatteredGlassAfterExplosion_1;
    Sprite[] spr_bricks,spr_buttons,spr_buttonsGlow,spr_menuButtons,spr_menuLabels,spr_numbers,spr_FlagsAtlas,
            spr_ProfileNumbersAtlas, spr_ShatteredGlass, spr_PaintBomb, spr_bricks2X2, spr_LaserCutter2X2, spr_bricks3X3,
            spr_LaserCutter3X3, spr_sideBorderLight,spr_OnStartLogo0,spr_BigHeart,spr_Help;

    int GAME_STATE = -3; /** (-3)-1й раз запуска, (-2)-показ шейдера проигрыша, (-1)-проигрыш, 0-пауза, 1-генерация фигуры, 2-падение, 3-удаление совпавших комбинаций (фигура упала),
    * 4-задержка, 5-расчет "физики" поля, 6-задержка для физизки, 7-применение жизни
     * **/

    //GS_BF - до паузы
    int GAME_STATE_BF=1,GAME_STATE_PDF=0,GAME_STATE_COLLIDE=0,GAME_STATE_GENBONUS=0,GAME_STATE_BONUSTYPE=0;
    int GAME_FIG_GENERATED = 0;//0-пусто,1-след фигура сгенерирована и ожидает;
    int MENU_STATE=0;//0-основной, 1-вопрос подтв.нов.игры, 2-вопрос подтв.выхода, 31-настройки стр1, 32-настройки стр2, 100-help
    int SYSTEM_STATE=0;//0-create, 1-render, 2-resize, 3-pause, 4-resume
    int section=0,int_onStartSFX=0;
    int APPLYING_LIFE_STATE=0;

    //Modules
    Random rand;
    float screenW=0.0f,screenH=0.0f;
    final long velocityTimerIncrementDefault=450,hiddenBricksTimerIncrementDefault=2000;
    long processTimer=0, scoreDisplayTimer=0, velocityTimerIncrement=velocityTimerIncrementDefault, lagTimerIncrement=500,
            scoreDisplayTimerIncrement=3000,hiddenBricksTimer=0,hiddenBricksTimerIncrement=hiddenBricksTimerIncrementDefault,
            afterExplosionTimer=0,brickAnnihilationTimer=0,comboTimesMultiplierRenderTimer=0,comboTimesMultiplierRenderTimerWOW=0,
            downPressedTimer=0, lowFPSTimer=0, soundMuteTimer=0,showLogo0Timer=0,lifeApplyingTimer=0;
    Preferences prefs;
    boolean bool_highScoreBeaten=false,bool_hiddenTimerCondition=false,bool_hiddenTimerSFX=false,bool_ghostEnabled=true, bool_soundEnable=true;
    boolean bool_FreshStart=true, bool_DisplayNoiseShaderEnabled=true,bool_Loading=true,bool_4XStones=false,bool_nextPrizeIsLife=false;
    boolean bool_bombExploded=false, bool_lightningExploded=false,bool_starExploded=false,bool_viktorBombExploded=false,bool_paintBombExploded=false,
            bool_lifeBrickRegen=false;
    int int_ShowLogoStage=0, int_musicNumber=0;
    static boolean bool_testLoader=false;/**TEST_BUILDER**/
    Pixmap pixmap_testLoaderMainField;
    ColorFromInt colorFromInt;
    boolean  bool_ShowLogo=true,bool_ShowVitekLogo=false;/**STARTUP_LOGO**/
    String pathPrefix,str_language;//str_fallingBrickState, str_nextBrickState, str_mainFieldState;
    //SaveEngine saveEngine;
    AlphabetCore alphabetCore;

    //JSON
    /*
    Json json = new Json();
    JsonValue json_mainArray = obj.optJSONArray("integers");*/
    StringBuilder stringBuilder;

    //Sounds
    Sound sfx_collapse_0,sfx_collapseBonus_0,sfx_collapseBonus_1,sfx_collapseBonus_2,sfx_collapseBonus_3,sfx_collapseBonus_4,sfx_tick_0,
            sfx_landed_0,sfx_landed_1,sfx_landed_2,sfx_landed_3,sfx_landed_4,sfx_viktorSuperBonusStart,sfx_move_0,sfx_move_1,sfx_move_2,sfx_shuffle_0,
            sfx_gameOver_0,sfx_gameOver_1, sfx_gameStart_0,sfx_warning_0,sfx_timer_0,sfx_timerAfter_0,sfx_menuButton_0,sfx_paintBomb_0,sfx_laserCut_0,
            sfx_onStart_0,sfx_onStart_1,sfx_lifeGet_0,sfx_lifeRegen_0,sfx_lifeDied_0,sfx_applyingLife_0,sfx_switch_0,sfx_holyChoir_0,sfx_smash_0,sfx_switch_1;
    Sound[] sfx_rockLanded,sfx_comboTimes;
    Music music_theme,music_highScoreBeaten;
    int int_music_menu_index=0;
    float float_volumeIncrement=0.5f, float_brightness =0.0f;
    Music[] music_menu;
    //long id_music,id_sfx;

    float hueAdjustTest=0.0f;
    float[] touchY_HelpField = {0.0f,0.0f};
    int int_helpFieldTouchCounter =0;

    //gestures
    class TouchInfo {//multitouch
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
        public boolean touchedGlobal = false;
    }
    final float HALF_TAP_SQUARE_SIZE = 20.0f;
    final float TAP_COUNT_INTERVAL = 0.4f;
    final float LONG_PRESS_DURATION = 1.1f;
    final float MAX_FLING_DELAY = 0.15f;
    GestureDetector gestureDetector;
    boolean bool_leftPressed=false,bool_rightPressed=false,bool_downPressed=false,bool_shufflePressed=false,bool_pausePressed=false,
            bool_menu_ReturnPressed=false,bool_menu_NewGamePressed=false, bool_menu_SettingsPressed =false,bool_menu_ExitPressed=false,
            bool_menu_YesPressed=false,bool_menu_NoPressed=false,bool_menu_AcceptPressed=false,bool_menu_GhostPressed=false,
            bool_menu_ChangeLanguagePressed=false, bool_menu_ChangeProfileNumbersPressed=false,bool_menu_SoundPressed=false,
            bool_menu_NextPressed=false, bool_helpPressed=false,bool_backPressed=false,bool_helpFieldPressed=false;
    boolean[] bool_buttonPressedGlobal;
    Vector3 touchVecXYZ;
    Vector2 touchDraggedXY;
    int touchX=-1,touchY=-1;
    InputMultiplexer inputMultiplexer;
    private Map<Integer,TouchInfo> touches;//multitouch

    ShapeRenderer shapeRenderer;


    @Override
    public void create () {

        if(section==0){

        SYSTEM_STATE=0;//system on create

        if (Gdx.app.getType() == Application.ApplicationType.Desktop){pathPrefix="android/assets/";}//remove this path, when compiling final desktop version
        if (Gdx.app.getType() == Android){pathPrefix="";}
        //saveEngine = new SaveEngine();

        stringBuilder = new StringBuilder();

        shapeRenderer = new ShapeRenderer();

        Gdx.app.log("'", '\n' + "<<<Loading New Game>>>");

        rand = new Random();

        camera = new OrthographicCamera();
        viewport = new FitViewport(720,1280,camera);
        viewport.apply();

        int_bricksArray2D = new int[6][13];
        int_nextBricksArray2D = new int[3][3];
        int_fallingBrickGPS = new int[3][3][3];//k0 - i, k1 - j, k2 - тип брика
        int_fallingBrickGPSghost = new int[3][3][3];
        int_beforeShuffle= new int[3][3];
        int_shuffleOrder = new int[9];
        int_matchedBricksArray2D = new int[6][13][2];//3D for rainbow bricks detection or other special bricks
        bool_buttonPressedGlobal = new boolean[7];//5-help,6-back
        sfx_rockLanded = new Sound[3];//Шлёп от камня
        sfx_comboTimes = new Sound[9];//Комбо
        music_menu = new Music[3];

        loadProgress(true);

        if(GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3){GAME_STATE=0;}

        processTimer=System.currentTimeMillis();

        shaderProgramInvert = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/invert.vert"),Gdx.files.internal(pathPrefix+"shaders/invert.frag"));

        shaderProgramDefault = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/default.frag"));
        shaderProgramDefault.setUniformf("uniform_float_brightness",1.0f);
        shaderProgramDefault.setUniformf("uniform_vec4_color",Color.WHITE);

        shaderProgramRainbow = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/rainbow.frag"));

        shaderProgramNoise_1 = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/noise_1.frag"));

        shaderProgramNoise_2 = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/noise_2.frag"));

        shaderProgramCrop = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/crop.frag"));
        shaderProgramCrop.setUniformf("uniform_float_brightness",1.0f);
        shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);

        shaderProgramRGB2HSV = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/rgb2hsv.frag"));
/*
        shaderProgramVignette = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/vignette.vert"),Gdx.files.internal(pathPrefix+"shaders/vignette.frag"));
        shaderProgramVignette.setUniformf("resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        shaderProgramRainbowWave = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/rainbow_wave.vert"),Gdx.files.internal(pathPrefix+"shaders/rainbow_wave.frag"));
        shaderProgramRainbowWave.setUniformf("resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        shaderProgramRainbowWave.setUniformf("time",System.currentTimeMillis());
*/
        shaderProgramTest = new ShaderProgram(Gdx.files.internal(pathPrefix+"shaders/default.vert"),Gdx.files.internal(pathPrefix+"shaders/test.frag"));
        //shaderProgramTest.setUniformf("resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //shaderProgramTest.setUniformf("time",System.currentTimeMillis());

        ShaderProgram.pedantic = false;//желательно использовать, тк если мы используем не все юниформы, то шейдер не скомпилируется

        if (!shaderProgramDefault.isCompiled()){throw new GdxRuntimeException("Could not compile default shader: "+ shaderProgramDefault.getLog());
        }else{if(shaderProgramDefault.getLog().length()!=0){System.err.println(shaderProgramDefault.getLog());}}
        if (!shaderProgramInvert.isCompiled()){throw new GdxRuntimeException("Could not compile invert shader: "+ shaderProgramInvert.getLog());
        }else{if(shaderProgramInvert.getLog().length()!=0){System.err.println(shaderProgramInvert.getLog());}}
        if (!shaderProgramRainbow.isCompiled()){throw new GdxRuntimeException("Could not compile Rainbow shader: "+ shaderProgramRainbow.getLog());
        }else{if(shaderProgramRainbow.getLog().length()!=0){System.err.println(shaderProgramRainbow.getLog());}}
        if (!shaderProgramNoise_1.isCompiled()){throw new GdxRuntimeException("Could not compile Noise1 shader: "+ shaderProgramNoise_1.getLog());
        }else{if(shaderProgramNoise_1.getLog().length()!=0){System.err.println(shaderProgramNoise_1.getLog());}}
        if (!shaderProgramNoise_2.isCompiled()){throw new GdxRuntimeException("Could not compile Noise2 shader: "+ shaderProgramNoise_2.getLog());
        }else{if(shaderProgramNoise_2.getLog().length()!=0){System.err.println(shaderProgramNoise_2.getLog());}}
        if (!shaderProgramCrop.isCompiled()){throw new GdxRuntimeException("Could not compile Crop shader: "+ shaderProgramCrop.getLog());
        }else{if(shaderProgramCrop.getLog().length()!=0){System.err.println(shaderProgramCrop.getLog());}}
        if (!shaderProgramRGB2HSV.isCompiled()){throw new GdxRuntimeException("Could not compile rgb2hsv shader: "+ shaderProgramRGB2HSV.getLog());
        }else{if(shaderProgramRGB2HSV.getLog().length()!=0){System.err.println(shaderProgramRGB2HSV.getLog());}}
/*      if (!shaderProgramVignette.isCompiled()){throw new GdxRuntimeException("Could not compile invert shader: "+ shaderProgramVignette.getLog());
        }else{if(shaderProgramVignette.getLog().length()!=0){System.err.println(shaderProgramVignette.getLog());}}
        if (!shaderProgramRainbowWave.isCompiled()){throw new GdxRuntimeException("Could not compile invert shader: "+ shaderProgramRainbowWave.getLog());
        }else{if(shaderProgramRainbowWave.getLog().length()!=0){System.err.println(shaderProgramRainbowWave.getLog());}}
 */
        if (!shaderProgramTest.isCompiled()){throw new GdxRuntimeException("Could not compile invert shader: "+ shaderProgramTest.getLog());
        }else{if(shaderProgramTest.getLog().length()!=0){System.err.println(shaderProgramTest.getLog());}}

        batch = new SpriteBatch();

        myAssetsLoader = new AssetsLoader();

            Gdx.app.log("'", '\n' + "<<<Setup Assets>>>");
            myAssetsLoader = new AssetsLoader();
            myAssetsLoader.loadStartupLogo();
            while(!myAssetsLoader.assetManager.update()){
                myAssetsLoader.progress = myAssetsLoader.assetManager.getProgress();
                Gdx.app.log("","Loading StartupLogo... " + myAssetsLoader.progress * 100 + "%");
            }
            myAssetsLoader.assetManager.finishLoading();
            sfx_onStart_0 = myAssetsLoader.assetManager.get("sfx_onStart_0.mp3");
            sfx_onStart_1 = myAssetsLoader.assetManager.get("sfx_onStart_1.mp3");
            tex_OnStartLogoVitek = myAssetsLoader.assetManager.get("VitekLoadingBackground.png");
            tex_OnStartLogo0 = myAssetsLoader.assetManager.get("Logo0Atlas.png");
            tex_OnStartLogoVitek.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
            tex_OnStartLogo0.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
            spr_OnStartLogoVitek = new Sprite(tex_OnStartLogoVitek);
            spr_OnStartLogoVitek.setOrigin(0,0);
            //StartLogo Hand animation
            textureRegion_OnStartLogo0 = new TextureRegion[tex_OnStartLogo0.getWidth()/720];//tex_OnStartLogo0 count
            spr_OnStartLogo0 = new Sprite[tex_OnStartLogo0.getWidth()/720];
            for(int j=0,n=0;j<tex_OnStartLogo0.getHeight();j+=1280){
                for(int i=0;i<tex_OnStartLogo0.getWidth();i+=720){
                    textureRegion_OnStartLogo0[n] = new TextureRegion(tex_OnStartLogo0,i,j,720,1280);
                    spr_OnStartLogo0[n] = new Sprite(textureRegion_OnStartLogo0[n],0,0,textureRegion_OnStartLogo0[n].getRegionWidth(),textureRegion_OnStartLogo0[n].getRegionHeight());
                    spr_OnStartLogo0[n].setOrigin(0,0);//очень важно
                    n++;}}
            spr_OnStartLogo0[0].setPosition(0,-spr_OnStartLogo0[0].getHeight());
            section+=1;
        }//end of section=0

        if(!bool_ShowLogo){
            while (section!=4){loadSectionAssets();}
        }

        if(section==4){//loading this part after logo

            SYSTEM_STATE=0;//system on create

            if(!bool_ShowLogo){bool_Loading=false;}
            gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE, TAP_COUNT_INTERVAL, LONG_PRESS_DURATION, MAX_FLING_DELAY, this);
            inputMultiplexer = new InputMultiplexer();
            inputMultiplexer.addProcessor(this);
            inputMultiplexer.addProcessor(gestureDetector);
            Gdx.input.setInputProcessor(inputMultiplexer);
            Gdx.input.setCatchBackKey(true);
            Gdx.input.setCatchMenuKey(true);
            touchVecXYZ = new Vector3(0,0,0);
            touchDraggedXY = new Vector2(0,0);
            touches = new HashMap<Integer, TouchInfo>();//multitouch
            for(int i = 0; i < 5; i++){touches.put(i, new TouchInfo());}//multitouch

        tex_MainField = myAssetsLoader.assetManager.get("MainField.png");
        tex_NextBrickField = myAssetsLoader.assetManager.get("NextBrickField.png");
        tex_ButtonsAtlas = myAssetsLoader.assetManager.get("ButtonsAtlas.png");
        tex_ButtonsGlowAtlas = myAssetsLoader.assetManager.get("ButtonsGlowAtlas.png");
        tex_BricksAtlas = myAssetsLoader.assetManager.get("BricksAtlas.png");
        tex_MenuBackground = myAssetsLoader.assetManager.get("MenuBackground.png");
        changeLanguageTextures();// creating locale dependent textures
        tex_NumbersAtlas = myAssetsLoader.assetManager.get("NumbersAtlas.png");
        tex_MainBackground = myAssetsLoader.assetManager.get("MainBackground.png");
        tex_MainBackgroundDisplays = myAssetsLoader.assetManager.get("MainBackgroundDisplays.png");
        tex_HiddenBricksField = myAssetsLoader.assetManager.get("HiddenBricksField.png");
        tex_HiddenBrick = myAssetsLoader.assetManager.get("HiddenBrick.png");
        tex_TimerLight = myAssetsLoader.assetManager.get("TimerLight.png");
        tex_AfterExplosion = myAssetsLoader.assetManager.get("AfterExplosion.png");
        tex_AfterExplosionLight = myAssetsLoader.assetManager.get("AfterExplosionLight.png");
        tex_WarningIcon = myAssetsLoader.assetManager.get("WarningIcon.png");
        tex_SwitchLight= myAssetsLoader.assetManager.get("SwitchLight.png");
        tex_FlagsAtlas = myAssetsLoader.assetManager.get("FlagsAtlas.png");
        tex_ProfileNumbersAtlas = myAssetsLoader.assetManager.get("ProfileNumbersAtlas.png");
       // tex_OnStartLogo = myAssetsLoader.assetManager.get("VitekLoadingBackground.png");
       // tex_ParticleMask = myAssetsLoader.assetManager.get("ParticleMask.png");
        tex_BloodBackground = myAssetsLoader.assetManager.get("Blood.png");
        tex_LightningBoltAtlas = myAssetsLoader.assetManager.get("LightningBoltAtlas.png");
        tex_LightningBoltLight = myAssetsLoader.assetManager.get("LightningBoltLight.png");
        tex_DisplayForShader_1 = myAssetsLoader.assetManager.get("DisplayForShader_1.png");
        tex_DisplayForShader_2 = myAssetsLoader.assetManager.get("DisplayForShader_2.png");
        tex_DisplayForShader_3 = myAssetsLoader.assetManager.get("DisplayForShader_3.png");
        tex_PaintBombAtlas = myAssetsLoader.assetManager.get("PaintBombAtlas.png");
        tex_ShatteredGlassAtlas = myAssetsLoader.assetManager.get("ShatteredGlassAtlas.png");// ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        tex_ShatteredGlassAfterExplosion_1 = myAssetsLoader.assetManager.get("ShatteredGlassAfterExplosion_1.png");
        tex_ShadersTest = myAssetsLoader.assetManager.get("ShadersTest.png");
        tex_BricksAtlas2X2 = myAssetsLoader.assetManager.get("BricksAtlas2X2.png");
        tex_LaserCutter2X2 = myAssetsLoader.assetManager.get("LaserCutter2X2.png");
        tex_BricksAtlas3X3 = myAssetsLoader.assetManager.get("BricksAtlas3X3.png");
        tex_LaserCutter3X3 = myAssetsLoader.assetManager.get("LaserCutter3X3.png");
        tex_SideBorderLight = myAssetsLoader.assetManager.get("SideBorderL.png");
        tex_Light_0 = myAssetsLoader.assetManager.get("Light_0.png");
        tex_BigHeartAtlas = myAssetsLoader.assetManager.get("BigHeart_Atlas.png");
        tex_GameNameAtlas = myAssetsLoader.assetManager.get("GameNameAtlas.png");

        array_prc_effects_0 = new Array<>();
        array_prc_removeEffects_0 = new Array<>();
        array_prc_effects_1 = new Array<>();
        array_prc_removeEffects_1 = new Array<>();
        array_prc_effects_2 = new Array<>();
        array_prc_removeEffects_2 = new Array<>();
        array_prc_effects_3 = new Array<>();
        array_prc_removeEffects_3 = new Array<>();
        array_prc_effects_4 = new Array<>();
        array_prc_effects_5 = new Array<>();
        prc_explosion_0 = myAssetsLoader.assetManager.get("particles/particle0.prc");//bomb explosion
        prc_remover_0 = myAssetsLoader.assetManager.get("particles/particle1.prc");
        particleEffectPool_0 = new ParticleEffectPool(prc_remover_0,1,60);
        prc_remover_1 = myAssetsLoader.assetManager.get("particles/particle3.prc");
        particleEffectPool_1 = new ParticleEffectPool(prc_remover_1,1,60);
        prc_remover_2 = myAssetsLoader.assetManager.get("particles/particle8.prc");
        particleEffectPool_2 = new ParticleEffectPool(prc_remover_2,1,60);
        prc_remover_3 = myAssetsLoader.assetManager.get("particles/particle10.prc");//virus
        particleEffectPool_3 = new ParticleEffectPool(prc_remover_3,1,60);//virus
        prc_lifeBrickRegen = myAssetsLoader.assetManager.get("particles/particle13.prc");//Life brick Regen it's health
        particleEffectPool_4 = new ParticleEffectPool(prc_lifeBrickRegen,1,60);//Life brick Regen it's health
        prc_lifeBrickDestroyed = myAssetsLoader.assetManager.get("particles/particle14.prc");//Life brick Destroyed
        particleEffectPool_5 = new ParticleEffectPool(prc_lifeBrickDestroyed,1,60);//Life brick Destroyed
        prc_bombFuse = myAssetsLoader.assetManager.get("particles/particle4.prc");
        prc_bombFuseNextField = myAssetsLoader.assetManager.get("particles/particle4copy.prc");
        prc_starEmission = myAssetsLoader.assetManager.get("particles/particle5.prc");
        prc_starEmissionNextField = myAssetsLoader.assetManager.get("particles/particle5copy.prc");
        prc_viktorBombHalo = myAssetsLoader.assetManager.get("particles/particle6.prc");
        prc_viktorBombHaloNextField= myAssetsLoader.assetManager.get("particles/particle6copy.prc");
        prc_lightningBrickStasis = myAssetsLoader.assetManager.get("particles/particle7.prc");
        prc_lightningBrickStasisNextField = myAssetsLoader.assetManager.get("particles/particle7copy.prc");
        prc_virusEmission = myAssetsLoader.assetManager.get("particles/particle9.prc");
        prc_virusEmissionNextField = myAssetsLoader.assetManager.get("particles/particle9copy.prc");
        prc_paintBombSpray = myAssetsLoader.assetManager.get("particles/particle11.prc");
        prc_paintBombDrop = myAssetsLoader.assetManager.get("particles/particle12.prc");
        prc_paintBombDropNextFiled = myAssetsLoader.assetManager.get("particles/particle12copy.prc");
        prc_bigHeartBlood_left = myAssetsLoader.assetManager.get("particles/particle16_left.prc");
        prc_bigHeartBlood_right = myAssetsLoader.assetManager.get("particles/particle16_right.prc");


        sfx_collapse_0 = myAssetsLoader.assetManager.get("sfx_collapse_0.mp3");
        sfx_collapseBonus_0 = myAssetsLoader.assetManager.get("sfx_collapseBonus_0.mp3");
        sfx_collapseBonus_1 = myAssetsLoader.assetManager.get("sfx_collapseBonus_1.mp3");
        sfx_collapseBonus_2 = myAssetsLoader.assetManager.get("sfx_collapseBonus_2.wav");
        sfx_collapseBonus_3 = myAssetsLoader.assetManager.get("sfx_collapseBonus_3.mp3");
        sfx_collapseBonus_4 = myAssetsLoader.assetManager.get("sfx_collapseBonus_4.ogg");
        sfx_gameOver_0 = myAssetsLoader.assetManager.get("sfx_gameOver_0.mp3");
        sfx_gameOver_1 = myAssetsLoader.assetManager.get("sfx_gameOver_1.mp3");
        sfx_gameStart_0 = myAssetsLoader.assetManager.get("sfx_gameStart_0.wav");
        sfx_landed_0 = myAssetsLoader.assetManager.get("sfx_landed_0.mp3");
        sfx_landed_1 = myAssetsLoader.assetManager.get("sfx_landed_1.mp3");
        sfx_landed_2 = myAssetsLoader.assetManager.get("sfx_landed_2.mp3");
        sfx_landed_3 = myAssetsLoader.assetManager.get("sfx_landed_3.mp3");
        sfx_landed_4 = myAssetsLoader.assetManager.get("sfx_landed_4.mp3");
        sfx_menuButton_0 = myAssetsLoader.assetManager.get("sfx_menuButton_0.mp3");
        sfx_move_0 = myAssetsLoader.assetManager.get("sfx_move_0.wav");
        sfx_move_1 = myAssetsLoader.assetManager.get("sfx_move_1.wav");
        sfx_move_2 = myAssetsLoader.assetManager.get("sfx_move_2.wav");
        for(int i=0;i<sfx_rockLanded.length;i++){sfx_rockLanded[i] = myAssetsLoader.assetManager.get("sfx_rockLanded_"+i+".wav");}
        sfx_shuffle_0 = myAssetsLoader.assetManager.get("sfx_shuffle_0.mp3");
        sfx_tick_0 = myAssetsLoader.assetManager.get("sfx_tick_0.mp3");
        sfx_timer_0 = myAssetsLoader.assetManager.get("sfx_timer_0.ogg");// Isn't used!!!
        sfx_viktorSuperBonusStart = myAssetsLoader.assetManager.get("sfx_viktorSuperBonusStart.mp3");
        sfx_timerAfter_0 = myAssetsLoader.assetManager.get("sfx_timerAfter_0.wav");
        sfx_warning_0 = myAssetsLoader.assetManager.get("sfx_warning_0.wav");
        sfx_paintBomb_0 = myAssetsLoader.assetManager.get("sfx_paintBomb_0.mp3");
        sfx_laserCut_0 = myAssetsLoader.assetManager.get("sfx_laserCut_0.mp3");
        sfx_lifeGet_0 = myAssetsLoader.assetManager.get("sfx_lifeGet_0.mp3");
        sfx_lifeRegen_0 = myAssetsLoader.assetManager.get("sfx_lifeRegen_0.mp3");
        sfx_lifeDied_0 = myAssetsLoader.assetManager.get("sfx_lifeDied_0.mp3");
        sfx_applyingLife_0 =  myAssetsLoader.assetManager.get("sfx_applyingLife_0.mp3");
        sfx_switch_0 = myAssetsLoader.assetManager.get("sfx_switch_0.mp3");
        sfx_holyChoir_0 = myAssetsLoader.assetManager.get("sfx_holyChoir_0.mp3");
        sfx_smash_0 = myAssetsLoader.assetManager.get("sfx_smash_0.mp3");
        sfx_switch_1 = myAssetsLoader.assetManager.get("sfx_switch_1.mp3");
        for(int i=0;i<sfx_comboTimes.length;i++){sfx_comboTimes[i]=myAssetsLoader.assetManager.get("combo_sounds/sfx_comboTimes_"+(i+2)+".ogg");}
        for(int i=0;i<music_menu.length;i++){music_menu[i]=myAssetsLoader.assetManager.get("music_menu_"+i+".mp3");
            music_menu[i].setLooping(true);music_menu[i].setVolume(0.3f+float_volumeIncrement*0.2f);}
        music_highScoreBeaten = myAssetsLoader.assetManager.get("music_highScoreBeaten.mp3");
        music_highScoreBeaten.setLooping(false);music_highScoreBeaten.setVolume(1.0f+float_volumeIncrement*0.2f);
        int_musicNumber=rand.nextInt(3);
        switch (int_musicNumber){
            case 1:
                music_theme = myAssetsLoader.assetManager.get("music_theme_1.ogg");
                music_theme.setVolume(0.2f+float_volumeIncrement*0.2f);break;
            case 2:
                music_theme = myAssetsLoader.assetManager.get("music_theme_2.mp3");
                music_theme.setVolume(0.03f+float_volumeIncrement*0.2f);break;
            default:
                music_theme = myAssetsLoader.assetManager.get("music_theme_0.ogg");
                music_theme.setVolume(0.2f+float_volumeIncrement*0.2f);break;
        }

        tex_MainField.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_NextBrickField.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_ButtonsAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_ButtonsGlowAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_BricksAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_MenuBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       // tex_MenuButtonsAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
      //  tex_MenuLabelsAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
      //  tex_ScoreField.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_NumbersAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_MainBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_MainBackgroundDisplays.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_HiddenBricksField.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_HiddenBrick.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_TimerLight.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_AfterExplosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_AfterExplosionLight.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_WarningIcon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_SwitchLight.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_FlagsAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_ProfileNumbersAtlas.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
      //  tex_OnStartLogo.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
      //  tex_ParticleMask.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_BloodBackground.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_LightningBoltAtlas.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_LightningBoltLight.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_DisplayForShader_1.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_DisplayForShader_2.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_DisplayForShader_3.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_ShatteredGlassAtlas.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_ShatteredGlassAfterExplosion_1.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_ShadersTest.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_PaintBombAtlas.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_BricksAtlas2X2.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_LaserCutter2X2.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_BricksAtlas3X3.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_LaserCutter3X3.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_Light_0.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_BigHeartAtlas.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        tex_GameNameAtlas.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);

        spr_MenuBackground = new Sprite(tex_MenuBackground);spr_MenuBackground.setOrigin(0,0);
        spr_mainField = new Sprite(tex_MainField);spr_mainField.setOrigin(0,0);
        spr_nextBrickField = new Sprite(tex_NextBrickField);spr_nextBrickField.setOrigin(0,0);
       // spr_ScoreField = new Sprite(tex_ScoreField);spr_ScoreField.setOrigin(0,0);
        spr_MainBackground = new Sprite(tex_MainBackground);spr_MainBackground.setOrigin(0,0);
        spr_MainMenuBackgroundDisplays = new Sprite(tex_MainBackgroundDisplays);spr_MainMenuBackgroundDisplays.setOrigin(0,0);
        spr_hiddenBricksField = new Sprite(tex_HiddenBricksField);spr_hiddenBricksField.setOrigin(0,0);
        spr_hiddenBrick = new Sprite(tex_HiddenBrick);spr_hiddenBrick.setOrigin(0,0);
        spr_timerLight = new Sprite(tex_TimerLight);spr_timerLight.setOrigin(spr_timerLight.getWidth()*0.5f,0);
        spr_afterExplosion = new Sprite(tex_AfterExplosion);spr_afterExplosion.setOrigin(0,0);
        spr_afterExplosionLight = new Sprite(tex_AfterExplosionLight);spr_afterExplosionLight.setOrigin(0,0);
        spr_warningIcon = new Sprite(tex_WarningIcon);spr_warningIcon.setScale(0.65f);spr_warningIcon.setOrigin(0,0);spr_warningIcon.setAlpha(0.8f);
        spr_switchLight = new Sprite(tex_SwitchLight);spr_switchLight.setOrigin(0,0);
      //  spr_OnStartLogo = new Sprite(tex_OnStartLogo);spr_OnStartLogo.setOrigin(0,0);
     //   spr_ParticleMask = new Sprite(tex_ParticleMask);spr_ParticleMask.setOrigin(0,0);
        spr_BloodBackground = new Sprite(tex_BloodBackground);spr_BloodBackground.setOrigin(0,0);spr_BloodBackground.setAlpha(0.0f);
        spr_LightingBoltLight = new Sprite(tex_LightningBoltLight);spr_LightingBoltLight.setOrigin(0,0);
        spr_DisplayForShader_1 = new Sprite(tex_DisplayForShader_1);spr_DisplayForShader_1.setOrigin(0,0);
        spr_DisplayForShader_2 = new Sprite(tex_DisplayForShader_2);spr_DisplayForShader_2.setOrigin(0,0);
        spr_DisplayForShader_3 = new Sprite(tex_DisplayForShader_3);spr_DisplayForShader_3.setOrigin(0,0);
        spr_ShadersTest = new Sprite(tex_ShadersTest);spr_ShadersTest.setOrigin(0,0);
        spr_ShatteredGlassAfterExplosion_1 = new Sprite(tex_ShatteredGlassAfterExplosion_1);spr_ShatteredGlassAfterExplosion_1.setOriginCenter();
        spr_Light0_0 = new Sprite(tex_Light_0);spr_Light0_0.setOrigin(0,0);

        //SideBorderLights
        //0-left,1-right
        spr_sideBorderLight = new Sprite[2];
        spr_sideBorderLight[0] = new Sprite(tex_SideBorderLight,0,0,tex_SideBorderLight.getWidth(),tex_SideBorderLight.getHeight());
        spr_sideBorderLight[0].setOrigin(0,0);//очень важно
        spr_sideBorderLight[0].setAlpha(0.0f);
        spr_sideBorderLight[1] = new Sprite(tex_SideBorderLight,0,0,tex_SideBorderLight.getWidth(),tex_SideBorderLight.getHeight());
        spr_sideBorderLight[1].setOrigin(0,0);//очень важно
        spr_sideBorderLight[1].setFlip(true,false);
        spr_sideBorderLight[1].setAlpha(0.0f);

        //LaserCutter 3X3 sprites[]
        //0-stage1,1-stage2
        texRegion_LaserCutter3X3 = new TextureRegion[tex_LaserCutter3X3.getWidth()/202];//bricks3X3 count
        spr_LaserCutter3X3 = new Sprite[tex_LaserCutter3X3.getWidth()/202];
        for(int j=0,n=0;j<tex_LaserCutter3X3.getHeight();j+=202){
            for(int i=0;i<tex_LaserCutter3X3.getWidth();i+=202){
                texRegion_LaserCutter3X3[n] = new TextureRegion(tex_LaserCutter3X3,i,j,202,202);
                spr_LaserCutter3X3[n] = new Sprite(texRegion_LaserCutter3X3[n],0,0,texRegion_LaserCutter3X3[n].getRegionWidth(),texRegion_LaserCutter3X3[n].getRegionHeight());
                spr_LaserCutter3X3[n].setOrigin(0,0);//очень важно
                n++;}}

        //Bricks 3X3 sprites[]
        //0-red,1-green,2-blue,3-turquoise,4-yellow,5-cyan,6-rainbow
        texRegion_BricksAtlas3X3 = new TextureRegion[tex_BricksAtlas3X3.getWidth()/180];//bricks3X3 count
        spr_bricks3X3 = new Sprite[tex_BricksAtlas3X3.getWidth()/180];
        for(int j=0,n=0;j<tex_BricksAtlas3X3.getHeight();j+=180){
            for(int i=0;i<tex_BricksAtlas3X3.getWidth();i+=180){
                texRegion_BricksAtlas3X3[n] = new TextureRegion(tex_BricksAtlas3X3,i,j,180,180);
                spr_bricks3X3[n] = new Sprite(texRegion_BricksAtlas3X3[n],0,0,texRegion_BricksAtlas3X3[n].getRegionWidth(),texRegion_BricksAtlas3X3[n].getRegionHeight());
                spr_bricks3X3[n].setOrigin(0,0);//очень важно
                n++;}}

        //LaserCutter 2X2 sprites[]
        //0-stage1,1-stage2
        texRegion_LaserCutter2X2 = new TextureRegion[tex_LaserCutter2X2.getWidth()/144];//bricks2X2 count
        spr_LaserCutter2X2 = new Sprite[tex_LaserCutter2X2.getWidth()/144];
        for(int j=0,n=0;j<tex_LaserCutter2X2.getHeight();j+=144){
            for(int i=0;i<tex_LaserCutter2X2.getWidth();i+=144){
                texRegion_LaserCutter2X2[n] = new TextureRegion(tex_LaserCutter2X2,i,j,144,144);
                spr_LaserCutter2X2[n] = new Sprite(texRegion_LaserCutter2X2[n],0,0,texRegion_LaserCutter2X2[n].getRegionWidth(),texRegion_LaserCutter2X2[n].getRegionHeight());
                spr_LaserCutter2X2[n].setOrigin(0,0);//очень важно
                n++;}}

        //Bricks 2X2 sprites[]
        //0-red,1-green,2-blue,3-turquoise,4-yellow,5-cyan,6-rainbow,7-stone
        texRegion_BricksAtlas2X2 = new TextureRegion[tex_BricksAtlas2X2.getWidth()/120];//bricks2X2 count
        spr_bricks2X2 = new Sprite[tex_BricksAtlas2X2.getWidth()/120];
        for(int j=0,n=0;j<tex_BricksAtlas2X2.getHeight();j+=120){
            for(int i=0;i<tex_BricksAtlas2X2.getWidth();i+=120){
                texRegion_BricksAtlas2X2[n] = new TextureRegion(tex_BricksAtlas2X2,i,j,120,120);
                spr_bricks2X2[n] = new Sprite(texRegion_BricksAtlas2X2[n],0,0,texRegion_BricksAtlas2X2[n].getRegionWidth(),texRegion_BricksAtlas2X2[n].getRegionHeight());
                spr_bricks2X2[n].setOrigin(0,0);//очень важно
                n++;}}

        //Big Heart
            textureRegion_BigHeart = new TextureRegion[tex_BigHeartAtlas.getWidth()/250];
            spr_BigHeart = new Sprite[tex_BigHeartAtlas.getWidth()/250];

            for(int j=0,n=0;j<tex_BigHeartAtlas.getHeight();j+=250){
                for(int i=0;i<tex_BigHeartAtlas.getWidth();i+=250){
                    textureRegion_BigHeart[n] = new TextureRegion(tex_BigHeartAtlas,i,j,250,250);
                    spr_BigHeart[n] = new Sprite( textureRegion_BigHeart[n],0,0,textureRegion_BigHeart[n].getRegionWidth(),textureRegion_BigHeart[n].getRegionHeight());
                    spr_BigHeart[n].setOrigin(0,0);
                n++;}}

        //Game Name Label animation
        textureRegion_GameName = new TextureRegion[4];
            for(int j=0,n=0;j<tex_GameNameAtlas.getHeight();j+=79){
                for(int i=0;i<tex_GameNameAtlas.getWidth();i+=361){
                    textureRegion_GameName[n] = new TextureRegion(tex_GameNameAtlas,i,j,361,79);
                    n++;
                }
            }

        //Positive,Neutral,Negative bricks animation
            textureRegion_positiveBrickFallingSpeedDown = new TextureRegion[4];
            textureRegion_negativeBrickFallingSpeedUp = new TextureRegion[4];
            textureRegion_neutral3XStonesTo4XStones = new TextureRegion[4];
            textureRegion_positiveTimerSpeedDown = new TextureRegion[4];
            textureRegion_negativeTimerSpeedUp = new TextureRegion[4];
            textureRegion_magicPlusLife = new TextureRegion[10];
            for(int j=60,n=0;j<120;j+=60){
                for(int i=0;i<60*4;i+=60){
                    textureRegion_positiveBrickFallingSpeedDown[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                    n++;
                }
            }
            for(int j=60,n=0;j<120;j+=60){
                for(int i=60*4;i<60*8;i+=60){
                    textureRegion_negativeBrickFallingSpeedUp[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                    n++;
                }
            }
            for(int j=60,n=0;j<120;j+=60){
                for(int i=60*8;i<60*12;i+=60){
                    textureRegion_neutral3XStonesTo4XStones[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                    n++;
                }
            }
            for(int j=120,n=0;j<180;j+=60){
                for(int i=0;i<60*4;i+=60){
                    textureRegion_positiveTimerSpeedDown[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                    n++;
                }
            }
            for(int j=120,n=0;j<180;j+=60){
                for(int i=60*4;i<60*8;i+=60){
                    textureRegion_negativeTimerSpeedUp[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                    n++;
                }
            }
            for(int j=120,n=0;j<180;j+=60){
                for(int i=60*8;i<60*18;i+=60){
                    textureRegion_magicPlusLife[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                    n++;
                }
            }
            animation_positiveBrickFallingSpeedDown = new Animation<TextureRegion>(0.6f,textureRegion_positiveBrickFallingSpeedDown);
            animation_negativeBrickFallingSpeedUp = new Animation<TextureRegion>(0.3f,textureRegion_negativeBrickFallingSpeedUp);
            animation_neutral3XStonesTo4XStones = new Animation<TextureRegion>(1.1f,textureRegion_neutral3XStonesTo4XStones);
            animation_positiveTimerSpeedDown = new Animation<TextureRegion>(0.65f,textureRegion_positiveTimerSpeedDown);
            animation_negativeTimerSpeedUp = new Animation<TextureRegion>(0.35f,textureRegion_negativeTimerSpeedUp);
            animation_magicPlusLife = new Animation<TextureRegion>(0.1f,textureRegion_magicPlusLife);
            animation_GameName = new Animation<TextureRegion>(0.08f,textureRegion_GameName);
            animation_positiveBrickFallingSpeedDown.setPlayMode(Animation.PlayMode.LOOP);
            animation_negativeBrickFallingSpeedUp.setPlayMode(Animation.PlayMode.LOOP);
            animation_neutral3XStonesTo4XStones.setPlayMode(Animation.PlayMode.LOOP);
            animation_positiveTimerSpeedDown.setPlayMode(Animation.PlayMode.LOOP);
            animation_negativeTimerSpeedUp.setPlayMode(Animation.PlayMode.LOOP);
            animation_magicPlusLife.setPlayMode(Animation.PlayMode.LOOP);
            animation_GameName.setPlayMode(Animation.PlayMode.LOOP);
            spr_positiveBrickFallingSpeedDown = new Sprite(animation_positiveBrickFallingSpeedDown.getKeyFrame(float_deltaTimer));
            spr_negativeBrickFallingSpeedUp = new Sprite(animation_negativeBrickFallingSpeedUp.getKeyFrame(float_deltaTimer));
            spr_neutral3XStonesTo4XStones = new Sprite(animation_neutral3XStonesTo4XStones.getKeyFrame(float_deltaTimer));
            spr_positiveTimerSpeedDown = new Sprite(animation_positiveTimerSpeedDown.getKeyFrame(float_deltaTimer));
            spr_negativeTimerSpeedUp = new Sprite(animation_negativeTimerSpeedUp.getKeyFrame(float_deltaTimer));
            spr_magicPlusLife = new Sprite(animation_magicPlusLife.getKeyFrame(float_deltaTimer));
            spr_GameName = new Sprite(animation_GameName.getKeyFrame(float_deltaTimer));
            spr_positiveBrickFallingSpeedDown.setOrigin(0,0);
            spr_negativeBrickFallingSpeedUp.setOrigin(0,0);
            spr_neutral3XStonesTo4XStones.setOrigin(0,0);
            spr_positiveTimerSpeedDown.setOrigin(0,0);
            spr_negativeTimerSpeedUp.setOrigin(0,0);
            spr_magicPlusLife.setOrigin(0,0);
            spr_GameName.setOrigin(0,0);

        //Lightning Bolt animation
        texRegion_LightningBolt = new TextureRegion[4];
        for(int j=0,n=0;j<tex_LightningBoltAtlas.getHeight();j+=604){
            for(int i=0;i<tex_LightningBoltAtlas.getWidth();i+=64){
                texRegion_LightningBolt[n] = new TextureRegion(tex_LightningBoltAtlas,i,j,64,604);
                n++;
            }
        }
        animation_lightningBolt = new Animation<TextureRegion>(0.09f,texRegion_LightningBolt);
        animation_lightningBolt.setPlayMode(Animation.PlayMode.LOOP);
        spr_LightningBolt = new Sprite(animation_lightningBolt.getKeyFrame(float_LightningBoltAnimTime));
        spr_LightningBolt.setOrigin(0,0);

        //Paint bomb sprites
        //0-red,1-green,2-blue,3-purple(turquoise),4-yellow,5-cyan,6-rainbow
        textRegion_PaintBomb = new TextureRegion[tex_PaintBombAtlas.getWidth()/60];
        spr_PaintBomb = new Sprite[tex_PaintBombAtlas.getWidth()/60];
        for(int j=0,n=0;j<tex_PaintBombAtlas.getHeight();j+=60){
            for(int i=0;i<tex_PaintBombAtlas.getWidth();i+=60){
                textRegion_PaintBomb[n] = new TextureRegion(tex_PaintBombAtlas,i,j,60,60);
                spr_PaintBomb[n] = new Sprite(textRegion_PaintBomb[n],0,0, textRegion_PaintBomb[n].getRegionWidth(), textRegion_PaintBomb[n].getRegionHeight());
                spr_PaintBomb[n].setOrigin(0, 0);//очень важно
                n++;}}

        //Shattered Glass sprites from ShatteredGlassAtlas
        texRegion_ShatteredGlass = new TextureRegion[tex_ShatteredGlassAtlas.getWidth()/382];
        spr_ShatteredGlass = new Sprite[tex_ShatteredGlassAtlas.getWidth()/382];
        for(int j=0,n=0;j<tex_ShatteredGlassAtlas.getHeight();j+=622){
            for(int i=0;i<tex_ShatteredGlassAtlas.getWidth();i+=382){
                texRegion_ShatteredGlass[n] = new TextureRegion(tex_ShatteredGlassAtlas,i,j,382,622);
                spr_ShatteredGlass[n]= new Sprite(texRegion_ShatteredGlass[n],0,0,texRegion_ShatteredGlass[n].getRegionWidth(),texRegion_ShatteredGlass[n].getRegionHeight());
                spr_ShatteredGlass[n].setOrigin(0,0);//so important
                n++;}}

        //Profile Numbers sprites from ProfileNumbersAtlas
        texRegion_ProfileNumbersAtlas = new TextureRegion[3];
        spr_ProfileNumbersAtlas = new Sprite[3];
        for(int j=0,n=0;j<tex_ProfileNumbersAtlas.getHeight();j+=63){
            for(int i=0;i<tex_ProfileNumbersAtlas.getWidth();i+=74){
                texRegion_ProfileNumbersAtlas[n] = new TextureRegion(tex_ProfileNumbersAtlas,i,j,74,63);
                spr_ProfileNumbersAtlas[n]= new Sprite(texRegion_ProfileNumbersAtlas[n],0,0,texRegion_ProfileNumbersAtlas[n].getRegionWidth(),texRegion_ProfileNumbersAtlas[n].getRegionHeight());
                spr_ProfileNumbersAtlas[n].setOrigin(0,0);//so important
                n++;}}

        //Flags sprites from FlagsAtlas
        texRegion_FlagsAtlas = new TextureRegion[3];
        spr_FlagsAtlas = new Sprite[3];
        for(int j=0,n=0;j<tex_FlagsAtlas.getHeight();j+=72){
            for(int i=0;i<tex_FlagsAtlas.getWidth();i+=120){
                texRegion_FlagsAtlas[n] = new TextureRegion(tex_FlagsAtlas,i,j,120,72);
                spr_FlagsAtlas[n] = new Sprite(texRegion_FlagsAtlas[n],0,0,texRegion_FlagsAtlas[n].getRegionWidth(),texRegion_FlagsAtlas[n].getRegionHeight());
                spr_FlagsAtlas[n].setOrigin(0,0);//so important
                n++;}}

        //Спрайты цифр из регионов текстуры
        texRegion_NumbersAtlas = new TextureRegion[11];
        spr_numbers = new Sprite[11];
        for(int j=0,n=0;j<tex_NumbersAtlas.getHeight();j+=19){
            for(int i=0;i<tex_NumbersAtlas.getWidth();i+=22){
                texRegion_NumbersAtlas[n] = new TextureRegion(tex_NumbersAtlas,i,j,22,19);
                spr_numbers[n] = new Sprite(texRegion_NumbersAtlas[n],0,0,texRegion_NumbersAtlas[n].getRegionWidth(),texRegion_NumbersAtlas[n].getRegionHeight());
                spr_numbers[n].setOrigin(0, 0);//очень важно
                n++;}}
/*
        //Спрайты лейблов меню из регионов текстуры
        texRegion_MenuLabels = new TextureRegion[5];
        spr_menuLabels = new Sprite[5];
        for(int j=0,n=0;j<tex_MenuLabelsAtlas.getHeight();j+=49){
            for(int i=0;i<tex_MenuLabelsAtlas.getWidth();i+=291){
                texRegion_MenuLabels[n] = new TextureRegion(tex_MenuLabelsAtlas,i,j,291,49);
                spr_menuLabels[n] = new Sprite(texRegion_MenuLabels[n],0,0,texRegion_MenuLabels[n].getRegionWidth(),texRegion_MenuLabels[n].getRegionHeight());
                spr_menuLabels[n].setOrigin(0, 0);//очень важно
                n++;}}*/
/*
        //Спрайты кнопок меню из регионов текстуры 321x100
        texRegion_MenuButtons = new TextureRegion[tex_MenuButtonsAtlas.getHeight()/100];
        spr_menuButtons = new Sprite[tex_MenuButtonsAtlas.getHeight()/100];
        for(int j=0,n=0;j<tex_MenuButtonsAtlas.getHeight();j+=100){
            for(int i=0;i<tex_MenuButtonsAtlas.getWidth();i+=321){
                texRegion_MenuButtons[n] = new TextureRegion(tex_MenuButtonsAtlas,i,j,321,100);
                spr_menuButtons[n] = new Sprite(texRegion_MenuButtons[n],0,0,texRegion_MenuButtons[n].getRegionWidth(),texRegion_MenuButtons[n].getRegionHeight());
                spr_menuButtons[n].setOrigin(0, 0);//очень важно
                n++;}}*/

        //Спрайты блоков из регионов текстуры
        /**
         * 0-empty,1-red,2-green,3-blue,4-turquoise,5-yellow,6-cyan,7-stone,8-bomb,9-star,10-lightning,11-viktor,12-virus,13-rainbow,14-paintbomb(not used),
         * 15,16,17,18 - positive,neutral,negative,magic - bricks
         * 18,19,20 - magic brick damaged - 21 background for damaged magic bricks
         * in game using (15)100 - brickspddwn,(17)102 - brickspdup...
         * for sprite 0-red,1-green...etc
         * in game 100-199 - special positive,neutral,negative
         * int game 800-899 - magic bricks
         * 888 - life brick
         **/
        int_bricksCount = tex_BricksAtlas.getWidth()/60;//bricks count
        texRegion_Bricks = new TextureRegion[int_bricksCount];
        spr_bricks = new Sprite[int_bricksCount];
        Gdx.app.log("'","bricks_count="+int_bricksCount);
        for(int j=0,n=0;j<60;j+=60){//only first stroke for different bricks types
            for(int i=0;i<tex_BricksAtlas.getWidth();i+=60){
                texRegion_Bricks[n] = new TextureRegion(tex_BricksAtlas,i,j,60,60);
                spr_bricks[n] = new Sprite(texRegion_Bricks[n],0,0,texRegion_Bricks[n].getRegionWidth(),texRegion_Bricks[n].getRegionHeight());
                spr_bricks[n].setOrigin(0,0);//очень важно
                n++;}}

        //Спрайты кнопок и их свечения из регионов текстуры
        texRegion_Buttons = new TextureRegion[24];
        texRegion_ButtonsGlow = new TextureRegion[12];
        spr_buttons = new Sprite[24];
        spr_buttonsGlow = new Sprite[12];
        for(int j=2,n=0;j<tex_ButtonsAtlas.getHeight()-2;j+=256){
            for(int i=2;i<tex_ButtonsAtlas.getWidth()-2;i+=256){
                texRegion_Buttons[n]= new TextureRegion(tex_ButtonsAtlas,i,j,256,256);
                spr_buttons[n] = new Sprite(texRegion_Buttons[n],0,0,texRegion_Buttons[n].getRegionWidth(),texRegion_Buttons[n].getRegionHeight());
                spr_buttons[n].setOrigin(0, 0);//очень важно
                spr_buttons[n].setScale(0.6f,0.6f);
                n++;i+=4;}j+=4;
        }
        for(int j=2,n=0;j<tex_ButtonsGlowAtlas.getHeight()-2;j+=256){
            for(int i=2;i<tex_ButtonsGlowAtlas.getWidth()-2;i+=256){
                texRegion_ButtonsGlow[n]= new TextureRegion(tex_ButtonsGlowAtlas,i,j,256,256);
                spr_buttonsGlow[n] = new Sprite(texRegion_ButtonsGlow[n],0,0,texRegion_ButtonsGlow[n].getRegionWidth(),texRegion_ButtonsGlow[n].getRegionHeight());
                spr_buttonsGlow[n].setOrigin(0, 0);//очень важно
                spr_buttonsGlow[n].setScale(0.6f,0.6f);

            n++;i+=4;}j+=4;
        }

            //music_theme.setVolume(0.3f+float_volumeIncrement*0.5f);
            music_theme.setLooping(true);
            int_music_menu_index = rand.nextInt(music_menu.length);
            //music_menu[int_music_menu_index].play();
            //Gdx.app.log("'",""+int_music_menu_index);

        /** TESTER **/
        if(bool_testLoader){
            boolean bool_loadMainFieldFromPixmap=true;//load form pixmap
            //mainField
            if(bool_loadMainFieldFromPixmap){
                pixmap_testLoaderMainField = myAssetsLoader.assetManager.get("TestField.png");
                colorFromInt = new ColorFromInt();
                Color color = new Color();
                for(int i=0;i<6;i++){
                    for(int j=3;j<13;j++){
                        colorFromInt.rgba8888ToColor(color,pixmap_testLoaderMainField.getPixel(i,j));
                        int_bricksArray2D[i][j]=0;
                        if(color.r==1.0f&&color.g==0.0f&&color.b==0.0f){int_bricksArray2D[i][j]=1;}//red brick
                        if(color.r==0.0f&&color.g==1.0f&&color.b==0.0f){int_bricksArray2D[i][j]=2;}//green brick
                        if(color.r==0.0f&&color.g==0.0f&&color.b==1.0f){int_bricksArray2D[i][j]=3;}//blue brick
                        if(color.r==1.0f&&color.g==0.0f&&color.b==1.0f){int_bricksArray2D[i][j]=4;}//purple brick
                        if(color.r==1.0f&&color.g==1.0f&&color.b==0.0f){int_bricksArray2D[i][j]=5;}//yellow brick
                        if(color.r==0.0f&&color.g==1.0f&&color.b==1.0f){int_bricksArray2D[i][j]=6;}//cyan brick
                        if(color.toString().equals("969696ff")){int_bricksArray2D[i][j]=int_const_stoneBrickNumber;}//stone brick
                        if(color.toString().equals("ffa7ffff")){int_bricksArray2D[i][j]=int_const_stoneBrickNumber+6;}//rainbow brick
                        if(color.toString().equals("850500ff")){int_bricksArray2D[i][j]=100+rand.nextInt(5);}//rand special brick
                        if(color.toString().equals("7fc9ffff")){int_bricksArray2D[i][j]=888;}//life brick
                    }
                }
            }else{
                for(int i=0;i<6;i++){for(int j=0;j<13;j++){int_bricksArray2D[i][j]=Figures.testMainField[j][i];}}
            }

            //fallingBlock
            for(int i=0;i<3;i++){for(int j=0;j<3;j++){for(int k=0;k<3;k++){int_fallingBrickGPS[i][j][k]=0;}}}
            for(int i=0,i_fall=0,j_fall=0;i<6;i++){
                for(int j=0;j<13;j++){
                    if(Figures.testFallingField[j][i]!=0&&int_bricksArray2D[i][j]==0){
                        j_fall+=1;
                        int_fallingBrickGPS[i_fall][j_fall][0]=j;
                        int_fallingBrickGPS[i_fall][j_fall][1]=i;
                        int_fallingBrickGPS[i_fall][j_fall][2]=Figures.testFallingField[j][i];
                        if(j_fall==2&&i_fall<3){j_fall=0;i_fall++;}
                        if(j_fall==1&&i_fall<3){j_fall=0;i_fall++;}
                    }
                }
            }
            //nextBlock
            for(int i=0;i<3;i++){for(int j=0;j<3;j++){int_nextBricksArray2D[i][j]=0;}}
            //for(int i=0;i<3;i++){for(int j=0;j<3;j++){if(Figures.testNextField[i][j]!=0){int_nextBricksArray2D[i][j]=Figures.testNextField[i][j];}}}
            for(int i=0;i<3;i++){for(int j=0;j<3;j++){if(Figures.type0[i][j]==1){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+4;}}}
            //for(int i=0;i<3;i++){ for(int j=0;j<3;j++){int_nextBricksArray2D[i][j]=1;}}//3x3
            //for(int i=0;i<2;i++){ for(int j=0;j<2;j++){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+6;}}//2x2
            //vars
            int_musicNumber = 0;
            switch (int_musicNumber){
                case 0:
                    music_theme = myAssetsLoader.assetManager.get("music_theme_1.ogg");
                    music_theme.setVolume(0.0f);break;//disable music
                case 1:
                    music_theme = myAssetsLoader.assetManager.get("music_theme_1.ogg");
                    music_theme.setVolume(0.2f+float_volumeIncrement*0.2f);break;
                case 2:
                    music_theme = myAssetsLoader.assetManager.get("music_theme_2.mp3");
                    music_theme.setVolume(0.03f+float_volumeIncrement*0.2f);break;
                default:
                    music_theme = myAssetsLoader.assetManager.get("music_theme_0.ogg");
                    music_theme.setVolume(0.2f+float_volumeIncrement*0.2f);break;
            }
            int_music_menu_index = 0;
            bool_ghostEnabled=true;
            bool_nextPrizeIsLife=false;
            int_lifeBrickDamage=2;
            int_lifeScore=0;
            int_comboTimesMultiplier=0;
            GAME_STATE=0;GAME_STATE_BF=2;GAME_STATE_GENBONUS=0;GAME_STATE_COLLIDE=0;GAME_STATE_PDF=0;GAME_STATE_BONUSTYPE=0;APPLYING_LIFE_STATE=0;
        }
        /** THE END OF TESTER **/

        calculatePhysicsForGhost();
        bool_FreshStart = true;
        alphabetCore = new AlphabetCore(myAssetsLoader.assetManager);
        prc_explosion_0.reset();
        prc_remover_1.reset();prc_remover_0.reset();prc_remover_2.reset();
        prc_bombFuse.reset();prc_bombFuseNextField.reset();
        prc_lightningBrickStasis.reset();prc_lightningBrickStasisNextField.reset();
        prc_paintBombSpray.reset();prc_paintBombDrop.reset();prc_paintBombDropNextFiled.reset();
        prc_viktorBombHalo.reset();prc_viktorBombHaloNextField.reset();
        prc_virusEmission.reset();prc_virusEmissionNextField.reset();
        prc_starEmission.reset();prc_starEmissionNextField.reset();
        prc_lifeBrickRegen.reset();prc_bigHeartBlood_left.reset();
        prc_bigHeartBlood_right.reset();
        countBonusChanceAndBloodBackAlpha();
       // bool_Loading=false;
        }//end of section 4
    }
    private void saveProgress(){

        if(GAME_STATE==-2){GAME_STATE=-1;GAME_STATE_BF=GAME_STATE;}
        prefs.putInteger("profileNumber",int_profileNumber);
        prefs.putInteger("language",int_language);
        prefs.putInteger("highScore"+int_profileNumber, int_highScore);
        prefs.putInteger("GAME_STATE"+int_profileNumber,GAME_STATE);
        prefs.putInteger("GAME_STATE_BF"+int_profileNumber,GAME_STATE_BF);
        prefs.putInteger("GAME_STATE_BONUSTYPE"+int_profileNumber,GAME_STATE_BONUSTYPE);
        prefs.putInteger("GAME_STATE_COLLIDE"+int_profileNumber,GAME_STATE_COLLIDE);
        prefs.putInteger("GAME_STATE_PDF"+int_profileNumber,GAME_STATE_PDF);
        prefs.putInteger("currentScore"+int_profileNumber,int_curScore);
        prefs.putBoolean("bool_highScoreBeaten"+int_profileNumber,bool_highScoreBeaten);
        prefs.putBoolean("bool_ghostEnabled"+int_profileNumber,bool_ghostEnabled);
        prefs.putInteger("int_comboTimesMultiplier"+int_profileNumber, int_comboTimesMultiplier);
        prefs.putInteger("int_paintBombColor"+int_profileNumber, int_paintBombColor);
        prefs.putInteger("int_paintBombNextColor"+int_profileNumber, int_paintBombNextColor);
        prefs.putInteger("int_brick2X2stage"+int_profileNumber, int_brick2X2stage);
        prefs.putInteger("int_brick3X3stage"+int_profileNumber, int_brick3X3stage);
        prefs.putBoolean("bool_soundEnable"+int_profileNumber, bool_soundEnable);
        prefs.putInteger("int_lifeScore"+int_profileNumber,int_lifeScore);
        prefs.putBoolean("bool_nextPrizeIsLife"+int_profileNumber,bool_nextPrizeIsLife);
        prefs.putInteger("int_lifeBrickDamage"+int_profileNumber,int_lifeBrickDamage);
        prefs.putInteger("APPLYING_LIFE_STATE"+int_profileNumber,APPLYING_LIFE_STATE);
        prefs.putInteger("int_numOfRemovedBricksByLifeBonus"+int_profileNumber,int_numOfRemovedBricksByLifeBonus);
        prefs.putInteger("int_numOfAllBricksForLifeBonus"+int_profileNumber,int_numOfAllBricksForLifeBonus);
        //prefs.putBoolean("bool_bombExploded"+int_profileNumber,bool_bombExploded);
        String a;

        stringBuilder.delete(0,stringBuilder.length());
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    stringBuilder.append(int_fallingBrickGPS[i][j][k]);
                    if(i!=2||j!=2||k!=2){stringBuilder.append(",");}
                }
            }
        }
        a=stringBuilder.toString();
        prefs.putString("fallingBrickState"+int_profileNumber,a);

        stringBuilder.delete(0,stringBuilder.length());
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                stringBuilder.append(int_nextBricksArray2D[i][j]);
                if(i!=2||j!=2){stringBuilder.append(",");}
            }
        }
        a=stringBuilder.toString();
        prefs.putString("nextBrickState"+int_profileNumber,a);

        stringBuilder.delete(0,stringBuilder.length());
        for(int i=0;i<6;i++){
            for(int j=3;j<13;j++){
                stringBuilder.append(int_bricksArray2D[i][j]);
                if(i!=5||j!=12){stringBuilder.append(",");}

            }
        }
        a=stringBuilder.toString();
        prefs.putString("mainFieldState"+int_profileNumber,a);
        prefs.flush();

        if(hiddenBricksTimer>System.currentTimeMillis()){bool_hiddenTimerCondition=true;}
    }

    void loadProgress(boolean bool_firstLoad){

        //Обнуление полей
        for(int i=0;i<6;i++){
            for(int j=0;j<13;j++){
                int_bricksArray2D[i][j]=0;int_matchedBricksArray2D[i][j][0]=0;int_matchedBricksArray2D[i][j][1]=0;}}
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                int_nextBricksArray2D[i][j]=0;
                for(int k=0;k<3;k++){int_fallingBrickGPS[i][j][k]=0;int_fallingBrickGPSghost[i][j][k]=0;}}}


        prefs = Gdx.app.getPreferences("GDX_GalaxyStars_Preferences");

        if(bool_firstLoad){
            if(prefs.contains("profileNumber")){int_profileNumber = prefs.getInteger("profileNumber",1);}
            else{prefs.putInteger("profileNumber", 1);prefs.flush();}

            //language 0-eng 1-rus
            str_language= Locale.getDefault().toString();Gdx.app.log("'","Default language ="+str_language);
            if(prefs.contains("language")){int_language = prefs.getInteger("language",0);}
            else{str_language = Locale.getDefault().toString();
                if(!str_language.equalsIgnoreCase("ru_RU")){prefs.putInteger("language", 0);int_language=0;}
                else{prefs.putInteger("language", 1);int_language=1;}
                prefs.flush();}
        }

        //Загрузка настроек и сохранений
        //language has already been loaded before!!!!!!!

        if(prefs.contains("highScore"+int_profileNumber)){int_highScore = prefs.getInteger("highScore"+int_profileNumber,0);}
        else{prefs.putInteger("highScore"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("currentScore"+int_profileNumber)){int_curScore = prefs.getInteger("currentScore"+int_profileNumber,0);}
        else{prefs.putInteger("currentScore"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_lifeScore"+int_profileNumber)){int_lifeScore = prefs.getInteger("int_lifeScore"+int_profileNumber,0);}
        else{prefs.putInteger("int_lifeScore"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_lifeBrickDamage"+int_profileNumber)){int_lifeBrickDamage = prefs.getInteger("int_lifeBrickDamage"+int_profileNumber,0);}
        else{prefs.putInteger("int_lifeBrickDamage"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("bool_highScoreBeaten"+int_profileNumber)){bool_highScoreBeaten = prefs.getBoolean("bool_highScoreBeaten"+int_profileNumber,false);}
        else{prefs.putBoolean("bool_highScoreBeaten"+int_profileNumber, false);prefs.flush();}

        if(prefs.contains("bool_ghostEnabled"+int_profileNumber)){bool_ghostEnabled = prefs.getBoolean("bool_ghostEnabled"+int_profileNumber,true);}
        else{prefs.putBoolean("bool_ghostEnabled"+int_profileNumber, true);prefs.flush();}

        if(prefs.contains("int_comboTimesMultiplier"+int_profileNumber)){int_comboTimesMultiplier = prefs.getInteger("int_comboTimesMultiplier"+int_profileNumber,0);}
        else{prefs.putInteger("int_comboTimesMultiplier"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("GAME_STATE"+int_profileNumber)){GAME_STATE = prefs.getInteger("GAME_STATE"+int_profileNumber,0);}
        else{prefs.putInteger("GAME_STATE"+int_profileNumber, -3);prefs.flush();GAME_STATE=-3;GAME_STATE_BF=1;}

        if(prefs.contains("GAME_STATE_BF"+int_profileNumber)){GAME_STATE_BF = prefs.getInteger("GAME_STATE_BF"+int_profileNumber,1);}
        else{prefs.putInteger("GAME_STATE_BF"+int_profileNumber, 1);prefs.flush();GAME_STATE=-3;GAME_STATE_BF=1;}

        if(prefs.contains("GAME_STATE_BONUSTYPE"+int_profileNumber)){GAME_STATE_BONUSTYPE = prefs.getInteger("GAME_STATE_BONUSTYPE"+int_profileNumber,0);}
        else{prefs.putInteger("GAME_STATE_BONUSTYPE"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("GAME_STATE_COLLIDE"+int_profileNumber)){GAME_STATE_COLLIDE = prefs.getInteger("GAME_STATE_COLLIDE"+int_profileNumber,0);}
        else{prefs.putInteger("GAME_STATE_COLLIDE"+int_profileNumber, 0);prefs.flush();}

       /* if(prefs.contains("GAME_STATE_GENBONUS")){GAME_STATE_GENBONUS = prefs.getInteger("GAME_STATE_GENBONUS",0);}
        else{prefs.putInteger("GAME_STATE_GENBONUS", 0);prefs.flush();}*/

        if(prefs.contains("GAME_STATE_PDF"+int_profileNumber)){GAME_STATE_PDF = prefs.getInteger("GAME_STATE_PDF"+int_profileNumber,0);}
        else{prefs.putInteger("GAME_STATE_PDF"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_paintBombColor"+int_profileNumber)){int_paintBombColor = prefs.getInteger("int_paintBombColor"+int_profileNumber,0);}
        else{prefs.putInteger("int_paintBombColor"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_paintBombNextColor"+int_profileNumber)){int_paintBombNextColor = prefs.getInteger("int_paintBombNextColor"+int_profileNumber,0);}
        else{prefs.putInteger("int_paintBombColor"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_brick2X2stage"+int_profileNumber)){int_brick2X2stage = prefs.getInteger("int_brick2X2stage"+int_profileNumber,0);}
        else{prefs.putInteger("int_brick2X2stage"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_brick3X3stage"+int_profileNumber)){int_brick3X3stage = prefs.getInteger("int_brick3X3stage"+int_profileNumber,0);}
        else{prefs.putInteger("int_brick3X3stage"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("bool_soundEnable"+int_profileNumber)){bool_soundEnable = prefs.getBoolean("bool_soundEnable"+int_profileNumber,true);}
        else{prefs.putBoolean("bool_soundEnable"+int_profileNumber, true);prefs.flush();}

        if(prefs.contains("bool_nextPrizeIsLife"+int_profileNumber)){bool_nextPrizeIsLife = prefs.getBoolean("bool_nextPrizeIsLife"+int_profileNumber,false);}
        else{prefs.putBoolean("bool_nextPrizeIsLife"+int_profileNumber, false);prefs.flush();}

        if(prefs.contains("APPLYING_LIFE_STATE"+int_profileNumber)){APPLYING_LIFE_STATE = prefs.getInteger("APPLYING_LIFE_STATE"+int_profileNumber,0);}
        else{prefs.putInteger("APPLYING_LIFE_STATE"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_numOfRemovedBricksByLifeBonus"+int_profileNumber)){int_numOfRemovedBricksByLifeBonus = prefs.getInteger("int_numOfRemovedBricksByLifeBonus"+int_profileNumber,0);}
        else{prefs.putInteger("int_numOfRemovedBricksByLifeBonus"+int_profileNumber, 0);prefs.flush();}

        if(prefs.contains("int_numOfAllBricksForLifeBonus"+int_profileNumber)){int_numOfAllBricksForLifeBonus = prefs.getInteger("int_numOfAllBricksForLifeBonus"+int_profileNumber,0);}
        else{prefs.putInteger("int_numOfAllBricksForLifeBonus"+int_profileNumber, 0);prefs.flush();}


        /*if(prefs.contains("bool_bombExploded"+int_profileNumber)){bool_bombExploded=prefs.getBoolean("bool_bombExploded"+int_profileNumber,false);}
        else{prefs.putBoolean("bool_bombExploded"+int_profileNumber, false);prefs.flush();}*/

        stringBuilder.delete(0,stringBuilder.length());//important

        if(prefs.contains("nextBrickState"+int_profileNumber)){
            String a = prefs.getString("nextBrickState"+int_profileNumber);
            String b = "0";
            for(int i=0,n=0;i<3;i++){
                for(int j=0;j<3;j++){
                    while(n<a.length()&&a.charAt(n)!=','){

                        stringBuilder.append(a.charAt(n));
                        b=stringBuilder.toString();
                        n++;
                    }
                    int_nextBricksArray2D[i][j]=Integer.parseInt(b);
                    stringBuilder.delete(0,stringBuilder.length());
                    n++;
                }
            }
            GAME_FIG_GENERATED=1;
        }else{prefs.putString("nextBrickState"+int_profileNumber,"");prefs.flush();}

        if(prefs.contains("fallingBrickState"+int_profileNumber)){
            String a = prefs.getString("fallingBrickState"+int_profileNumber);
            String b = "0";
            for(int i=0,n=0;i<3;i++){
                for(int j=0;j<3;j++){
                    for(int k=0;k<3;k++){
                        while(n<a.length()&&a.charAt(n)!=','){
                            stringBuilder.append(a.charAt(n));
                            b=stringBuilder.toString();
                            n++;
                        }
                        int_fallingBrickGPS[i][j][k]=Integer.parseInt(b);
                        stringBuilder.delete(0,stringBuilder.length());
                        n++;
                    }
                }
            }
        }else{prefs.putString("fallingBrickState"+int_profileNumber,"");prefs.flush();}

        if(prefs.contains("mainFieldState"+int_profileNumber)){
            String a = prefs.getString("mainFieldState"+int_profileNumber);
            String b = "0";
            for(int i=0,n=0;i<6;i++){
                for(int j=3;j<13;j++){
                    while(n<a.length()&&a.charAt(n)!=','){
                        stringBuilder.append(a.charAt(n));
                        b=stringBuilder.toString();
                        n++;
                    }
                    int_bricksArray2D[i][j]=Integer.parseInt(b);
                    stringBuilder.delete(0,stringBuilder.length());
                    n++;
                }
            }
        }else{prefs.putString("mainFieldState"+int_profileNumber,"");prefs.flush();}


        //life
        if((APPLYING_LIFE_STATE==1||APPLYING_LIFE_STATE==2)||(APPLYING_LIFE_STATE==0&&GAME_STATE_BF==7)){
            for (int i=0;i<6;i++){
                for (int j=0;j<13;j++){
                    if(j<3){int_bricksArray2D[i][j]=0;}
                    if(int_bricksArray2D[i][j]==888){int_bricksArray2D[i][j]=0;}
                    if(int_bricksArray2D[i][j]!=0){int_numOfAllBricksForLifeBonus++;}
                }}int_numOfAllBricksForLifeBonus=(int)(int_numOfAllBricksForLifeBonus*float_lifeApplyingPower);
            APPLYING_LIFE_STATE=3;
        }
        if(APPLYING_LIFE_STATE==3){
            while(int_numOfRemovedBricksByLifeBonus<int_numOfAllBricksForLifeBonus){
                int i = rand.nextInt(6);int j = rand.nextInt(13);
                if(int_bricksArray2D[i][j]!=0){
                    int_bricksArray2D[i][j]=0;int_numOfRemovedBricksByLifeBonus++;
                }
            }
        }
        if(GAME_STATE_BF==7){GAME_STATE=0;GAME_STATE_BF=1;}
        if(APPLYING_LIFE_STATE!=0){APPLYING_LIFE_STATE=0;}
        calculatePhysics();
        calculatePhysics();
        if(!bool_firstLoad){
            calculatePhysicsForGhost();
            countBonusChanceAndBloodBackAlpha();
        }
        Gdx.app.log("'","LOADING DONE");

    }
    void changeLanguageTextures(){

        if (int_language == 1) {
            tex_MenuButtonsAtlas = myAssetsLoader.assetManager.get("MenuButtonsAtlas_RUS.png");
            tex_MenuLabelsAtlas = myAssetsLoader.assetManager.get("MenuLabelsAtlas_RUS.png");
            tex_ScoreField = myAssetsLoader.assetManager.get("ScoreField_RUS.png");
            tex_Help = myAssetsLoader.assetManager.get("HelpBookAtlas_RUS.png");
        } else {
            tex_MenuButtonsAtlas = myAssetsLoader.assetManager.get("MenuButtonsAtlas_ENG.png");
            tex_MenuLabelsAtlas = myAssetsLoader.assetManager.get("MenuLabelsAtlas_ENG.png");
            tex_ScoreField = myAssetsLoader.assetManager.get("ScoreField_ENG.png");
            tex_Help = myAssetsLoader.assetManager.get("HelpBookAtlas_ENG.png");
        }
        remakeLanguageDependedSprites();
    }
    void remakeLanguageDependedSprites(){

        tex_MenuButtonsAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_MenuLabelsAtlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_ScoreField.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex_Help.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);

        //Спрайты кнопок меню из регионов текстуры
        texRegion_MenuButtons = new TextureRegion[tex_MenuButtonsAtlas.getHeight()/10];
        spr_menuButtons = new Sprite[tex_MenuButtonsAtlas.getHeight()/10];
        for(int j=0,n=0;j<tex_MenuButtonsAtlas.getHeight();j+=100){
            for(int i=0;i<tex_MenuButtonsAtlas.getWidth();i+=321){
                texRegion_MenuButtons[n] = new TextureRegion(tex_MenuButtonsAtlas,i,j,321,100);
                spr_menuButtons[n] = new Sprite(texRegion_MenuButtons[n],0,0,texRegion_MenuButtons[n].getRegionWidth(),texRegion_MenuButtons[n].getRegionHeight());
                spr_menuButtons[n].setOrigin(0, 0);//очень важно
                n++;}}
        //Спрайты лейблов меню из регионов текстуры
        texRegion_MenuLabels = new TextureRegion[5];
        spr_menuLabels = new Sprite[5];
        for(int j=0,n=0;j<tex_MenuLabelsAtlas.getHeight();j+=49){
            for(int i=0;i<tex_MenuLabelsAtlas.getWidth();i+=291){
                texRegion_MenuLabels[n] = new TextureRegion(tex_MenuLabelsAtlas,i,j,291,49);
                spr_menuLabels[n] = new Sprite(texRegion_MenuLabels[n],0,0,texRegion_MenuLabels[n].getRegionWidth(),texRegion_MenuLabels[n].getRegionHeight());
                spr_menuLabels[n].setOrigin(0, 0);//очень важно
                n++;}}
        spr_ScoreField = new Sprite(tex_ScoreField);spr_ScoreField.setOrigin(0,0);

        textureRegion_Help = new TextureRegion[2];
        spr_Help = new Sprite[2];
        for(int j=0,n=0;j<tex_Help.getHeight();j+=1854){
            for(int i=0;i<tex_Help.getWidth();i+=361){
                textureRegion_Help[n] = new TextureRegion(tex_Help,i,j,361,1854);
                spr_Help[n] = new Sprite(textureRegion_Help[n],0,0,textureRegion_Help[n].getRegionWidth(),textureRegion_Help[n].getRegionHeight());
                spr_Help[n].setOrigin(0,0);
            n++;}}
    }

    @NotNull
    private float[] getFallingBrickPos(int i, int j){
        float[] float_posXY = {0,0};
        float_posXY[0]=60+11+i*60+30;
        float_posXY[1]=viewport.getWorldHeight()-180-11-(j-3)*60+35;
        return float_posXY;
    }
    @NotNull
    private float getSolidBrickPosX(int i){
        float float_posX = 60+11+i*60+30;
        return float_posX;
    }
    @NotNull
    private float getSolidBrickPosY(int j){
        float float_posY = viewport.getWorldHeight()-180-11-(j-3)*60+30;;
        return float_posY;
    }
    @NotNull
    private Color getSolidBrickColor(int colorOfBrick){ /** Add new bricks here**/
        Color color;
        switch (colorOfBrick){
                case 1:color = Color.valueOf("ff2929");break;
                case 2:color = Color.GREEN;break;
                case 3:color = Color.valueOf("0099ff");break;
                case 4:color = Color.PURPLE;break;
                case 5:color = Color.YELLOW ;break;
                case 6:color = Color.CYAN ;break;
                case int_const_stoneBrickNumber:color = Color.WHITE ; break;//7
                case int_const_stoneBrickNumber+6:color = Color.BLACK;break;//rainbow
                case int_const_stoneBrickNumber+8:color = Color.GREEN; break;//pos
                case int_const_stoneBrickNumber+9:color = Color.YELLOW; break;//neu
                case int_const_stoneBrickNumber+10:color = Color.RED; break;//neg
                case int_const_stoneBrickNumber+11:color = Color.BLACK;break;//magic

            default:color = Color.WHITE ;break;
        }
        return color;
    }
    @NotNull
    private Color getPaintBombColor(int int_paintBombColor){ /** Add new bricks here**/
        Color color = new Color();
        switch (int_paintBombColor){
            case 0:color = Color.valueOf("ff2929");break;
            case 1:color = Color.GREEN;break;
            case 2:color = Color.valueOf("0099ff");break;
            case 3:color = Color.PURPLE;break;
            case 4:color = Color.YELLOW ;break;
            case 5:color = Color.CYAN ;break;
            case 6:color.set(rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);break;//rainbow
            default:color = Color.WHITE ;break;
        }
        return color;
    }

    private int genRandBrick(){/** Add new bricks here**/
        switch (rand.nextInt(int_const_stoneBrickNumber-1)+1){//0-int_const_stoneBrickNumber -> 1-int_const_stoneBrickNumber+1
            case 1:return 1;
            case 2:return 2;
            case 3:return 3;
            case 4:return 4;
            case 5:return 5;
            case 6:return 6;//cyan
            default:return 0;
        }
    }

    @Override
    public void pause(){
        saveProgress();
        if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3){GAME_STATE_BF=GAME_STATE;GAME_STATE=0;
            if(bool_soundEnable){music_theme.pause();}
        }
        else{
            if(bool_soundEnable){music_menu[int_music_menu_index].stop();}
        }
        SYSTEM_STATE=3;//system on pause
        super.pause();
    }
    @Override
    public void resume(){
        if(bool_hiddenTimerCondition){hiddenBricksTimer=System.currentTimeMillis()+hiddenBricksTimerIncrement;bool_hiddenTimerCondition=false;}
        if(bool_soundEnable){music_menu[int_music_menu_index].play();}
        SYSTEM_STATE=4;//system on resume
    }
    @Override
    public void resize (int width,int height) {
        screenW=width;screenH=height;Gdx.app.log("'","ScreenW="+screenW+" ScreenH="+screenH);
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth*0.5f,camera.viewportHeight*0.5f,0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        SYSTEM_STATE=2;//system on resize
    }
    private void countBonusChanceAndBloodBackAlpha(){

        int_numOfBricks=0;
        float_bonusChance=10.0f;
        for (int i=0,n=0,m=0,sp=0;i<6;i++){
            for (int j=3;j<13;j++){
                if(int_bricksArray2D[i][j]!=0){float_bonusChance += 0.020f;}//0.025 by default 0.01*60=0.6 = 5.6% chance if no stones

                if(int_bricksArray2D[i][j]==int_const_stoneBrickNumber||int_bricksArray2D[i][j]==int_const_stoneBrickNumber+8||
                        int_bricksArray2D[i][j]==int_const_stoneBrickNumber+9||int_bricksArray2D[i][j]==int_const_stoneBrickNumber+10){//if a stone or neg or pos or neu
                    for(int k=0;k<6;k++){//checking the stroke for other bricks
                        if(int_bricksArray2D[k][j]!=0){n++;}
                    }
                    if(j<5){
                        for(int k=0;k<6;k++){//checking the down stroke for other bricks
                            if(int_bricksArray2D[k][j+1]!=0){m++;}
                        }
                    }
                    if(m>3){sp+=0.03f;}
                    if(n>3){//if more than 3 blocks in the stroke
                        if(i>0&&i<5){sp+=0.007f;}//in near the center of the game field => increase chance
                        float_bonusChance+=(13-j)*0.018f+sp;
                    }
                 //   Gdx.app.log("'","j="+j+" n="+n+" bonus increase="+(13-j)*0.020f+sp);
                    n=0;m=0;sp=0;
                }//0-2(hidden field) 3-12(main field)

            }
        }
        //float_BloodBackgroundAlphaFresh = (float)(1.0f-(1.0f/Math.pow(3,Math.pow(((float_bonusChance-10.0f)/(0.020f*60)),2))));
        float_BloodBackgroundAlphaFresh = (float)(1.0-1.0/Math.pow(2,(float_bonusChance-10.0f)*0.5f));
        Gdx.app.log("'","bonus chance="+float_bonusChance+" alpha="+float_BloodBackgroundAlphaFresh);
        if(float_BloodBackgroundAlphaFresh<0.0f){float_BloodBackgroundAlphaFresh=0;}
        if(float_BloodBackgroundAlphaFresh>1.0f){float_BloodBackgroundAlphaFresh=1.0f;}
        float_bonusChance=10.0f/float_bonusChance;// (10/(10+0.02*60)-1)*(-100) chance% 0.02 max 10.7%=1.2+10   25.3%=3.4+10
        Gdx.app.log("'","10/bonus chance="+float_bonusChance);
        Gdx.app.log("'","---------------------------------");
     //   float_bonusChance=0.2f;//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    }
    @Override
    public void render () {

        //Gdx.app.log("'","GAME_STATE="+GAME_STATE);
        //Gdx.app.log("'","APPLYING_LIFE_STATE="+APPLYING_LIFE_STATE);

        SYSTEM_STATE=1;//system on render

        //counters
        if(float_deltaTimer<100.0f){float_deltaTimer+=Gdx.graphics.getDeltaTime();}else{float_deltaTimer=0.0f;}
        if(float_time<100.0f){float_time+=0.1f;}else{float_time=0.0f;}
        if(int_time<100){int_time+=1;}else{int_time=0;}
        if(float_saturation!=0.0f&&float_saturation<0.0f&&GAME_STATE!=7&&GAME_STATE!=0){
            float_saturation+=Gdx.graphics.getDeltaTime()*0.5;}//GAME_STATE==7 applying life
        else{
            if(float_saturation>1.0f){float_saturation=1.0f;}
            if(GAME_STATE==7&&float_saturation>-0.98f){float_saturation-=0.008f;}
        }
        if(GAME_STATE==7&&float_lifeBrickSaturation<0.0f){float_lifeBrickSaturation+=0.007f;}//applying life

        //Генератор фигуры шанс бонуса 10-макс
        if(GAME_FIG_GENERATED==0&&!bool_nextPrizeIsLife&&GAME_STATE!=-3){int_brickStone=0;int r=0, int_BigBrickType=0;

            //Подсчет кол-ва бриков и шанса на бонус (макс бриков 60)
            countBonusChanceAndBloodBackAlpha();

            float a = rand.nextFloat();
            //Gdx.app.log("'","a="+a+" float_bonusChance+b="+(float_bonusChance));
            if(a>float_bonusChance){
                r=0;GAME_STATE_GENBONUS=1;
            }
            else{
                r=rand.nextInt(27);
                if(r>=0&&r<8){r=0;}
                else{
                    if(r>=8&&r<16){r=1+rand.nextInt(2);}
                    else{
                        if(r>=16&&r<24){r=3+rand.nextInt(2);}
                        else{
                            if(r>=24){r=4+rand.nextInt(4);}
                        }}}
            }

            //brick2X2 & 3X3 -r- used for figure type
            if(GAME_STATE_GENBONUS==0&&rand.nextInt(80)==0){ //80
                if(rand.nextInt(5)==0){r=10;int_BigBrickType=rand.nextInt(tex_BricksAtlas3X3.getWidth()/180)+1;}//1-7 3x3 brick chance=5
                else{r=9;int_BigBrickType=rand.nextInt(tex_BricksAtlas2X2.getWidth()/120)+1;}//1-8 2x2 brick 7 - stone
                if(int_BigBrickType==7){int_BigBrickType=rand.nextInt(6)+1;}//if stone - remake!
                if(int_BigBrickType==8){int_BigBrickType=int_const_stoneBrickNumber+6;}//rainbow brick
            }

            //stones
            if(GAME_STATE_GENBONUS==0){// no stones for r==9 brick 2x2 & 3x3
                if(r==0){if(rand.nextInt(7)==0){int_brickStone=1;}}//1=100%,2=50%,3=25%,4=12.5%
                if(r==1){if(rand.nextInt(24)==0){int_brickStone=1;//24
                    if(bool_soundEnable){sfx_warning_0.setVolume(sfx_warning_0.play(),0.5f+float_volumeIncrement);
                    if(bool_4XStones){r=8;int_BigBrickType=7;}}}}//1=100%,2=50%,3=25%,4=12.5%
                if(r>=3&&r<5){if(rand.nextInt(10)==0){int_brickStone=1;}}//1=100%,2=50%,3=25%,4=12.5%
                if((r>=5&&r<9)||r==2){if(rand.nextInt(26)==0){int_brickStone=1;}}//1=100%,2=50%,3=25%,4=12.5%
            }
            //GAME_STATE_GENBONUS=1;r=0;
            //Запрос к базе фигур
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    /**  if(r==-1){
                     if(Figures.type1[i][j]==1){
                     int_nextBricksArray2D[i][j]=1;
                     }else{int_nextBricksArray2D[i][j]=0;}
                     }**/
                  //  r=0;GAME_STATE_GENBONUS=1;//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                  //   int_brickStone=1;//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                   // r=8;int_BigBrickType=7; //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                  //  int_specialBricksChance=1;
                    if(r==0){
                        if(Figures.type0[i][j]==1){
                            if(GAME_STATE_GENBONUS==1){
                                int bonus=rand.nextInt(38);
                                if(bonus>=0&&bonus<9){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+2;}//star
                                if(bonus>=9&&bonus<13){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+5;}//virus
                                if(bonus>=13&&bonus<21){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+1;}//bomb
                                if(bonus>=21&&bonus<29){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+3;}//lightning
                                if(bonus==29){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+4;}//viktor
                                if(bonus>=30&&bonus<38){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+7;int_paintBombNextColor=rand.nextInt(7);}//paint bomb
                                if(int_nextBricksArray2D[i][j]==int_const_stoneBrickNumber+4){
                                    if(bool_soundEnable){sfx_viktorSuperBonusStart.setVolume(sfx_viktorSuperBonusStart.play(),1.0f+float_volumeIncrement);}
                                }
                                /** put int_nextBricksArray2D[i][j] here for test supposes**/
                               //  int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+7;int_paintBombNextColor=rand.nextInt(7);//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                //int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+4;//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                GAME_STATE_GENBONUS=0;}//(по возрастанию редкости: lightning,bomb,star,viktor)
                            else{
                                if(int_brickStone!=1){
                                    if(rand.nextInt(int_rainbowBrickChance)==0){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+6;}//rainbow brick
                                    else{int_nextBricksArray2D[i][j]=genRandBrick();}
                                }
                                else{
                                    if(rand.nextInt(int_specialBricksChance)==0){int_nextBricksArray2D[i][j]=generateSpecialBrick(3);}// special bricks 0-pos,1-neu,2-neg
                                    else{int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber;}
                                }// if int_brickStone==1 => stones or pos,neg,neu bricks
                            }
                        }else{int_nextBricksArray2D[i][j]=0;}
                    }else{

                        Figures.getFigureType(r);

                        if(r!=9&&r!=10){//if not 2x2 or 3x3 big brick
                            if(Figures.typeN[i][j]==1){
                                if(int_brickStone!=1){
                                    if(rand.nextInt(int_rainbowBrickChance)==0){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+6;}//rainbow brick
                                    else{int_nextBricksArray2D[i][j]=genRandBrick();}
                                }
                                else{

                                    if(rand.nextInt(int_specialBricksChance)==0){int_nextBricksArray2D[i][j]= generateSpecialBrick(3);}// special bricks 0-pos,1-neu,2-neg
                                    else{int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber;}
                                }// if int_brickStone==1 => stones or pos,neg,neu bricks
                            }else{int_nextBricksArray2D[i][j]=0;}
                        }else{//if big brick 2X2 or 3x3
                            if(Figures.typeN[i][j]==1){
                                if(int_BigBrickType==8){int_nextBricksArray2D[i][j]=int_const_stoneBrickNumber+6;}//rainbow brick
                                else{int_nextBricksArray2D[i][j]=int_BigBrickType;}
                            }else{int_nextBricksArray2D[i][j]=0;}
                        }

                    }
                }
            }

        }else{
            if(bool_nextPrizeIsLife&&GAME_FIG_GENERATED==0){
                bool_nextPrizeIsLife=false;sfx_lifeGet_0.setVolume(sfx_lifeGet_0.play(),0.8f+float_volumeIncrement);
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        int_nextBricksArray2D[i][j]=0;
                    }
                }int_nextBricksArray2D[1][1]=888;
            }
        }
        GAME_FIG_GENERATED=1;

       // Gdx.app.log("'","APPLYING_LIFE_STATE= "+APPLYING_LIFE_STATE);
        //проигрыш при не пустом поле вставления новой фигуры Game Over
        if(GAME_STATE==1&&(GAME_STATE_BONUSTYPE<=int_const_stoneBrickNumber||GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+6||GAME_STATE_BONUSTYPE>=100)){/**important for new bricks!**/
            for(int i=0;i<6;i++){
                for(int j=0;j<3;j++){
                    if(int_bricksArray2D[i][j]!=0){

                        if(int_numOfLives==0&&GAME_STATE!=7){//if no life on the main field
                            GAME_STATE=-1;GAME_STATE_BF=GAME_STATE;
                            saveProgress();
                            GAME_STATE=-2;GAME_STATE_BF=GAME_STATE;//game over shader
                            float_gameOverShaderAlpha=0.0f;//shader alpha timer
                            if(bool_soundEnable){
                                music_theme.pause();
                                music_menu[int_music_menu_index].stop();
                                int_music_menu_index = rand.nextInt(music_menu.length);
                            }
                            //music_menu[int_music_menu_index].play();
                            Gdx.app.log("'","Game Over");
                        }else {//if there is life on the whole field (it can be in hidden field)
                            Gdx.app.log("'","Applying the life");
                            int_numOfLives=0;float_lifeBrickSaturation=-1.0f;
                            lifeApplyingTimer=0;
                            int_numOfRemovedBricksByLifeBonus=0;
                            int_numOfAllBricksForLifeBonus=0;
                            GAME_STATE=7;//applying the life
                            APPLYING_LIFE_STATE=0;
                            spr_Light0_0.setAlpha(1.0f);
                            spr_DisplayForShader_1.setColor(Color.BLACK);
                            spr_DisplayForShader_1.setAlpha(0.0f);

                            for (int n=0;n<spr_BigHeart.length;n++) {
                                spr_BigHeart[n].setPosition(spr_mainField.getX() + spr_mainField.getWidth() * 0.5f - spr_BigHeart[n].getWidth() * 0.5f,
                                        spr_mainField.getY() + spr_BigHeart[n].getHeight() + spr_mainField.getHeight() * 0.5f);
                            }
                            if(bool_soundEnable){
                                music_theme.pause();
                            }
                            saveProgress();
                        }
                    }
                }
            }if(GAME_STATE==-2){if(bool_soundEnable){sfx_gameOver_1.setVolume(sfx_gameOver_1.play(),1.5f+float_volumeIncrement);}}
            if(GAME_STATE==7){if(bool_soundEnable){sfx_applyingLife_0.setVolume(sfx_applyingLife_0.play(),1.0f+float_volumeIncrement);}}
        }

        //Обновление максимального счетчика
        if(int_curScore>int_highScore){
            if(!bool_highScoreBeaten&&int_highScore!=0){if(bool_soundEnable){music_highScoreBeaten.play();}bool_highScoreBeaten=true;}
            int_highScore=int_curScore;}

        //Копирование сгенерированной фигуры в массив падающей фигуры
        if(GAME_STATE==1&&GAME_FIG_GENERATED==1){
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    for(int k=0;k<3;k++){
                        //00 01 02
                        //10 11 12
                        //20 21 22
                        if(k==0){int_fallingBrickGPS[i][j][k]=i;}//i=0,1,2 - за границей рендринга
                        if(k==1){int_fallingBrickGPS[i][j][k]=j+2;}//j
                        if(k==2){int_fallingBrickGPS[i][j][k]=int_nextBricksArray2D[i][j];}//значение
                    }
                }
            }
            if(int_nextBricksArray2D[0][0]!=0&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[0][1]&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[1][0]&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[1][1]){// if 2x2 big brick
                int_brick2X2stage=1;int_brick3X3stage=0;
            }
            if(int_nextBricksArray2D[0][0]!=0&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[0][2]&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[0][1]){// if 3X3 big brick
                int_brick3X3stage=1;int_brick2X2stage=0;
            }//Checking only upper stroke!!!

            calculatePhysicsForGhost();
            hiddenBricksTimer=System.currentTimeMillis()+hiddenBricksTimerIncrement;
            GAME_STATE_BONUSTYPE=int_fallingBrickGPS[1][1][2];
            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+7){int_paintBombColor=int_paintBombNextColor;}
            GAME_FIG_GENERATED=0;GAME_STATE=2;
        }
/**-----------------------------------------------------------------CONTROLS----------------------------------------------------------------------------**/
        switch (checkButtonPress()){
            //control buttons
            case 0:break;
            case 1:if(GAME_STATE==2){bool_leftPressed=true;}break;
            case 2:if(GAME_STATE==2){bool_rightPressed=true;}break;
            case 3:if(GAME_STATE==2&&downPressedTimer<System.currentTimeMillis()){bool_downPressed=true;downPressedTimer=System.currentTimeMillis()+600;}break;
            case 4:if(GAME_STATE==2||GAME_STATE==3){bool_shufflePressed=true;}break;
            case 5:if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3){bool_pausePressed=true;
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            case 18:if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-2||GAME_STATE==-3){bool_helpPressed=true;
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            case 19:if(MENU_STATE==100){bool_backPressed=true;
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            //menu 0
            case 6:if(GAME_STATE==0){bool_menu_ReturnPressed=true;Gdx.app.log("'","Return pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            case 7:if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-2||GAME_STATE==-3){bool_menu_NewGamePressed=true;Gdx.app.log("'","NewGame pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            case 8:if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-2||GAME_STATE==-3){bool_menu_SettingsPressed=true;Gdx.app.log("'","Settings pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            case 9:if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-2||GAME_STATE==-3){bool_menu_ExitPressed=true;Gdx.app.log("'","Exit pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}}break;
            //y&n
            case 10:bool_menu_YesPressed=true;Gdx.app.log("'","Yes pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}break;
            case 11:bool_menu_NoPressed=true;Gdx.app.log("'","No pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}break;
            //settings
            case 12:bool_menu_AcceptPressed=true;Gdx.app.log("'","Accept pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}break;
            case 13:bool_menu_GhostPressed=true;Gdx.app.log("'","GhostSwitch pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}break;
            case 14:bool_menu_ChangeProfileNumbersPressed=true;Gdx.app.log("'","Change Profile Number pressed");
                sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);break;
            case 15:bool_menu_ChangeLanguagePressed=true;Gdx.app.log("'","Change Language pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}break;
            case 16:bool_menu_NextPressed=true;Gdx.app.log("'","Next pressed");
                if(bool_soundEnable){sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);}break;
            case 17:bool_menu_SoundPressed=true;Gdx.app.log("'","SoundSwitch pressed");
                sfx_menuButton_0.setVolume(sfx_menuButton_0.play(),0.3f+float_volumeIncrement);break;
            default:break;
        }

        if(bool_menu_NextPressed){bool_menu_NextPressed=false;
            MENU_STATE=32;
        }

        if(bool_menu_SoundPressed){bool_menu_SoundPressed=false;
            bool_soundEnable=!bool_soundEnable;
            if(bool_soundEnable&&GAME_STATE_BF!=7&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3){
                music_menu[int_music_menu_index].play();}
            else{music_menu[int_music_menu_index].stop();}
        }

        if(bool_menu_ChangeProfileNumbersPressed){bool_menu_ChangeProfileNumbersPressed=false;
            if(int_profileNumber<3){int_profileNumber+=1;}else{int_profileNumber=1;}
            loadProgress(false);

        }

        if(bool_menu_ChangeLanguagePressed){bool_menu_ChangeLanguagePressed=false;
            if(int_language==0){int_language=1;}else{int_language=0;}
            changeLanguageTextures();
            Gdx.app.log("'","Language="+int_language);
        }

        if(bool_menu_AcceptPressed){bool_menu_AcceptPressed=false;
            saveProgress();
            MENU_STATE=0;
            if(bool_soundEnable){music_menu[int_music_menu_index].play();}
            else{music_menu[int_music_menu_index].stop();}
        }

        if(bool_menu_GhostPressed){bool_menu_GhostPressed=false;
            bool_ghostEnabled=!bool_ghostEnabled;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    int_fallingBrickGPSghost[i][j][2]=0;
                }
            }
            calculatePhysicsForGhost();
        }

        if(bool_menu_YesPressed){bool_menu_YesPressed=false;
            if(MENU_STATE==1){//нов.игра - да
                //Обнуление полей
                for(int i=0;i<6;i++){
                    for(int j=0;j<13;j++){
                        int_bricksArray2D[i][j]=0;int_matchedBricksArray2D[i][j][0]=0;int_matchedBricksArray2D[i][j][1]=0;}}
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        int_nextBricksArray2D[i][j]=0;
                        for(int k=0;k<3;k++){int_fallingBrickGPS[i][j][k]=0;
                            if(bool_ghostEnabled){int_fallingBrickGPSghost[i][j][k]=0;}
                        }
                    }
                }

                GAME_STATE=1;GAME_STATE_BF=GAME_STATE;GAME_STATE_BONUSTYPE=0;GAME_STATE_COLLIDE=0;GAME_STATE_GENBONUS=0;GAME_STATE_PDF=0;GAME_FIG_GENERATED=0;
                APPLYING_LIFE_STATE=0;int_numOfAllBricksForLifeBonus=0;int_numOfRemovedBricksByLifeBonus=0;
                int_brick2X2stage=0;int_brick3X3stage=0;
                bool_highScoreBeaten=false;
                bool_hiddenTimerCondition=false;
                MENU_STATE=0;
                int_curScore=0;
                int_lifeScore=0;
                int_scoreAdded=0;
                bool_nextPrizeIsLife=false;
                countBonusChanceAndBloodBackAlpha();
                resetSpecialStats();
                processTimer=System.currentTimeMillis();
                if(bool_soundEnable){
                    music_theme.play();
                    sfx_gameStart_0.setVolume(sfx_gameStart_0.play(),0.6f+float_volumeIncrement);
                    music_menu[int_music_menu_index].stop();
                }
            }
            if(MENU_STATE==2){//выход - да
                MENU_STATE=0;saveProgress();Gdx.app.exit();
            }
        }

        if(bool_menu_NoPressed){bool_menu_NoPressed=false;
            if(MENU_STATE==1) {//нов.игра - нет
                MENU_STATE=0;
            }
            if(MENU_STATE==2) {//выход - нет
                MENU_STATE=0;
            }
        }

        if(bool_menu_NewGamePressed){bool_menu_NewGamePressed=false;//не чистое обнуление, но норм работает
            if(GAME_STATE==-1||GAME_STATE==-2||GAME_STATE==-3){bool_menu_YesPressed=true;}
            MENU_STATE=1;}

        if(bool_menu_ReturnPressed){bool_menu_ReturnPressed=false;
            GAME_STATE=GAME_STATE_BF;
            if(bool_soundEnable){
                if(GAME_STATE!=7){music_theme.play();}
                music_menu[int_music_menu_index].stop();
            }
            if(GAME_STATE==2){processTimer=System.currentTimeMillis()+lagTimerIncrement;}
            if(bool_hiddenTimerCondition){hiddenBricksTimer=System.currentTimeMillis()+hiddenBricksTimerIncrement;bool_hiddenTimerCondition=false;}
            if(!bool_soundEnable){soundMuteTimer=System.currentTimeMillis()+3000;}
        }

        if(bool_menu_SettingsPressed){bool_menu_SettingsPressed=false;
            MENU_STATE=31;
        }

        if(bool_menu_ExitPressed){bool_menu_ExitPressed=false;
            MENU_STATE=2;}

        if(bool_pausePressed){bool_pausePressed=false;
            GAME_STATE_BF = GAME_STATE;GAME_STATE=0;
            saveProgress();
            if(bool_soundEnable){music_theme.pause();
                music_menu[int_music_menu_index].stop();
                int_music_menu_index = rand.nextInt(music_menu.length);
                music_menu[int_music_menu_index].play();
            }
        }

        if(bool_helpPressed){bool_helpPressed=false;
            MENU_STATE=100;
            spr_Help[0].setPosition(spr_mainField.getX()+11,spr_mainField.getY()+11-spr_Help[0].getHeight()+602);
            spr_Help[1].setPosition(spr_Help[0].getX(),spr_Help[0].getY()-spr_Help[1].getHeight());
        }

        if(bool_backPressed){bool_backPressed=false;
            MENU_STATE=0;
        }

        if(bool_downPressed){bool_downPressed=false;//sfx_move_1.setVolume(sfx_move_1.play(),0.6f+float_volumeIncrement);

            if(GAME_STATE_BONUSTYPE<=int_const_stoneBrickNumber||GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+6||GAME_STATE_BONUSTYPE>=100){ /** important for new bricks**/
                float_saturation=-0.2f;}//isn't bonus

            if(int_brick2X2stage==1||int_brick3X3stage==1) {//2x2 or 3x3

                if(int_brick2X2stage==1){
                    for(int k=int_fallingBrickGPS[1][0][0];k<13;k++){
                        if(k+1<13&&int_bricksArray2D[int_fallingBrickGPS[1][0][1]][k+1]==0&&
                                int_bricksArray2D[int_fallingBrickGPS[1][1][1]][k+1]==0){
                            for(int i=0;i<3;i++){
                                for(int j=0;j<3;j++){
                                    int_fallingBrickGPS[i][j][0]+=1;}}
                        }else{break;}
                    }
                    int_brick2X2stage=-1;
                    if(bool_soundEnable){sfx_landed_3.setVolume(sfx_landed_3.play(),0.8f+float_volumeIncrement);}//2x2 down sfx
                }
                if(int_brick3X3stage==1){
                    for(int k=int_fallingBrickGPS[2][0][0];k<13;k++){
                        if(k+1<13&&int_bricksArray2D[int_fallingBrickGPS[2][0][1]][k+1]==0&&
                                int_bricksArray2D[int_fallingBrickGPS[2][1][1]][k+1]==0&&
                                int_bricksArray2D[int_fallingBrickGPS[2][2][1]][k+1]==0){
                            for(int i=0;i<3;i++){
                                for(int j=0;j<3;j++){
                                    int_fallingBrickGPS[i][j][0]+=1;}}
                        }else{break;}
                    }
                    int_brick3X3stage=-1;
                    if(bool_soundEnable){sfx_landed_2.setVolume(sfx_landed_2.play(),1.0f+float_volumeIncrement);}//3x3 down sfx
                }
                GAME_STATE=3;GAME_STATE_PDF=0;hiddenBricksTimer=0;
            }
            else{
                boolean rock=false;//rock sfx
                boolean magic=false;//magic sfx

                /** important for new bricks**/
                if(GAME_STATE_BONUSTYPE>int_const_stoneBrickNumber&&GAME_STATE_BONUSTYPE!=int_const_stoneBrickNumber+6&&GAME_STATE_BONUSTYPE<100){//bonus but not rainbow or special or magic
                    for(int j=int_fallingBrickGPS[1][1][0];j<13;j++){
                        if(j+1<13&&int_bricksArray2D[int_fallingBrickGPS[1][1][1]][j+1]==0){
                            int_fallingBrickGPS[1][1][0]+=1;}else{break;}
                    }
                }
                else{
                    //копирование падающей фигуры в основной массив
                    for(int i=0,i_fall,j_fall;i<3;i++){
                        for(int j=0;j<3;j++){
                            if(int_fallingBrickGPS[i][j][2]!=0){
                                if(int_fallingBrickGPS[i][j][2]==int_const_stoneBrickNumber||
                                        (int_fallingBrickGPS[i][j][2]>=100&&int_fallingBrickGPS[i][j][2]<200)){rock=true;}//if rock or special, but not magic
                                if(int_fallingBrickGPS[i][j][2]>=800&&int_fallingBrickGPS[i][j][2]<900){magic=true;}//if magic 800-899
                                i_fall=int_fallingBrickGPS[i][j][0];j_fall=int_fallingBrickGPS[i][j][1];
                                int_bricksArray2D[j_fall][i_fall]=int_fallingBrickGPS[i][j][2];
                                int_fallingBrickGPS[i][j][2]=0;
                            }
                        }
                    }
                    calculatePhysics();
                }
                GAME_STATE=4;GAME_STATE_PDF=0;
                hiddenBricksTimer=0;

                if(bool_soundEnable){
                    if(rock){int i=rand.nextInt(sfx_rockLanded.length);sfx_rockLanded[i].setVolume(sfx_rockLanded[i].play(),0.5f+float_volumeIncrement);}
                    if(magic){sfx_landed_4.setVolume(sfx_landed_4.play(),0.8f+float_volumeIncrement);}
                    if(!rock){sfx_landed_1.setVolume(sfx_landed_1.play(),0.8f+float_volumeIncrement);}
                }
            }
            lifeBonusCalculation();
        }

        if(bool_shufflePressed){bool_shufflePressed=false;
            if(bool_soundEnable){sfx_shuffle_0.setVolume(sfx_shuffle_0.play(),0.5f+float_volumeIncrement);}
            for(int i=0;i<3;i++){for(int j=0;j<3;j++){int_beforeShuffle[i][j]=int_fallingBrickGPS[i][j][2];}}//copy
            int max_order=0;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(int_fallingBrickGPS[i][j][2]!=0){
                        int_shuffleOrder[max_order]=byte_shuffleMask[i][j];max_order++;}
                }
            }
            for(int i=0,n;i<max_order;i++){n=int_shuffleOrder[i];
                for(int j=0;j<max_order;j++){
                    if(n<int_shuffleOrder[j]){int_shuffleOrder[i]=int_shuffleOrder[j];int_shuffleOrder[j]=n;n=int_shuffleOrder[i];}//лист по возрастанию
                }
            }

            if(max_order>1){
                for(int n=0;n<max_order;n+=1){
                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            if(int_fallingBrickGPS[i][j][2]!=0&&byte_shuffleMask[i][j]==int_shuffleOrder[n]){
                                for(int ik=0;ik<3;ik++){
                                    for(int jk=0;jk<3;jk++){
                                        if(n==0&&byte_shuffleMask[ik][jk]==int_shuffleOrder[max_order-1]){
                                            int_fallingBrickGPS[i][j][2]=int_beforeShuffle[ik][jk];i=3;j=3;ik=3;jk=3;}//first element
                                        else{
                                            if(n-1>=0&&byte_shuffleMask[ik][jk]==int_shuffleOrder[n-1]){
                                                int_fallingBrickGPS[i][j][2]=int_beforeShuffle[ik][jk];i=3;j=3;ik=3;jk=3;}//others
                                            else{
                                                if(n==max_order-1&&byte_shuffleMask[ik][jk]==int_shuffleOrder[n-1]){
                                                    int_fallingBrickGPS[i][j][2]=int_beforeShuffle[ik][jk];i=3;j=3;ik=3;jk=3;}//last element
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            calculatePhysicsForGhost();
        }

        if(bool_leftPressed){bool_leftPressed=false;int pos_con=1;

            for(int i=0,i_fall,j_fall;i<3;i++){
                for(int j=0;j<3;j++){
                    i_fall=int_fallingBrickGPS[i][j][0];j_fall=int_fallingBrickGPS[i][j][1];
                    if((i_fall>=0&&j_fall>0&&int_fallingBrickGPS[i][j][2]!=0&&int_bricksArray2D[j_fall-1][i_fall]==0)
                            ||(int_fallingBrickGPS[i][j][2]==0)){//?
                    }else{pos_con=0;}
                }
            }
            if(pos_con==1){if(bool_soundEnable){sfx_move_0.setVolume(sfx_move_0.play(),0.6f+float_volumeIncrement);}
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        int_fallingBrickGPS[i][j][1]-=1;
                    }
                }calculatePhysicsForGhost();
            }else{if(bool_soundEnable){sfx_move_2.setVolume(sfx_move_2.play(),0.6f+float_volumeIncrement);}
                spr_sideBorderLight[0].setAlpha(1.0f);}
        }

        if(bool_rightPressed){bool_rightPressed=false;int pos_con=1;

            for(int i=0,i_fall,j_fall;i<3;i++){
                for(int j=0;j<3;j++){
                    i_fall=int_fallingBrickGPS[i][j][0];j_fall=int_fallingBrickGPS[i][j][1];
                    if((i_fall>=0&&j_fall<5&&int_fallingBrickGPS[i][j][2]!=0&&int_bricksArray2D[j_fall+1][i_fall]==0)
                            ||(int_fallingBrickGPS[i][j][2]==0)){//?
                    }else{pos_con=0;}
                }
            }
            if(pos_con==1){if(bool_soundEnable){sfx_move_0.setVolume(sfx_move_0.play(),0.6f+float_volumeIncrement);}
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        int_fallingBrickGPS[i][j][1]+=1;
                    }
                }calculatePhysicsForGhost();
            }else{if(bool_soundEnable){sfx_move_2.setVolume(sfx_move_2.play(),0.6f+float_volumeIncrement);}
                spr_sideBorderLight[1].setAlpha(1.0f);}
        }
/**-----------------------------------------------------------------CONTROLS_END-------------------------------------------------------------------------**/


        //Падение управляемой фигуры
        if(processTimer+velocityTimerIncrement<System.currentTimeMillis()&&GAME_STATE==2&&hiddenBricksTimer<System.currentTimeMillis()){
            GAME_STATE_COLLIDE=0;
            for(int i=0,i_fall,j_fall;i<3;i++){
                for(int j=0;j<3;j++){
                    i_fall=int_fallingBrickGPS[i][j][0];j_fall=int_fallingBrickGPS[i][j][1];
                    if(i_fall==12&&int_fallingBrickGPS[i][j][2]!=0){GAME_STATE_COLLIDE=1;}//столконовение с полом
                    else{
                        if(GAME_STATE!=3&&int_fallingBrickGPS[i][j][2]!=0&&i_fall<12
                                &&int_bricksArray2D[j_fall][i_fall+1]!=0){//столконовение с фигурой
                            GAME_STATE_COLLIDE=1;
                        }
                    }
                }
            }

            //Задержка после столконовения для управления
            if(GAME_STATE_COLLIDE==1){
                if(GAME_STATE_PDF==0){GAME_STATE_PDF=1;}
                else{
                    if(GAME_STATE_PDF==5){GAME_STATE=3;GAME_STATE_PDF=0;}
                    else{
                        if(GAME_STATE_PDF!=0){GAME_STATE_PDF+=1;processTimer=System.currentTimeMillis()+100;}
                    }
                }
            }else{
                if(GAME_STATE_PDF!=0){GAME_STATE_PDF=0;}
            }

            //инкремент координаты i падающей фигуры
            if(GAME_STATE==2&&GAME_STATE_PDF==0&&GAME_STATE_COLLIDE==0){
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        int_fallingBrickGPS[i][j][0]+=1;}}
                if(bool_soundEnable){sfx_tick_0.setVolume(sfx_tick_0.play(),0.5f+float_volumeIncrement);}
                     processTimer=System.currentTimeMillis()+velocityTimerIncrement;//speed lag
            }
            //if(GAME_STATE==3){processTimer=System.currentTimeMillis()+2000;}//задержка для shuffle, после столкновения

        }

        //Фигура упала (препятствие)
        if(GAME_STATE==3&&processTimer<System.currentTimeMillis()){

            if((int_brick2X2stage==0||int_brick2X2stage==4)&&(int_brick3X3stage==0||int_brick3X3stage==4)){// 2x2 or 3x3
                //копирование падающей фигуры в основной массив
                if(GAME_STATE_BONUSTYPE<=int_const_stoneBrickNumber||GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+6||GAME_STATE_BONUSTYPE>=100){ /** important for new bricks **/
                    for(int i=0,i_fall,j_fall;i<3;i++){
                        for(int j=0;j<3;j++){
                            if(int_fallingBrickGPS[i][j][2]!=0){
                                i_fall=int_fallingBrickGPS[i][j][0];j_fall=int_fallingBrickGPS[i][j][1];
                                int_bricksArray2D[j_fall][i_fall]=int_fallingBrickGPS[i][j][2];
                                int_fallingBrickGPS[i][j][2]=0;
                            }
                        }
                    }
                    if(int_brick2X2stage==0&&int_brick3X3stage==0){
                        if(bool_soundEnable){
                            if(GAME_STATE_BONUSTYPE>=800&&GAME_STATE_BONUSTYPE<900){ sfx_landed_4.setVolume(sfx_landed_4.play(),0.8f+float_volumeIncrement);}
                            sfx_landed_0.setVolume(sfx_landed_0.play(),0.5f+float_volumeIncrement);
                        }
                    } //sfx when 2x2 or 3x3 fully cutted on pieces
                }
                GAME_STATE=4;processTimer=System.currentTimeMillis()+lagTimerIncrement;
                if(int_brick2X2stage==4||int_brick3X3stage==4){int_brick2X2stage=5;int_brick3X3stage=5;}
                spr_LaserCutter2X2[1].setAlpha(0.0f);spr_LaserCutter3X3[1].setAlpha(0.0f);
            }else{
                if(int_brick2X2stage==1||int_brick2X2stage==-1||int_brick3X3stage==1||int_brick3X3stage==-1){

                    if(int_brick2X2stage!=-1&&int_brick3X3stage==0){if(bool_soundEnable){sfx_landed_0.setVolume(sfx_landed_0.play(),0.5f+float_volumeIncrement);}}//fell without down btn
                    if(int_brick2X2stage!=0&&int_brick3X3stage==0){int_brick2X2stage=2;spr_LaserCutter2X2[1].setAlpha(0.0f);
                    if(bool_soundEnable){sfx_laserCut_0.setVolume(sfx_laserCut_0.play(),0.5f+float_volumeIncrement);}}

                    if(int_brick3X3stage!=-1&&int_brick2X2stage==0){if(bool_soundEnable){sfx_landed_0.setVolume(sfx_landed_0.play(),0.5f+float_volumeIncrement);}}//fell without down btn
                    if(int_brick3X3stage!=0&&int_brick2X2stage==0){int_brick3X3stage=2;spr_LaserCutter3X3[1].setAlpha(0.0f);
                    if(bool_soundEnable){sfx_laserCut_0.setVolume(sfx_laserCut_0.play(),0.5f+float_volumeIncrement);}}
                }
            }
            lifeBonusCalculation();
        }
/*
        Gdx.app.log("'","int_lifeBrickDamage "+int_lifeBrickDamage);
        Gdx.app.log("'","int_numOflives "+int_numOfLives);
        Gdx.app.log("'","int_lifeScore "+int_lifeScore);*/

        //Удаление, совпавшие, Физика(1)
        if(GAME_STATE==4&&processTimer<System.currentTimeMillis()){

            //bonuses
            switch (GAME_STATE_BONUSTYPE){

                case int_const_stoneBrickNumber+1:/** STANDARD BOMB BONUS **/
                    for(int i=int_fallingBrickGPS[1][1][1]-1;i<int_fallingBrickGPS[1][1][1]+2;i++){
                        for(int j=int_fallingBrickGPS[1][1][0]-1;j<int_fallingBrickGPS[1][1][0]+2;j++){
                            if(i>=0&&i<6&&j>=0&&j<13&&int_bricksArray2D[i][j]!=0){

                                if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                if(int_bricksArray2D[i][j]!=888||(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2)){//life remove condition
                                    int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect
                                    if(int_bricksArray2D[i][j]==888){
                                        //particles
                                        if(j>2){
                                            ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                            pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                            array_prc_effects_5.add(pooledEffect_5);
                                        }
                                        sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                    }
                                    int_bricksArray2D[i][j]=0;
                                    brickAnnihilationTimer=System.currentTimeMillis()+200;//!!! increment must be lower than lagTimerIncrement !!!
                                    int_curScore++;int_scoreAdded++;int_lifeScore++;
                                }

                            }
                        }
                    }
                    if(int_scoreAdded!=0){int_scoreAddedRendering = int_scoreAdded;int_scoreAdded=0;
                        scoreDisplayTimer=System.currentTimeMillis()+scoreDisplayTimerIncrement;}

                    bool_bombExploded=true;
                    if(bool_soundEnable){sfx_collapseBonus_1.setVolume(sfx_collapseBonus_1.play(),1.0f+float_volumeIncrement);}
                    GAME_STATE=6;//задержка для физики
                    processTimer=System.currentTimeMillis()+lagTimerIncrement;
                    GAME_STATE_BONUSTYPE=0;break;

                case int_const_stoneBrickNumber+2:/** STAR BONUS **/
                    int bt;
                    if(int_fallingBrickGPS[1][1][0]+1<13
                            &&int_bricksArray2D[int_fallingBrickGPS[1][1][1]][int_fallingBrickGPS[1][1][0]+1]!=0){
                        bt=int_bricksArray2D[int_fallingBrickGPS[1][1][1]][int_fallingBrickGPS[1][1][0]+1];

                        if(bt!=int_const_stoneBrickNumber+6&&bt!=888){//if isn't rainbow or magic life
                            for(int i=0;i<6;i++){
                                for(int j=0;j<13;j++){
                                    if(int_bricksArray2D[i][j]==bt){
                                            //particles
                                            if(j>2){
                                                ParticleEffectPool.PooledEffect pooledEffect_0 = particleEffectPool_0.obtain();
                                                pooledEffect_0.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                array_prc_effects_0.add(pooledEffect_0);
                                                if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_0.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                                else{array_prc_removeEffects_0.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                                            }
                                            int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect
                                            int_bricksArray2D[i][j]=0;
                                            brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
                                            int_curScore++;int_scoreAdded++;int_lifeScore++;

                                    }//удаляем все того же цвета, что под звездой
                                }
                            }
                        }else{
                            if(bt==int_const_stoneBrickNumber+6){//if is rainbow
                                //удаляем все того же цвета, что под звездой, а также несколько случайных
                                for(int i=0;i<6;i++){
                                    for(int j=0;j<13;j++){
                                        if(int_bricksArray2D[i][j]==bt||rand.nextInt(4)==0){

                                            if(int_bricksArray2D[i][j]!=0){

                                                if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                                if(int_bricksArray2D[i][j]!=888||(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2)){//+life remove condition
                                                    int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect

                                                    //particles
                                                    if(j>2){
                                                        ParticleEffectPool.PooledEffect pooledEffect_0 = particleEffectPool_0.obtain();
                                                        pooledEffect_0.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                        array_prc_effects_0.add(pooledEffect_0);
                                                        if(int_bricksArray2D[i][j]>=100){
                                                            array_prc_removeEffects_0.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                                        else{array_prc_removeEffects_0.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                                                    }

                                                    //particles if life destroyed
                                                    if(int_bricksArray2D[i][j]==888){
                                                        if(j>2){
                                                            ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                                            pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                            array_prc_effects_5.add(pooledEffect_5);
                                                        }
                                                        sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                                    }

                                                    int_bricksArray2D[i][j]=0;
                                                    brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
                                                    int_curScore++;int_scoreAdded++;int_lifeScore++;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                            if(bt==888){//if is magic life brick
                                    for(int i=0;i<6;i++){
                                        for(int j=0;j<13;j++){


                                            if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                            if(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2){//life remove condition
                                                int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect

                                                //particles
                                                if(j>2){
                                                    ParticleEffectPool.PooledEffect pooledEffect_0 = particleEffectPool_0.obtain();
                                                    pooledEffect_0.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                    array_prc_effects_0.add(pooledEffect_0);
                                                    if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_0.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                                    else{array_prc_removeEffects_0.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                                                }
                                                //particles if destroyed
                                                if(j>2){
                                                    ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                                    pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                    array_prc_effects_5.add(pooledEffect_5);
                                                }
                                                sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);

                                                int_bricksArray2D[i][j]=0;
                                                brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
                                                int_curScore++;int_scoreAdded++;int_lifeScore++;
                                            }

                                    }

                                }
                            }

                        }
                    }else{//если в пустоту, то  камни удаляем
                        for(int i=0;i<6;i++){
                            for(int j=0;j<13;j++){
                                if(int_bricksArray2D[i][j]==int_const_stoneBrickNumber){

                                    //particles
                                    if(j>2){
                                        ParticleEffectPool.PooledEffect pooledEffect_0 = particleEffectPool_0.obtain();
                                        pooledEffect_0.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                        array_prc_effects_0.add(pooledEffect_0);
                                        if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_0.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                        else{array_prc_removeEffects_0.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                                    }
                                        int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect
                                        int_bricksArray2D[i][j]=0;

                                    brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
                                    int_curScore++;int_scoreAdded++;int_lifeScore++;
                                }
                            }
                        }
                    }
                    if(int_scoreAdded!=0){int_scoreAddedRendering = int_scoreAdded;int_scoreAdded=0;
                        scoreDisplayTimer=System.currentTimeMillis()+scoreDisplayTimerIncrement;}
                    bool_starExploded=true;
                    if(bool_soundEnable){sfx_collapseBonus_0.setVolume(sfx_collapseBonus_0.play(),1.0f+float_volumeIncrement);}
                    GAME_STATE=6;//задержка для физики
                    processTimer=System.currentTimeMillis()+lagTimerIncrement;
                    GAME_STATE_BONUSTYPE=0;break;

                case int_const_stoneBrickNumber+3:/** LIGHTNING BONUS **/

                    int_lightningRow = int_fallingBrickGPS[1][1][1];//затрагиваемый ряд (для анимации)
                    for(int j=int_fallingBrickGPS[1][1][0],i=int_fallingBrickGPS[1][1][1];j<13;j++){

                        if(int_bricksArray2D[i][j]!=0){

                            if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                            if(int_bricksArray2D[i][j]!=888||(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2)){//+life remove condition
                                int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect

                                //particles
                                if(j>2){
                                    ParticleEffectPool.PooledEffect pooledEffect_1 = particleEffectPool_1.obtain();
                                    pooledEffect_1.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                    array_prc_effects_1.add(pooledEffect_1);
                                    if(int_bricksArray2D[i][j]>=100){
                                        array_prc_removeEffects_1.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                    else{array_prc_removeEffects_1.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                                }

                                //particles if life destroyed
                                if(int_bricksArray2D[i][j]==888){
                                    if(j>2){
                                        ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                        pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                        array_prc_effects_5.add(pooledEffect_5);
                                    }
                                    sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                }

                                int_bricksArray2D[i][j]=0;
                                brickAnnihilationTimer=System.currentTimeMillis()+150;//!!! increment must be lower than lagTimerIncrement !!!
                                int_curScore++;int_scoreAdded++;int_lifeScore++;
                            }

                        }
                    }//удаление столбца
                    if(int_scoreAdded!=0){int_scoreAddedRendering = int_scoreAdded;int_scoreAdded=0;
                        scoreDisplayTimer=System.currentTimeMillis()+scoreDisplayTimerIncrement;}
                    bool_lightningExploded=true;
                    if(bool_soundEnable){sfx_collapseBonus_2.setVolume(sfx_collapseBonus_2.play(),1.0f+float_volumeIncrement);}
                    GAME_STATE=6;//задержка для физики
                    processTimer=System.currentTimeMillis()+lagTimerIncrement;
                    GAME_STATE_BONUSTYPE=0;break;

                case int_const_stoneBrickNumber+4:/** VIKTOR BOMB BONUS **/
                    for(int i=int_fallingBrickGPS[1][1][1]-3;i<int_fallingBrickGPS[1][1][1]+4;i++){
                        for(int j=int_fallingBrickGPS[1][1][0]-3;j<int_fallingBrickGPS[1][1][0]+4;j++){
                            if(i>=0&&i<6&&j>=0&&j<13&&int_bricksArray2D[i][j]!=0){

                                if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                if(int_bricksArray2D[i][j]!=888||(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2)){//+life remove condition
                                    int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect

                                    //particles
                                    if(j>2){
                                        ParticleEffectPool.PooledEffect pooledEffect_2 = particleEffectPool_2.obtain();
                                        pooledEffect_2.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                        array_prc_effects_2.add(pooledEffect_2);
                                        if(int_bricksArray2D[i][j]>=100){
                                            array_prc_removeEffects_2.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                        else{array_prc_removeEffects_2.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                                    }

                                    //particles if life destroyed
                                    if(int_bricksArray2D[i][j]==888){
                                        if(j>2){
                                            ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                            pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                            array_prc_effects_5.add(pooledEffect_5);
                                        }
                                        sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                    }

                                    int_bricksArray2D[i][j]=0;
                                    brickAnnihilationTimer=System.currentTimeMillis()+200;//!!! increment must be lower than lagTimerIncrement !!!
                                    int_curScore++;int_scoreAdded++;int_lifeScore++;
                                }

                            }
                        }
                    }
                    if(int_scoreAdded!=0){int_scoreAddedRendering = int_scoreAdded;int_scoreAdded=0;
                        scoreDisplayTimer=System.currentTimeMillis()+scoreDisplayTimerIncrement;}
                    if(bool_soundEnable){sfx_collapseBonus_3.setVolume(sfx_collapseBonus_3.play(),0.7f+float_volumeIncrement);}
                    GAME_STATE=6;//задержка для физики

                    bool_viktorBombExploded = true;
                    float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                    spr_ShatteredGlassAfterExplosion_1.setPosition(float_posXY[0]-spr_ShatteredGlassAfterExplosion_1.getWidth()*0.5f-10.0f,
                            float_posXY[1]-spr_ShatteredGlassAfterExplosion_1.getHeight()*0.5f-20.0f);
                    //spr_ShatteredGlassAfterExplosion_1.setRotation(rand.nextInt(360));
                    spr_ShatteredGlassAfterExplosion_1.setAlpha(1.0f);

                    processTimer=System.currentTimeMillis()+lagTimerIncrement;
                    spr_afterExplosion.setAlpha(1.0f);
                    spr_afterExplosionLight.setAlpha(1.0f);
                    afterExplosionTimer=2000+System.currentTimeMillis();

                    GAME_STATE_BONUSTYPE=0;break;

                case int_const_stoneBrickNumber+5:/** VIRUS BONUS **/
                    int bv,bs;
                    if(int_fallingBrickGPS[1][1][0]+1<13
                            &&int_bricksArray2D[int_fallingBrickGPS[1][1][1]][int_fallingBrickGPS[1][1][0]+1]!=0){
                        bv=int_bricksArray2D[int_fallingBrickGPS[1][1][1]][int_fallingBrickGPS[1][1][0]+1];

                        if(bv!=int_const_stoneBrickNumber+6&&bv!=888){// if isn't rainbow or magic life
                            do{bs=rand.nextInt(int_const_stoneBrickNumber-1)+1;}while(bv==bs);
                            for(int i=0;i<6;i++){
                                for(int j=0;j<13;j++){
                                    if(int_bricksArray2D[i][j]==bv){int_bricksArray2D[i][j]=bs; // меняем все того же цвета, что под вирусом

                                        //particles
                                        if(j>2){
                                            ParticleEffectPool.PooledEffect pooledEffect_3 = particleEffectPool_3.obtain();
                                            pooledEffect_3.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                            array_prc_effects_3.add(pooledEffect_3);
                                            if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_3.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                            else{array_prc_removeEffects_3.add(getSolidBrickColor(int_bricksArray2D[i][j]));}//for color
                                        }
                                    }
                                }
                            }
                        }else {
                            if(bv==int_const_stoneBrickNumber+6){// if is rainbow
                                    for(int i=0;i<6;i++){
                                        for(int j=0;j<13;j++){
                                            if(int_bricksArray2D[i][j]!=0){

                                                if(int_bricksArray2D[i][j]==int_const_stoneBrickNumber&&rand.nextInt((int)(int_specialBricksChance*0.25f))==0){//if stone than increased chance to special
                                                    int_bricksArray2D[i][j]= generateSpecialBrick(3);}//special to
                                                else{
                                                    if(rand.nextInt(int_rainbowBrickChance)==0){//меняем все, если упал на rainbow
                                                    int_bricksArray2D[i][j]=int_const_stoneBrickNumber+6;
                                                    }
                                                    else{

                                                        if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                                        if(int_bricksArray2D[i][j]!=888||(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2)){//+life remove condition
                                                           // int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect

                                                            //particles
                                                            if(j>2){
                                                                ParticleEffectPool.PooledEffect pooledEffect_3 = particleEffectPool_3.obtain();
                                                                pooledEffect_3.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                                array_prc_effects_3.add(pooledEffect_3);
                                                                if(int_bricksArray2D[i][j]>=100){
                                                                    array_prc_removeEffects_3.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                                                else{array_prc_removeEffects_3.add(getSolidBrickColor(int_bricksArray2D[i][j]));}//for color
                                                            }

                                                            //particles if life destroyed
                                                            if(int_bricksArray2D[i][j]==888){
                                                                if(j>2){
                                                                    ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                                                    pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                                    array_prc_effects_5.add(pooledEffect_5);
                                                                }
                                                                sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                                            }

                                                            int_bricksArray2D[i][j]=rand.nextInt(int_const_stoneBrickNumber)+1;
                                                            int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect
                                                            brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
                                                          //  int_curScore++;int_scoreAdded++;int_lifeScore++;
                                                        }


                                                    }
                                                }

                                                }

                                        }
                                    }
                                }

                            if(bv==888){//if is magic life brick
                                bs=rand.nextInt(int_const_stoneBrickNumber-1)+1;
                                for(int i=0;i<6;i++){
                                    for(int j=0;j<13;j++){

                                        if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                        if(int_bricksArray2D[i][j]==888&&int_lifeBrickDamage>2){//life remove condition
                                           // int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect

                                            //particles
                                            if(j>2){
                                                ParticleEffectPool.PooledEffect pooledEffect_3 = particleEffectPool_3.obtain();
                                                pooledEffect_3.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                array_prc_effects_3.add(pooledEffect_3);
                                                if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_3.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                                else{array_prc_removeEffects_3.add(getSolidBrickColor(int_bricksArray2D[i][j]));}//for color
                                            }
                                            //particles if destroyed
                                            if(j>2){
                                                ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                                pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                array_prc_effects_5.add(pooledEffect_5);
                                            }
                                            sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);

                                            int_bricksArray2D[i][j]=bs;
                                            int_matchedBricksArray2D[i][j][0]=int_bricksArray2D[i][j];//for alpha effect
                                            brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
                                          //  int_curScore++;int_scoreAdded++;int_lifeScore++;
                                        }

                                    }

                                }
                            }

                        }

                    }else{
                        bs=rand.nextInt(int_const_stoneBrickNumber-1)+1;
                        for(int i=0;i<6;i++){
                            for(int j=0;j<13;j++){
                                if(int_bricksArray2D[i][j]==int_const_stoneBrickNumber){int_bricksArray2D[i][j]=bs; // если в пустоту, то  камни меняем
                                    //particles
                                    if(j>2){
                                        ParticleEffectPool.PooledEffect pooledEffect_3 = particleEffectPool_3.obtain();
                                        pooledEffect_3.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                        array_prc_effects_3.add(pooledEffect_3);
                                        if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_3.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                                        else{array_prc_removeEffects_3.add(getSolidBrickColor(int_bricksArray2D[i][j]));}//for color
                                    }
                                }
                            }
                        }
                    }
                    if(bool_soundEnable){sfx_collapseBonus_4.setVolume(sfx_collapseBonus_4.play(),1.5f+float_volumeIncrement);}
                    GAME_STATE=6;//задержка для физики
                    processTimer=System.currentTimeMillis()+lagTimerIncrement;
                    GAME_STATE_BONUSTYPE=0;break;

                case int_const_stoneBrickNumber+7:/** PAINT BOMB BONUS**/
                    for(int i=int_fallingBrickGPS[1][1][1]-1;i<int_fallingBrickGPS[1][1][1]+2;i++){
                        for(int j=int_fallingBrickGPS[1][1][0]-1;j<int_fallingBrickGPS[1][1][0]+2;j++){
                            if(i>=0&&i<6&&j>=0&&j<13&&int_bricksArray2D[i][j]!=0){
                                if(int_bricksArray2D[i][j]==888){int_lifeBrickDamage+=1;}
                                if(int_paintBombColor<6){
                                    if(int_bricksArray2D[i][j]!=888){int_bricksArray2D[i][j]=int_paintBombColor+1;}
                                    else{
                                        if(int_lifeBrickDamage>2){int_bricksArray2D[i][j]=int_paintBombColor+1;
                                            //particles if destroyed
                                            if(j>2){
                                                ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                                pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                array_prc_effects_5.add(pooledEffect_5);
                                            }
                                            sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                        }
                                    }//if magic life
                                }//int_paintBombColor<=6 but +1 for brick comp
                                else{if(int_paintBombColor==6){
                                    if(int_bricksArray2D[i][j]!=888){ int_bricksArray2D[i][j]=int_const_stoneBrickNumber+6;}
                                    else{
                                        if(int_lifeBrickDamage>2){ int_bricksArray2D[i][j]=int_const_stoneBrickNumber+6;
                                            if(j>2){
                                                ParticleEffectPool.PooledEffect pooledEffect_5 = particleEffectPool_5.obtain();
                                                pooledEffect_5.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                                array_prc_effects_5.add(pooledEffect_5);
                                            }
                                            sfx_lifeDied_0.setVolume(sfx_lifeDied_0.play(),0.8f+float_volumeIncrement);
                                        }
                                    }//if magic life
                                    }//rainbow

                                }
                            }
                        }
                    }

                    bool_paintBombExploded=true;
                    if(bool_soundEnable){sfx_paintBomb_0.setVolume(sfx_paintBomb_0.play(),1.0f+float_volumeIncrement);}
                    GAME_STATE=6;//задержка для физики
                    processTimer=System.currentTimeMillis()+lagTimerIncrement;
                    GAME_STATE_BONUSTYPE=0;break;

                default:GAME_STATE_BONUSTYPE=0;break;
            }

            if(GAME_STATE!=6){

                if(int_noPhysics==0){//условие для оптимизации
                    //"Физика" поля
                    calculatePhysics();
                }else{int_noPhysics=0;}

                for(int i=0;i<6;i++){//clean up int_matchedBricksArray2D because it's used for alpha effects to
                    for(int j=0;j<13;j++){
                        int_matchedBricksArray2D[i][j][0]=0;
                    }
                }

                //Поиск совпавших
                for(int ps=1,p=0;ps<int_const_stoneBrickNumber+1;ps++){//check every type of usual bricks separately for rainbow brick
                    p=ps;
                    if(ps==int_const_stoneBrickNumber){p=int_const_stoneBrickNumber+6;}//rainbow
                    int[][] int_copyBricksArray2D = new int[6][13];
                    for(int i=0;i<6;i++){
                        for(int j=0;j<13;j++){
                            if(int_bricksArray2D[i][j]==p || int_bricksArray2D[i][j]==int_const_stoneBrickNumber+6){
                                int_copyBricksArray2D[i][j]=p;
                            }
                        }
                    }
                    //вертикальные
                    for(int i=0,m=0,rc=0;i<6;i++){
                        for(int j=0;j<13;j++){
                            if(int_copyBricksArray2D[i][j]!=0&&int_copyBricksArray2D[i][j]!=int_const_stoneBrickNumber&&j+1<13
                                    &&int_copyBricksArray2D[i][j+1]==int_copyBricksArray2D[i][j]){
                                if(p!=int_const_stoneBrickNumber+6&&
                                        (int_bricksArray2D[i][j]!=int_const_stoneBrickNumber+6||int_bricksArray2D[i][j+1]!=int_const_stoneBrickNumber+6)
                                        &&rc==0){rc=1;}
                                m+=1;}
                            else{
                                if(m>=2&&(rc==1||p==int_const_stoneBrickNumber+6)){for(int b=0;b<=m;b++){
                                    int_matchedBricksArray2D[i][j-b][0]=int_copyBricksArray2D[i][j-b];
                                    if(int_bricksArray2D[i][j-b]==int_const_stoneBrickNumber+6){
                                        int_matchedBricksArray2D[i][j-b][1]=int_const_stoneBrickNumber+6;}//сохраняем инфо о том, что это rainbow
                                    else{int_matchedBricksArray2D[i][j-b][1]=0;}
                                }
                                    m=0;rc=0;if(GAME_STATE!=5){GAME_STATE=5;}
                                    if(int_comboTimesMultiplier<10){int_comboTimesMultiplier+=1;}
                                }
                                else{m=0;rc=0;}
                            }
                        }
                    }
                    //горизонтальные
                    for(int j=0,m=0,rc=0;j<13;j++){
                        for(int i=0;i<6;i++){
                            if(int_copyBricksArray2D[i][j]!=0&&int_copyBricksArray2D[i][j]!=int_const_stoneBrickNumber&&i+1<6
                                    &&int_copyBricksArray2D[i+1][j]==int_copyBricksArray2D[i][j]){
                                if(p!=int_const_stoneBrickNumber+6&&
                                        (int_bricksArray2D[i][j]!=int_const_stoneBrickNumber+6||int_bricksArray2D[i+1][j]!=int_const_stoneBrickNumber+6)
                                        &&rc==0){rc=1;}
                                m+=1;}
                            else{
                                if(m>=2&&(rc==1||p==int_const_stoneBrickNumber+6)){for(int b=0;b<=m;b++){
                                    int_matchedBricksArray2D[i-b][j][0]=int_copyBricksArray2D[i-b][j];
                                    if(int_bricksArray2D[i-b][j]==int_const_stoneBrickNumber+6){
                                        int_matchedBricksArray2D[i-b][j][1]=int_const_stoneBrickNumber+6;}//сохраняем инфо о том, что это rainbow
                                    else{int_matchedBricksArray2D[i-b][j][1]=0;}
                                }
                                    m=0;rc=0;if(GAME_STATE!=5){GAME_STATE=5;}
                                    if(int_comboTimesMultiplier<10){int_comboTimesMultiplier+=1;}
                                }
                                else{m=0;rc=0;}
                            }
                        }
                    }
                    //диагональные снизу вверх (старт слева сверху) слева направо
                    for(int k=0,i=0,j=0,imin=0,imax=2,jmin=2,jmax=0,m=0,rc=0;k<14;k++){
                        if(k<4){imin=0;imax=2+k;jmin=0;jmax=2+k;i=imin;j=jmax;}
                        if(k>=4&&k<11){imin=0;imax=5;jmin=k-3;jmax=k+2;i=imin;j=jmax;}
                        if(k>=11){imin=k-10;imax=5;jmin=k-3;jmax=12;i=imin;j=jmax;}

                        while(i<=imax||j>=jmin){
                            if(int_copyBricksArray2D[i][j]!=0&&int_copyBricksArray2D[i][j]!=int_const_stoneBrickNumber&&i+1<6&&j-1>=0
                                    &&int_copyBricksArray2D[i+1][j-1]==int_copyBricksArray2D[i][j]){
                                if(p!=int_const_stoneBrickNumber+6&&
                                        (int_bricksArray2D[i][j]!=int_const_stoneBrickNumber+6||int_bricksArray2D[i+1][j-1]!=int_const_stoneBrickNumber+6)
                                        &&rc==0){rc=1;}
                                m+=1;}
                            else{
                                if(m>=2&&(rc==1||p==int_const_stoneBrickNumber+6)){for(int b=0;b<=m;b++){
                                    int_matchedBricksArray2D[i-b][j+b][0]=int_copyBricksArray2D[i-b][j+b];
                                    if(int_bricksArray2D[i-b][j+b]==int_const_stoneBrickNumber+6){
                                        int_matchedBricksArray2D[i-b][j+b][1]=int_const_stoneBrickNumber+6;}//сохраняем инфо о том, что это rainbow
                                    else{int_matchedBricksArray2D[i-b][j+b][1]=0;}
                                }
                                    m=0;rc=0;if(GAME_STATE!=5){GAME_STATE=5;}
                                    if(int_comboTimesMultiplier<10){int_comboTimesMultiplier+=1;}
                                }
                                else{m=0;rc=0;}
                            }
                            i++;j--;
                        }
                    }

                    //диагональные снизу вверх (старт справа сверху) справа налево
                    for(int k=0,i=0,j=0,imin=0,imax=2,jmin=2,jmax=0,m=0,rc=0;k<14;k++){
                        if(k<4){imin=3-k;imax=5;jmin=0;jmax=2+k;i=imin;j=jmin;}
                        if(k>=4&&k<11){imin=0;imax=5;jmin=k-3;jmax=k+2;i=imin;j=jmin;}
                        if(k>=11){imin=0;imax=15-k;jmin=k-3;jmax=12;i=imin;j=jmin;}

                        while(i<=imax||j<=jmax){
                            if(int_copyBricksArray2D[i][j]!=0&&int_copyBricksArray2D[i][j]!=int_const_stoneBrickNumber&&i+1<6&&j+1<13
                                    &&int_copyBricksArray2D[i+1][j+1]==int_copyBricksArray2D[i][j]){
                                if(p!=int_const_stoneBrickNumber+6&&
                                        (int_bricksArray2D[i][j]!=int_const_stoneBrickNumber+6||int_bricksArray2D[i+1][j+1]!=int_const_stoneBrickNumber+6)
                                        &&rc==0){rc=1;}
                                m+=1;}
                            else{
                                if(m>=2&&(rc==1||p==int_const_stoneBrickNumber+6)){for(int b=0;b<=m;b++){
                                    int_matchedBricksArray2D[i-b][j-b][0]=int_copyBricksArray2D[i-b][j-b];
                                    if(int_bricksArray2D[i-b][j-b]==int_const_stoneBrickNumber+6){
                                        int_matchedBricksArray2D[i-b][j-b][1]=int_const_stoneBrickNumber+6;}//сохраняем инфо о том, что это rainbow
                                    else{int_matchedBricksArray2D[i-b][j-b][1]=0;}
                                }
                                    m=0;rc=0;if(GAME_STATE!=5){GAME_STATE=5;}
                                    if(int_comboTimesMultiplier<10){int_comboTimesMultiplier+=1;}
                                }
                                else{m=0;rc=0;}
                            }

                            i++;j++;
                        }
                    }
                }

                if(GAME_STATE!=5){GAME_STATE=1;
                    if(int_comboTimesMultiplier!=0){
                        int_comboTimesMultiplier=0;int_scoreAdded=0;scoreDisplayTimer=System.currentTimeMillis()+scoreDisplayTimerIncrement;}
                }//цикл логики заново
                else{processTimer=System.currentTimeMillis()+lagTimerIncrement;}
            }

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    int_fallingBrickGPS[i][j][2]=0;
                    if(bool_ghostEnabled){int_fallingBrickGPSghost[i][j][2]=0;}
                }
            }
        }

        // Уничтожение совпавших
        if(GAME_STATE==5&&processTimer<System.currentTimeMillis()){

            for (int i = array_prc_effects_0.size - 1; i >= 0; i--)
                array_prc_effects_0.get(i).free(); //free all the effects back to the pool
                array_prc_effects_0.clear();
                array_prc_removeEffects_0.clear();
            //particles
            for (int i = array_prc_effects_1.size - 1; i >= 0; i--)
                array_prc_effects_1.get(i).free(); //free all the effects back to the pool
            array_prc_effects_1.clear();
            array_prc_removeEffects_1.clear();

            for(int i=0;i<6;i++){
                for(int j=0;j<13;j++){
                    if(int_matchedBricksArray2D[i][j][0]!=0&&int_bricksArray2D[i][j]!=0){

                        //particles for bricks remove if is not 0,1,2 strokes (upper)
                        if(j>2){
                        ParticleEffectPool.PooledEffect pooledEffect_0 = particleEffectPool_0.obtain();
                        pooledEffect_0.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                        array_prc_effects_0.add(pooledEffect_0);
                            if(int_bricksArray2D[i][j]>=100){array_prc_removeEffects_0.add(getSolidBrickColor(decodeSpecialBrick(int_bricksArray2D[i][j])));}//for color
                            else{array_prc_removeEffects_0.add(getSolidBrickColor(int_bricksArray2D[i][j]));}
                        }

                        int_curScore+=int_comboTimesMultiplier;int_lifeScore+=int_comboTimesMultiplier;
                        int_scoreAdded+=int_comboTimesMultiplier;
                            int_bricksArray2D[i][j]=0;

                        GAME_STATE=6;
                    }
                }
                int_scoreAddedRendering = int_scoreAdded;//for +N on score display
            }

            brickAnnihilationTimer=System.currentTimeMillis()+450;//!!! increment must be lower than lagTimerIncrement !!!
            if(GAME_STATE!=6){GAME_STATE=1;
                if(int_comboTimesMultiplier!=0){
                    int_comboTimesMultiplier=0;int_scoreAdded=0;scoreDisplayTimer=System.currentTimeMillis()+scoreDisplayTimerIncrement;}
            }//цикл логики заново
            else{processTimer=System.currentTimeMillis()+lagTimerIncrement;
                if(bool_soundEnable){sfx_collapse_0.setVolume(sfx_collapse_0.play(),0.8f+float_volumeIncrement);}
            }

        }


        // Физика(2) повторно ,т.к. поле изменилось
        if(GAME_STATE==6&&processTimer<System.currentTimeMillis()){

            if(int_comboTimesMultiplier-2>=0){
                int_comboTimesMultiplierForRender=int_comboTimesMultiplier;
                comboTimesMultiplierRenderTimer=System.currentTimeMillis()+2500;
                if(int_comboTimesMultiplier>=5){comboTimesMultiplierRenderTimerWOW=System.currentTimeMillis()+6000;}
                if(bool_soundEnable){sfx_comboTimes[int_comboTimesMultiplier-2].setVolume(sfx_comboTimes[int_comboTimesMultiplier-2].play(),0.5f+float_volumeIncrement);}
            }
            calculatePhysics();
            GAME_STATE=4;processTimer=System.currentTimeMillis()+lagTimerIncrement;int_noPhysics=1;
        }

        /**-----------------------------------------------------------------RENDERING----------------------------------------------------------------------------**/
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if(!bool_Loading){
        //batch.setShader(shaderProgramDefault);
        //batch.setShader(shaderProgramInvert);
        //shaderProgramInvert.setUniformf("hueAdjust",hueAdjustTest);

            //Darken shader
            if(bool_FreshStart){
                if(float_brightness <1.0f){
                    batch.setShader(shaderProgramDefault);
                    float_brightness +=Gdx.graphics.getDeltaTime()*0.4f;
                    shaderProgramDefault.setUniformf("uniform_vec4_color",Color.WHITE);
                    shaderProgramDefault.setUniformf("uniform_float_brightness", float_brightness);
                }else{bool_FreshStart=false;
                    float_brightness =1.0f;shaderProgramDefault.setUniformf("uniform_float_brightness",1.0f);}
            }

        spr_mainField.setPosition(60,viewport.getWorldHeight()-spr_mainField.getHeight()-120);//net
        spr_mainField.draw(batch);

            batch.setShader(shaderProgramRGB2HSV);
            shaderProgramRGB2HSV.setUniformf("u_resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            shaderProgramRGB2HSV.setUniformf("u_brightness", float_brightness);
            shaderProgramRGB2HSV.setUniformf("u_saturation",float_saturation);
            shaderProgramRGB2HSV.setUniformf("u_time",float_time);
            shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);//if u_hue set to 1.0f then u_hue disabled in shader

        //рендринг MainField bricks
        for(int i=0,f,fs=0;i<6;i++){
            for(int j=3;j<13;j++){
                if(int_bricksArray2D[i][j]!=0){
                    f=int_bricksArray2D[i][j]-1;//компенсация для пустой клетки брика
                    if(f!=int_const_stoneBrickNumber+7-1){//if not paint bomb

                        if(f>=100-1){//if special
                            fs=f;
                            f=decodeSpecialBrick(f+1)-1;//+1 декомпенсация для функции декодера

                            if(f==int_const_stoneBrickNumber+10){//special magic | if u_hue set to 1.0f then u_hue disabled in shader
                                if(int_lifeBrickDamage>0){
                                    shaderProgramRGB2HSV.setUniformf("u_hue",0.99f-float_time*0.01f);
                                    spr_bricks[int_const_stoneBrickNumber+13].setAlpha(0.6f);
                                    spr_bricks[int_const_stoneBrickNumber+13].setPosition(60+11+i*60+spr_bricks[int_const_stoneBrickNumber+13].getOriginX(),
                                            viewport.getWorldHeight()-180-11-(j-3)*60-spr_bricks[int_const_stoneBrickNumber+13].getOriginY());//11=оранж. рамка mainField
                                    spr_bricks[int_const_stoneBrickNumber+13].draw(batch);batch.flush();
                                    shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);
                                }
                                switch (int_lifeBrickDamage){//damage of magic life brick
                                    case 1:f=int_const_stoneBrickNumber+11;break;
                                    case 2:f=int_const_stoneBrickNumber+12;break;
                                    default:break;
                                }
                                shaderProgramRGB2HSV.setUniformf("u_hue",float_time*0.01f);
                            }
                        }

                        spr_bricks[f].setAlpha(1.0f);
                        spr_bricks[f].setPosition(60+11+i*60+spr_bricks[f].getOriginX(),
                                viewport.getWorldHeight()-180-11-(j-3)*60-spr_bricks[f].getOriginY());//11=оранж. рамка mainField
                        spr_bricks[f].draw(batch);batch.flush();

                        shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);

                        if(fs>=100-1){//animation effect for special stones bricks

                            if(GAME_STATE==7&&fs==888-1){shaderProgramRGB2HSV.setUniformf("u_saturation",float_lifeBrickSaturation);}//when applying life
                            getSpecialSpriteAnimationAndDraw(fs+1,f,false);batch.flush();
                            if(GAME_STATE==7){shaderProgramRGB2HSV.setUniformf("u_saturation",float_saturation);}
                            fs=0;
                        }

                    }else{
                        if(f==int_const_stoneBrickNumber+7-1){
                            spr_PaintBomb[int_paintBombColor].setAlpha(1.0f);
                            spr_PaintBomb[int_paintBombColor].setPosition(60+11+i*60+spr_bricks[int_paintBombColor].getOriginX(),
                                    viewport.getWorldHeight()-180-11-(j-3)*60-spr_bricks[int_paintBombColor].getOriginY());//11=оранж. рамка mainField

                            spr_PaintBomb[int_paintBombColor].draw(batch);}
                    }

                   /* switch (f){
                        case 0:
                            batch.setShader(shaderProgramInvert);shaderProgramInvert.setUniformf("hueAdjust",-12.75f);
                            spr_bricks[f].draw(batch);
                            batch.setShader(null);
                            break;
                        default:spr_bricks[f].draw(batch);break;
                    }*/
                }else{
                    if(int_matchedBricksArray2D[i][j][0]!=0&&brickAnnihilationTimer>System.currentTimeMillis()){
                        if(int_matchedBricksArray2D[i][j][1]==0){f=int_matchedBricksArray2D[i][j][0]-1;}// -1 компенсация для пустой клетки атласа
                        else{f=int_matchedBricksArray2D[i][j][1]-1;}//3d for bonus? bricks info
                        float a=((brickAnnihilationTimer-System.currentTimeMillis())*100.0f/450.0f)*0.01f;
                        if(f>=100-1){//if special
                            fs=f;
                            f=decodeSpecialBrick(f+1)-1;//+1 декомпенсация для функции декодера
                            if(f==int_const_stoneBrickNumber+10){shaderProgramRGB2HSV.setUniformf("u_hue",float_time*0.01f);}//special magic | if u_hue set to 1.0f then u_hue disabled in shader
                        }

                        spr_bricks[f].setAlpha(a);
                        spr_bricks[f].setPosition(60+11+i*60+spr_bricks[f].getOriginX(),
                                viewport.getWorldHeight()-180-11-(j-3)*60-spr_bricks[f].getOriginY());//11=оранж. рамка mainField
                        spr_bricks[f].draw(batch);batch.flush();
                        shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);

                        if(fs>=100-1){//animation effect for special stones bricks
                            getSpecialSpriteAnimationAndDraw(fs+1,f,false);
                            fs=0;
                        }

                    }else{if(int_matchedBricksArray2D[i][j][0]!=0){int_matchedBricksArray2D[i][j][0]=0;int_matchedBricksArray2D[i][j][1]=0;}}
                }
            }
        }

            /** -------------- Particles ---------------- **/
            if(GAME_STATE!=0&&GAME_STATE!=-1){
            enableCropShader(0);
            if(bool_bombExploded){//Usual Bomb Explosion particle
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                prc_explosion_0.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                prc_explosion_0.draw(batch,Gdx.graphics.getDeltaTime());
                if(prc_explosion_0.isComplete()){
                    prc_explosion_0.reset();bool_bombExploded=false;}
            }

            if(bool_paintBombExploded){//PaintBomb spray
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                prc_paintBombSpray.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                shaderProgramCrop.setUniformf("uniform_vec4_color", getPaintBombColor(int_paintBombColor));
                prc_paintBombSpray.draw(batch,Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color", Color.WHITE);
                if(prc_paintBombSpray.isComplete()){
                    prc_paintBombSpray.reset();bool_paintBombExploded=false;}
            }

            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+4){//Viktor Bomb Halo Main Field
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                prc_viktorBombHalo.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);//for main field
                prc_viktorBombHalo.draw(batch,Gdx.graphics.getDeltaTime());
            }else{if(!prc_viktorBombHalo.isComplete()){prc_viktorBombHalo.reset();}}

            if(int_nextBricksArray2D[1][1]==int_const_stoneBrickNumber+4){//Viktor Bomb Halo NextBrick Field
                enableCropShader(1);
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                prc_viktorBombHaloNextField.getEmitters().first().setPosition(viewport.getWorldWidth()-120-11-60+spr_bricks[0].getWidth()*0.5f,
                        viewport.getWorldHeight()-180-11-60+spr_bricks[0].getHeight()*0.5f);
                prc_viktorBombHaloNextField.draw(batch,Gdx.graphics.getDeltaTime());
                enableCropShader(0);
            }else{if(!prc_viktorBombHaloNextField.isComplete()){prc_viktorBombHaloNextField.reset();}}
            }

            batch.setShader(shaderProgramDefault);

            //рендринг призрака падающей фигуры
            if(bool_ghostEnabled){
                for(int i=0,i_fall,j_fall,f,fs=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        if((GAME_STATE!=3||(GAME_STATE==3&&(int_brick2X2stage!=0||int_brick3X3stage!=0)))&&int_fallingBrickGPSghost[i][j][2]!=0){
                            f=int_fallingBrickGPSghost[i][j][2]-1;//компенсация для пустой клетки
                            i_fall=int_fallingBrickGPSghost[i][j][0];j_fall=int_fallingBrickGPSghost[i][j][1];
                            int af=0;
                            for(int ik=0;ik<3;ik++){if(i_fall==int_fallingBrickGPS[ik][j][0]-3&&int_fallingBrickGPS[ik][j][2]!=0){af=1;ik=3;}}
                            if(i_fall>=0&&af==0){
                                if(f!=int_const_stoneBrickNumber+7-1){

                                    if(f>=100-1){//if special
                                        fs=f;
                                        f=decodeSpecialBrick(f+1)-1;//+1 декомпенсация для функции декодера
                                    }

                                    spr_bricks[f].setAlpha(0.3f);
                                    spr_bricks[f].setPosition(60+11+j_fall*60+spr_bricks[f].getOriginX(),
                                            viewport.getWorldHeight()-180-11-i_fall*60-spr_bricks[f].getOriginY());//11=оранж. рамка mainField
                                    if(f==int_const_stoneBrickNumber+10){
                                        batch.setShader(shaderProgramRGB2HSV);
                                        shaderProgramRGB2HSV.setUniformf("u_resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                                        shaderProgramRGB2HSV.setUniformf("u_brightness", float_brightness);
                                        shaderProgramRGB2HSV.setUniformf("u_saturation",float_saturation);
                                        shaderProgramRGB2HSV.setUniformf("u_time",float_time);
                                        shaderProgramRGB2HSV.setUniformf("u_hue",float_time*0.01f);
                                        spr_bricks[f].draw(batch);batch.flush();
                                        shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);
                                        batch.setShader(shaderProgramDefault);
                                    }//special magic | if u_hue set to 1.0f then u_hue disabled in shader
                                    else {spr_bricks[f].draw(batch);batch.flush();}


                                    if(fs>=100-1){//animation effect for special stones bricks
                                        getSpecialSpriteAnimationAndDraw(fs+1,f,false);
                                        fs=0;
                                    }
                              }
                                else{
                                    if(f==int_const_stoneBrickNumber+7-1){
                                        spr_PaintBomb[int_paintBombColor].setAlpha(0.3f);
                                        spr_PaintBomb[int_paintBombColor].setPosition(60+11+j_fall*60+spr_PaintBomb[int_paintBombColor].getOriginX(),
                                                viewport.getWorldHeight()-180-11-i_fall*60-spr_PaintBomb[int_paintBombColor].getOriginY());
                                        spr_PaintBomb[int_paintBombColor].draw(batch);
                                    }
                                }
                            }
                            else {
                                if(i_fall==-1&&(GAME_STATE_BONUSTYPE<=int_const_stoneBrickNumber||GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+6
                                        ||GAME_STATE_BONUSTYPE>=100)){ /** important for new bricks **/
                                    spr_warningIcon.setPosition(60+9+j_fall*60+spr_warningIcon.getWidth()*0.5f*spr_warningIcon.getScaleX(),
                                    viewport.getWorldHeight()-180-11+60-spr_warningIcon.getHeight()*spr_warningIcon.getScaleY());//11=оранж. рамка mainField
                                    spr_warningIcon.draw(batch);
                                }
                            }
                        }
                    }
                }
            }

        //рендринг падающей фигуры
            enableCropShader(0);
            if((int_brick2X2stage==0&&int_brick3X3stage==0)||int_brick2X2stage>=3||int_brick3X3stage>=3){//not 2x2 or 3x3

                for(int i=0,i_fall,j_fall,f,fs=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        if(int_fallingBrickGPS[i][j][2]!=0&&int_fallingBrickGPS[i][j][0]>=3){
                            f=int_fallingBrickGPS[i][j][2]-1;//компенсация для пустой клетки
                            i_fall=int_fallingBrickGPS[i][j][0]-3;j_fall=int_fallingBrickGPS[i][j][1];
                            if(f!=int_const_stoneBrickNumber+7-1){

                                if(f>=100-1){//if special
                                    fs=f;
                                    f=decodeSpecialBrick(f+1)-1;//+1 декомпенсация для функции декодера
                                }

                                spr_bricks[f].setAlpha(1.0f);
                                spr_bricks[f].setPosition(60+11+j_fall*60+spr_bricks[f].getOriginX(),
                                        viewport.getWorldHeight()-180-11-i_fall*60-spr_bricks[f].getOriginY());//11=оранж. рамка mainField
                                if(f==int_const_stoneBrickNumber+10){
                                    batch.setShader(shaderProgramRGB2HSV);
                                    shaderProgramRGB2HSV.setUniformf("u_resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                                    shaderProgramRGB2HSV.setUniformf("u_brightness", float_brightness);
                                    shaderProgramRGB2HSV.setUniformf("u_saturation",float_saturation);
                                    shaderProgramRGB2HSV.setUniformf("u_time",float_time);
                                    shaderProgramRGB2HSV.setUniformf("u_hue",float_time*0.01f);
                                    spr_bricks[f].draw(batch);batch.flush();
                                    shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);
                                    enableCropShader(0);
                                }//special magic | if u_hue set to 1.0f then u_hue disabled in shader
                                else {spr_bricks[f].draw(batch);batch.flush();}

                                if(fs>=100-1){//animation effect for special stones bricks
                                    getSpecialSpriteAnimationAndDraw(fs+1,f,false);
                                    fs=0;
                                }

                            }else{
                                if(f==int_const_stoneBrickNumber+7-1){
                                    spr_PaintBomb[int_paintBombColor].setAlpha(1.0f);
                                    spr_PaintBomb[int_paintBombColor].setPosition(60+11+j_fall*60+spr_PaintBomb[int_paintBombColor].getOriginX(),
                                            viewport.getWorldHeight()-180-11-i_fall*60-spr_PaintBomb[int_paintBombColor].getOriginY());//11=оранж. рамка mainField
                                    spr_PaintBomb[int_paintBombColor].draw(batch);
                                }
                            }
                        }
                    }
                }
            }
            if(int_fallingBrickGPS[0][0][2]!=0&&int_fallingBrickGPS[0][2][2]==0&&
                    int_fallingBrickGPS[0][0][2]==int_fallingBrickGPS[0][1][2]&&
                    int_fallingBrickGPS[0][0][2]==int_fallingBrickGPS[1][0][2]&&
                    int_fallingBrickGPS[0][0][2]==int_fallingBrickGPS[1][1][2]){// if 2x2 big brick    crop????????????

                int f = int_fallingBrickGPS[1][0][2]-1;
                if(f==12){f=7;}//rainbow
                else{if(f==int_const_stoneBrickNumber){f=7;}}

                spr_bricks2X2[f].setPosition(60 + 11 + int_fallingBrickGPS[1][0][1] * 60 + spr_bricks2X2[f].getOriginX(),
                        viewport.getWorldHeight() - 11 - int_fallingBrickGPS[1][0][0] * 60 - spr_bricks2X2[f].getOriginY());
                if(int_fallingBrickGPS[1][0][0]>=3&&int_brick2X2stage<3){
                    spr_bricks2X2[f].draw(batch);
                }

                if(int_brick2X2stage==2){
                    spr_LaserCutter2X2[0].setAlpha(1.0f);
                    spr_LaserCutter2X2[0].setPosition(spr_bricks2X2[f].getX()-12,spr_bricks2X2[f].getY()-12);
                    spr_LaserCutter2X2[0].draw(batch);
                    float a = spr_LaserCutter2X2[1].getColor().a+Gdx.graphics.getDeltaTime()+0.01f;
                    if(a>1.0f){a=1.0f;int_brick2X2stage=3;}
                    if(int_brick2X2stage!=3){
                    spr_LaserCutter2X2[1].setAlpha(a);
                    spr_LaserCutter2X2[1].setPosition(spr_bricks2X2[f].getX()-12,spr_bricks2X2[f].getY()-12);
                    spr_LaserCutter2X2[1].draw(batch);}
                }
                if(int_brick2X2stage==3){
                    float a = spr_LaserCutter2X2[0].getColor().a-(Gdx.graphics.getDeltaTime()+0.01f);
                    if(a<0.0f){a=0.0f;int_brick2X2stage=4;}
                    spr_LaserCutter2X2[0].setAlpha(a);
                    spr_LaserCutter2X2[0].setPosition(spr_bricks2X2[f].getX()-12,spr_bricks2X2[f].getY()-12);
                    spr_LaserCutter2X2[0].draw(batch);
                    if(a>0.9f){spr_LaserCutter2X2[1].draw(batch);}
                    if(a>0.8f){spr_LaserCutter2X2[1].draw(batch);}
                    spr_LaserCutter2X2[1].setAlpha(a);
                    spr_LaserCutter2X2[1].setPosition(spr_bricks2X2[f].getX()-12,spr_bricks2X2[f].getY()-12);
                    spr_LaserCutter2X2[1].draw(batch);
                }
            }


            if(int_fallingBrickGPS[0][0][2]!=0&&int_fallingBrickGPS[0][0][2]==int_fallingBrickGPS[0][1][2]&&
                    int_fallingBrickGPS[0][0][2]==int_fallingBrickGPS[0][2][2]){// if 3X3 big brick Checking only upper stroke!!! crop????????????

                int f = int_fallingBrickGPS[2][0][2]-1;
                if(f==12){f=6;}//rainbow
                spr_bricks3X3[f].setPosition(60 + 11 + int_fallingBrickGPS[2][0][1] * 60 + spr_bricks3X3[f].getOriginX(),
                        viewport.getWorldHeight() - 11 - int_fallingBrickGPS[2][0][0] * 60 - spr_bricks3X3[f].getOriginY());
                if(int_fallingBrickGPS[2][0][0]>=3&&int_brick3X3stage<3){
                    spr_bricks3X3[f].draw(batch);
                }

                if(int_brick3X3stage==2){
                    spr_LaserCutter3X3[0].setAlpha(1.0f);
                    spr_LaserCutter3X3[0].setPosition(spr_bricks3X3[f].getX()-12,spr_bricks3X3[f].getY()-12);
                    spr_LaserCutter3X3[0].draw(batch);
                    float a = spr_LaserCutter3X3[1].getColor().a+Gdx.graphics.getDeltaTime()+0.01f;
                    if(a>1.0f){a=1.0f;int_brick3X3stage=3;}
                    if(int_brick3X3stage!=3){
                        spr_LaserCutter3X3[1].setAlpha(a);
                        spr_LaserCutter3X3[1].setPosition(spr_bricks3X3[f].getX()-12,spr_bricks3X3[f].getY()-12);
                        spr_LaserCutter3X3[1].draw(batch);}
                }
                if(int_brick3X3stage==3){
                    float a = spr_LaserCutter3X3[0].getColor().a-(Gdx.graphics.getDeltaTime()+0.01f);
                    if(a<0.0f){a=0.0f;int_brick3X3stage=4;}
                    spr_LaserCutter3X3[0].setAlpha(a);
                    spr_LaserCutter3X3[0].setPosition(spr_bricks3X3[f].getX()-12,spr_bricks3X3[f].getY()-12);
                    spr_LaserCutter3X3[0].draw(batch);
                    if(a>0.9f){spr_LaserCutter3X3[1].draw(batch);}
                    if(a>0.8f){spr_LaserCutter3X3[1].draw(batch);}
                    spr_LaserCutter3X3[1].setAlpha(a);
                    spr_LaserCutter3X3[1].setPosition(spr_bricks3X3[f].getX()-12,spr_bricks3X3[f].getY()-12);
                    spr_LaserCutter3X3[1].draw(batch);
                }
            }


            if(GAME_STATE!=0&&GAME_STATE!=-1){
            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+1){//Bomb fuse fire
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                if(int_fallingBrickGPS[1][1][0]>2){
                    prc_bombFuse.getEmitters().first().setPosition(float_posXY[0]+24,float_posXY[1]+14);
                    prc_bombFuse.draw(batch,Gdx.graphics.getDeltaTime());
                }
            }else{if(!prc_bombFuse.isComplete()){prc_bombFuse.reset();}}

            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+5){//Virus prc emission
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                if(int_fallingBrickGPS[1][1][0]>2){
                    prc_virusEmission.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                    prc_virusEmission.draw(batch,Gdx.graphics.getDeltaTime());
                }
            }else{if(!prc_virusEmission.isComplete()){prc_virusEmission.reset();}}

            if(int_nextBricksArray2D[1][1]==int_const_stoneBrickNumber+5){//Virus prc emission NextBrick Field
                enableCropShader(1);
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                prc_virusEmissionNextField.getEmitters().first().setPosition(viewport.getWorldWidth()-120-11-60+spr_bricks[0].getWidth()*0.5f,
                        viewport.getWorldHeight()-180-11-60+spr_bricks[0].getHeight()*0.5f);
                prc_virusEmissionNextField.draw(batch,Gdx.graphics.getDeltaTime());
                enableCropShader(0);
            }else{if(!prc_virusEmissionNextField.isComplete()){prc_virusEmissionNextField.reset();}}

            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+2){//Star prc emission
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                    prc_starEmission.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                    prc_starEmission.draw(batch,Gdx.graphics.getDeltaTime());
            }else{if(!prc_starEmission.isComplete()){prc_starEmission.reset();}}

            if(int_nextBricksArray2D[1][1]==int_const_stoneBrickNumber+2){//Star prc emission NextBrick Field
                enableCropShader(1);
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                prc_starEmissionNextField.getEmitters().first().setPosition(viewport.getWorldWidth()-120-11-60+spr_bricks[0].getWidth()*0.5f,
                        viewport.getWorldHeight()-180-11-60+spr_bricks[0].getHeight()*0.5f);
                prc_starEmissionNextField.draw(batch,Gdx.graphics.getDeltaTime());
                enableCropShader(0);
            }else{if(!prc_starEmissionNextField.isComplete()){prc_starEmissionNextField.reset();}}

            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+3){//Lightning Stasis prc
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                if(int_fallingBrickGPS[1][1][0]>2){//if not in hidden filed
                    prc_lightningBrickStasis.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                    prc_lightningBrickStasis.draw(batch,Gdx.graphics.getDeltaTime());
                }
            }else{if(!prc_lightningBrickStasis.isComplete()){prc_lightningBrickStasis.reset();}}
            }

            if(GAME_STATE_BONUSTYPE==int_const_stoneBrickNumber+7){//Paint Bomb drop prc
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                prc_paintBombDrop.getEmitters().first().setPosition(float_posXY[0]-spr_bricks[0].getWidth()*0.5f,float_posXY[1]-spr_bricks[0].getHeight()*0.5f);
                shaderProgramCrop.setUniformf("uniform_vec4_color",getPaintBombColor(int_paintBombColor));
                prc_paintBombDrop.draw(batch,Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
            }else{if(!prc_paintBombDrop.isComplete()){prc_paintBombDrop.reset();}}

            if(int_nextBricksArray2D[1][1]==int_const_stoneBrickNumber+7){//Paint Bomb drop prc NextBrick Field
                enableCropShader(1);
                shaderProgramCrop.setUniformf("uniform_vec4_color",getPaintBombColor(int_paintBombNextColor));
                shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                prc_paintBombDropNextFiled.getEmitters().first().setPosition(viewport.getWorldWidth()-120-11-60,
                        viewport.getWorldHeight()-180-11-60);
                prc_paintBombDropNextFiled.draw(batch,Gdx.graphics.getDeltaTime());
                enableCropShader(0);
            }else{if(!prc_paintBombDropNextFiled.isComplete()){prc_paintBombDropNextFiled.reset();}}

            if(bool_starExploded){//Star brick remove effect
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],int_fallingBrickGPS[1][1][0]);
                prc_remover_1.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                shaderProgramCrop.setUniformf("uniform_vec4_color",rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);
                prc_remover_1.draw(batch,Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                if(prc_remover_1.isComplete()){
                    prc_remover_1.reset();
                    bool_starExploded=false;}
            }
            if(bool_lightningExploded){//first lightning effect
                enableCropShader(0);
                float[] float_posXY = getFallingBrickPos(int_fallingBrickGPS[1][1][1],12);
                prc_remover_1.getEmitters().first().setPosition(float_posXY[0],float_posXY[1]);
                prc_remover_1.draw(batch,Gdx.graphics.getDeltaTime());
                if(prc_remover_1.isComplete()){
                    prc_remover_1.reset();
                    bool_lightningExploded=false;}
            }

            //bricks (remove effects particles)
            for (int i = array_prc_effects_0.size - 1; i >= 0; i--) {
                pooledEffect_0 = array_prc_effects_0.get(i);
                if(array_prc_removeEffects_0.get(i) == Color.BLACK){
                    shaderProgramCrop.setUniformf("uniform_vec4_color",rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);
                }else{
                    shaderProgramCrop.setUniformf("uniform_vec4_color", array_prc_removeEffects_0.get(i));
                }
                pooledEffect_0.draw(batch, Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                if (pooledEffect_0.isComplete()) {
                    pooledEffect_0.free();
                    array_prc_effects_0.removeIndex(i);
                    array_prc_removeEffects_0.removeIndex(i);
                }
            }
            //life bricks regen particles
            for (int i = array_prc_effects_4.size - 1; i >= 0; i--) {
                pooledEffect_4 = array_prc_effects_4.get(i);
                pooledEffect_4.draw(batch, Gdx.graphics.getDeltaTime());
                if (pooledEffect_4.isComplete()) {
                    pooledEffect_4.free();
                    array_prc_effects_4.removeIndex(i);
                }
            }
            //life bricks destroy particles
            for (int i = array_prc_effects_5.size - 1; i >= 0; i--) {
                pooledEffect_5 = array_prc_effects_5.get(i);
                pooledEffect_5.draw(batch, Gdx.graphics.getDeltaTime());
                if (pooledEffect_5.isComplete()) {
                    pooledEffect_5.free();
                    array_prc_effects_5.removeIndex(i);
                }
            }

            //remove-explosion effects for lightning
            for (int i = array_prc_effects_1.size - 1; i >= 0; i--) {
                pooledEffect_1 = array_prc_effects_1.get(i);
                if(array_prc_removeEffects_1.get(i) == Color.BLACK){
                    shaderProgramCrop.setUniformf("uniform_vec4_color",rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);
                }else{
                    shaderProgramCrop.setUniformf("uniform_vec4_color", array_prc_removeEffects_1.get(i));
                }
                pooledEffect_1.draw(batch, Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                if (pooledEffect_1.isComplete()) {
                    pooledEffect_1.free();
                    array_prc_effects_1.removeIndex(i);
                    array_prc_removeEffects_1.removeIndex(i);
                }
            }
            //remove-explosion effects for viktorbomb
            for (int i = array_prc_effects_2.size - 1; i >= 0; i--) {
                pooledEffect_2 = array_prc_effects_2.get(i);
                if(array_prc_removeEffects_2.get(i) == Color.BLACK){
                    shaderProgramCrop.setUniformf("uniform_vec4_color",rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);
                }else{
                    shaderProgramCrop.setUniformf("uniform_vec4_color", array_prc_removeEffects_2.get(i));
                }
                pooledEffect_2.draw(batch, Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                if (pooledEffect_2.isComplete()) {
                    pooledEffect_2.free();
                    array_prc_effects_2.removeIndex(i);
                    array_prc_removeEffects_2.removeIndex(i);
                }
            }
            //remove-switch effects for virus
            for (int i = array_prc_effects_3.size - 1; i >= 0; i--) {
                pooledEffect_3 = array_prc_effects_3.get(i);
                if(array_prc_removeEffects_3.get(i) == Color.BLACK){
                    shaderProgramCrop.setUniformf("uniform_vec4_color",rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);
                }else{
                    shaderProgramCrop.setUniformf("uniform_vec4_color", array_prc_removeEffects_3.get(i));
                }
                pooledEffect_3.draw(batch, Gdx.graphics.getDeltaTime());
                shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                if (pooledEffect_3.isComplete()) {
                    pooledEffect_3.free();
                    array_prc_effects_3.removeIndex(i);
                    array_prc_removeEffects_3.removeIndex(i);
                }
            }

            batch.setShader(shaderProgramDefault);

            //texture mask
           // spr_ParticleMask.draw(batch);

            //hidden bricks field
            for(int i=0,i_fall,j_fall,f;i<3;i++){
                for(int j=0;j<3;j++){
                    if(int_fallingBrickGPS[i][j][2]!=0&&int_fallingBrickGPS[i][j][0]<3){

                    f=int_fallingBrickGPS[i][j][2]-1;//компенсация для пустой клетки
                    switch (f){ /** Add new bricks here **/
                        case 0:spr_hiddenBrick.setColor(Color.RED);break;
                        case 1:spr_hiddenBrick.setColor(Color.GREEN);break;
                        case 2:spr_hiddenBrick.setColor(Color.BLUE);break;
                        case 3:spr_hiddenBrick.setColor(Color.PURPLE);break;
                        case 4:spr_hiddenBrick.setColor(Color.YELLOW);break;
                        case 5:spr_hiddenBrick.setColor(Color.CYAN);break;
                        case int_const_stoneBrickNumber-1:spr_hiddenBrick.setColor(Color.WHITE);break;
                        case int_const_stoneBrickNumber+7:spr_hiddenBrick.setColor(rand.nextFloat(),0.0f,0.0f,1.0f);break;
                        default:spr_hiddenBrick.setColor(rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);break;
                    }
                    i_fall=int_fallingBrickGPS[i][j][0]-3;j_fall=int_fallingBrickGPS[i][j][1];
                    spr_hiddenBrick.setPosition(60+11+j_fall*61+spr_hiddenBrick.getOriginX(),
                            viewport.getWorldHeight()-121-i_fall*10-spr_hiddenBrick.getOriginY());
                    spr_hiddenBrick.draw(batch);
                        batch.setShader(shaderProgramDefault);
                    }
                }
            }

            //сетки
            spr_hiddenBricksField.setPosition(71,viewport.getWorldHeight()-110);//net
            spr_hiddenBricksField.draw(batch);

            spr_nextBrickField.setPosition(viewport.getWorldWidth()-(spr_nextBrickField.getWidth()+60),viewport.getWorldHeight()-spr_nextBrickField.getHeight()-120);//net
            spr_nextBrickField.draw(batch);

            //рендринг бегущей строки
            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3) {
                if(bool_soundEnable){
                    alphabetCore.draw(batch, 620, 1175, int_musicNumber, float_varForHiddenTimer,false,int_language);//if float_varForHiddenTimer<40 alpha reset
                }else{
                    alphabetCore.draw(batch, 620, 1175, -1, float_varForHiddenTimer,false,int_language);
                }
            }else { alphabetCore.draw(batch, 620, 1175, int_profileNumber-1, 100.0f ,true ,int_language);}


        //рендринг HiddenBricksField bricks - не обязателен
        for(int i=0,f;i<6;i++){
            for(int j=0;j<3;j++){
                if(int_bricksArray2D[i][j]!=0){
                    f=int_bricksArray2D[i][j]-1;//компенсация для пустой клетки
                    switch (f){
                        case 0:spr_hiddenBrick.setColor(Color.RED);break;
                        case 1:spr_hiddenBrick.setColor(Color.GREEN);break;
                        case 2:spr_hiddenBrick.setColor(Color.BLUE);break;
                        case 3:spr_hiddenBrick.setColor(Color.PURPLE);break;
                        case 4:spr_hiddenBrick.setColor(Color.YELLOW);break;
                        case 5:spr_hiddenBrick.setColor(Color.CYAN);break;
                        case int_const_stoneBrickNumber-1:spr_hiddenBrick.setColor(Color.WHITE);break;
                        case int_const_stoneBrickNumber+7:spr_hiddenBrick.setColor(rand.nextFloat(),0.0f,0.0f,1.0f);break;
                        default:spr_hiddenBrick.setColor(rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1.0f);break;
                    }
                    spr_hiddenBrick.setPosition(60+11+i*61+spr_hiddenBrick.getOriginX(),
                            viewport.getWorldHeight()-121-(j-3)*10-spr_hiddenBrick.getOriginY());
                    spr_hiddenBrick.draw(batch);
                }
            }
        }


        //рендринг NextBrickField bricks
        if(int_nextBricksArray2D[0][0]!=0&&int_nextBricksArray2D[0][2]==0&&
                int_nextBricksArray2D[0][0]==int_nextBricksArray2D[0][1]&&
                int_nextBricksArray2D[0][0]==int_nextBricksArray2D[1][0]&&
                int_nextBricksArray2D[0][0]==int_nextBricksArray2D[1][1]){// if 2x2 big brick
            int f = int_nextBricksArray2D[1][0]-1;
            if(f==12){f=7;}//rainbow
            spr_bricks2X2[f].setAlpha(1.0f);
            spr_bricks2X2[f].setPosition(viewport.getWorldWidth()-120-11-120-spr_bricks2X2[f].getOriginX(),
                    viewport.getWorldHeight()-180-11-60-spr_bricks2X2[f].getOriginY());
            spr_bricks2X2[f].draw(batch);
        }else {
            if(int_nextBricksArray2D[0][0]!=0&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[0][1]&&
                    int_nextBricksArray2D[0][0]==int_nextBricksArray2D[0][2]) {// if 3X3 big brick Checking only upper stroke!!! )
                int f = int_nextBricksArray2D[1][0]-1;
                if(f==12){f=6;}//rainbow
                spr_bricks3X3[f].setAlpha(1.0f);
                spr_bricks3X3[f].setPosition(viewport.getWorldWidth()-120-11-120-spr_bricks3X3[f].getOriginX(),
                        viewport.getWorldHeight()-180-11-120-spr_bricks3X3[f].getOriginY());
                spr_bricks3X3[f].draw(batch);
            }else{
                for(int i=0,f,fs=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        if(int_nextBricksArray2D[i][j]!=0){
                            f=int_nextBricksArray2D[i][j]-1;//компенсация для пустой клетки
                            if(f!=int_const_stoneBrickNumber+7-1){
                                if(f>=100-1){//if special
                                    fs=f;
                                    f=decodeSpecialBrick(f+1)-1;//+1 декомпенсация для функции декодера
                                }

                                spr_bricks[f].setAlpha(1.0f);
                                spr_bricks[f].setPosition(viewport.getWorldWidth()-120-11-(2-j)*60-spr_bricks[f].getOriginX(),
                                        viewport.getWorldHeight()-180-11-i*60-spr_bricks[f].getOriginY());
                                if(f==int_const_stoneBrickNumber+10){
                                    batch.setShader(shaderProgramRGB2HSV);
                                    shaderProgramRGB2HSV.setUniformf("u_resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                                    shaderProgramRGB2HSV.setUniformf("u_brightness", float_brightness);
                                    shaderProgramRGB2HSV.setUniformf("u_saturation",float_saturation);
                                    shaderProgramRGB2HSV.setUniformf("u_time",float_time);
                                    shaderProgramRGB2HSV.setUniformf("u_hue",float_time*0.01f);
                                    spr_bricks[f].draw(batch);batch.flush();
                                    shaderProgramRGB2HSV.setUniformf("u_hue",1.0f);
                                    batch.setShader(shaderProgramDefault);
                                }//special magic | if u_hue set to 1.0f then u_hue disabled in shader
                                else {spr_bricks[f].draw(batch);batch.flush();}

                                if(fs>=100-1){//animation effect for special stones bricks
                                    getSpecialSpriteAnimationAndDraw(fs+1,f,false);
                                    fs=0;
                                }
                            }else{
                                if(f==int_const_stoneBrickNumber+7-1){
                                    spr_PaintBomb[int_paintBombNextColor].setAlpha(1.0f);
                                    spr_PaintBomb[int_paintBombNextColor].setPosition(viewport.getWorldWidth()-120-11-(2-j)*60-spr_PaintBomb[int_paintBombNextColor].getOriginX(),
                                            viewport.getWorldHeight()-180-11-i*60-spr_PaintBomb[int_paintBombNextColor].getOriginY());
                                    spr_PaintBomb[int_paintBombNextColor].draw(batch);
                                }
                            }
                        }
                    }
                }
            }
        }

            if(GAME_STATE!=0&&GAME_STATE!=-1){//particles in NextBricksField for render below brick

                if(int_nextBricksArray2D[1][1]==int_const_stoneBrickNumber+1){//Bomb fuse fire prc NextBrick Field
                    enableCropShader(1);
                    shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                    shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                    prc_bombFuseNextField.getEmitters().first().setPosition(viewport.getWorldWidth()-120-11-60+spr_bricks[0].getWidth()*0.5f+24,
                            viewport.getWorldHeight()-180-11-60+spr_bricks[0].getHeight()*0.5f+14);
                    prc_bombFuseNextField.draw(batch,Gdx.graphics.getDeltaTime());
                    enableCropShader(0);
                }else{if(!prc_bombFuseNextField.isComplete()){prc_bombFuseNextField.reset();}}

                if(int_nextBricksArray2D[1][1]==int_const_stoneBrickNumber+3){//Lightning stasis prc NextBrick Field
                    enableCropShader(1);
                    shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
                    shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
                    prc_lightningBrickStasisNextField.getEmitters().first().setPosition(viewport.getWorldWidth()-120-11-60+spr_bricks[0].getWidth()*0.5f,
                            viewport.getWorldHeight()-180-11-60+spr_bricks[0].getHeight()*0.5f);
                    prc_lightningBrickStasisNextField.draw(batch,Gdx.graphics.getDeltaTime());
                    enableCropShader(0);
                }else{if(!prc_lightningBrickStasisNextField.isComplete()){prc_lightningBrickStasisNextField.reset();}}

            }batch.setShader(shaderProgramDefault);


            spr_MainBackground.draw(batch);// main background


            //lightning effect
            if(int_lightningRow!=-1&&float_LightingBoltAlpha>0.0f){
                spr_LightningBolt.setRegion(animation_lightningBolt.getKeyFrame(float_LightningBoltAnimTime));
                spr_LightningBolt.setPosition(spr_mainField.getX()+10+int_lightningRow*60+spr_LightningBolt.getOriginX(),spr_mainField.getY()+11);
                spr_LightingBoltLight.setPosition(spr_LightningBolt.getX()-32,spr_LightningBolt.getY()-65);
                spr_LightingBoltLight.draw(batch);
                spr_LightningBolt.draw(batch);
                float_LightningBoltAnimTime+=Gdx.graphics.getDeltaTime();
                if(float_LightingBoltAlpha>=0.0f){
                    spr_LightningBolt.setAlpha(float_LightingBoltAlpha);
                    spr_LightingBoltLight.setAlpha(float_LightingBoltAlpha*0.6f);
                    float_LightingBoltAlpha-=Gdx.graphics.getDeltaTime()*4.0f;
                }
            }else{if(int_lightningRow!=-1){int_lightningRow=-1;float_LightningBoltAnimTime=0.0f;float_LightingBoltAlpha=1.0f;}}





            //рендринг кнопок

            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3&&GAME_STATE!=7){
                spr_buttons[1].setPosition(71+10,360-20);//left
                if(comboTimesMultiplierRenderTimer<System.currentTimeMillis()&&
                        comboTimesMultiplierRenderTimerWOW<System.currentTimeMillis()){
                    spr_buttons[1].draw(batch);
                    if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[1]){
                        spr_buttonsGlow[1].setPosition(spr_buttons[1].getX(),spr_buttons[1].getY());
                        spr_buttonsGlow[1].draw(batch);
                    }
                }else{
                    if(comboTimesMultiplierRenderTimer>System.currentTimeMillis()){
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].setPosition(71+10,360-20);//left number
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-0.35f);
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].draw(batch);
                    }else{
                        spr_buttons[15].setPosition(71+10,360-20);//wow left
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-0.35f);
                        spr_buttons[15].draw(batch);
                    }
                    batch.setShader(shaderProgramDefault);
                }
            }
            else{
                spr_buttons[5].setPosition(71+10,360-20);
                spr_buttons[5].draw(batch);}

            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3&&GAME_STATE!=7){
                spr_buttons[0].setPosition(71+106,180-20);//down
                if(comboTimesMultiplierRenderTimer<System.currentTimeMillis()&&comboTimesMultiplierRenderTimerWOW<System.currentTimeMillis()){
                    spr_buttons[0].draw(batch);
                    if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[0]){
                        spr_buttonsGlow[0].setPosition(spr_buttons[0].getX(),spr_buttons[0].getY());
                        spr_buttonsGlow[0].draw(batch);
                    }
                }else{
                    if(comboTimesMultiplierRenderTimer>System.currentTimeMillis()){
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].setPosition(71+106,180-20);//down number
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-0.35f);
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].draw(batch);
                    }else{
                        spr_buttons[15].setPosition(71+106,180-20);//wow down
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-0.35f);
                        spr_buttons[15].draw(batch);
                    }
                    batch.setShader(shaderProgramDefault);
                }
            }
            else{
                spr_buttons[5].setPosition(71+106,180-20);
                spr_buttons[5].draw(batch);}

            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3&&GAME_STATE!=7){
                spr_buttons[2].setPosition(71+200,360-20);//right
                if(comboTimesMultiplierRenderTimer<System.currentTimeMillis()&&comboTimesMultiplierRenderTimerWOW<System.currentTimeMillis()){
                    spr_buttons[2].draw(batch);
                    if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[2]){
                        spr_buttonsGlow[2].setPosition(spr_buttons[2].getX(),spr_buttons[2].getY());
                        spr_buttonsGlow[2].draw(batch);
                    }
                }else{
                    if(comboTimesMultiplierRenderTimer>System.currentTimeMillis()){
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].setPosition(71+200,360-20);//right number
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-0.35f);
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].draw(batch);
                    }else{
                        spr_buttons[15].setPosition(71+200,360-20);//wow right
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-0.35f);
                        spr_buttons[15].draw(batch);
                    }
                    batch.setShader(shaderProgramDefault);
                }
            }
            else{
                spr_buttons[5].setPosition(71+200,360-20);
                spr_buttons[5].draw(batch);}

            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3&&GAME_STATE!=7){
                spr_buttons[3].setPosition(500,360-20);//shuffle
                if(comboTimesMultiplierRenderTimer<System.currentTimeMillis()&&comboTimesMultiplierRenderTimerWOW<System.currentTimeMillis()){
                    spr_buttons[3].draw(batch);
                    if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[3]){
                        spr_buttonsGlow[3].setPosition(spr_buttons[3].getX(),spr_buttons[3].getY());
                        spr_buttonsGlow[3].draw(batch);
                    }
                }else{
                    if(comboTimesMultiplierRenderTimer>System.currentTimeMillis()){
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].setPosition(500,360-20);//shuffle number
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-2.45f);//4.3f
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].draw(batch);
                    }else{
                        spr_buttons[15].setPosition(500,360-20);//wow shuffle
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-2.45f);//4.3f
                        spr_buttons[15].draw(batch);
                    }
                    batch.setShader(shaderProgramDefault);
                }
            }
            else{
                spr_buttons[5].setPosition(500,360-20);
                spr_buttons[5].draw(batch);}

            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3){//pause
                spr_buttons[4].setPosition(500,710-20);
                if(comboTimesMultiplierRenderTimer<System.currentTimeMillis()&&
                        comboTimesMultiplierRenderTimerWOW<System.currentTimeMillis()&&
                        soundMuteTimer<System.currentTimeMillis()){
                    spr_buttons[4].draw(batch);
                    if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[4]){
                        spr_buttonsGlow[4].setPosition(spr_buttons[4].getX(),spr_buttons[4].getY());
                        spr_buttonsGlow[4].draw(batch);
                    }
                }else{
                    if(comboTimesMultiplierRenderTimer>System.currentTimeMillis()){
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].setPosition(500,710-20);//pause number
                        batch.setShader(shaderProgramInvert);
                        shaderProgramInvert.setUniformf("hueAdjust",-5.35f);
                        spr_buttons[6+(int_comboTimesMultiplierForRender-2)].draw(batch);
                    }else{
                        if(comboTimesMultiplierRenderTimerWOW>System.currentTimeMillis()){
                            spr_buttons[15].setPosition(500,710-20);//wow pause
                            batch.setShader(shaderProgramInvert);
                            shaderProgramInvert.setUniformf("hueAdjust",-5.35f);
                            spr_buttons[15].draw(batch);
                        }else{
                            spr_buttons[16].setPosition(500,710-20);//wow pause
                            batch.setShader(shaderProgramInvert);
                            shaderProgramInvert.setUniformf("hueAdjust",-5.35f);
                            spr_buttons[16].draw(batch);
                        }
                    }
                    batch.setShader(shaderProgramDefault);
                }
            }
            else{
                if(GAME_STATE!=-2){//стадия показа шейдера проигрыша
                    if(MENU_STATE!=100){//if help isn't opened render help btn
                        spr_buttons[17].setPosition(500,710-20);
                        spr_buttons[17].draw(batch);
                        if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[5]){
                            spr_buttonsGlow[5].setPosition(spr_buttons[17].getX(),spr_buttons[17].getY());
                            spr_buttonsGlow[5].draw(batch);
                        }
                    }else{//back btn if help active
                        spr_buttons[18].setPosition(500,710-20);
                        spr_buttons[18].draw(batch);
                        if(rand.nextInt(int_buttonsGlowChance)==0|| bool_buttonPressedGlobal[6]){
                            spr_buttonsGlow[6].setPosition(spr_buttons[18].getX(),spr_buttons[18].getY());
                            spr_buttonsGlow[6].draw(batch);
                        }
                    }
                }else{
                    spr_buttons[5].setPosition(500,710-20);
                    spr_buttons[5].draw(batch);
                }
            }

        //дисплей hidden таймера
        if(hiddenBricksTimer>System.currentTimeMillis()&&GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2&&GAME_STATE!=-3&&GAME_STATE!=7){
            if(!bool_hiddenTimerSFX){
                if(bool_soundEnable){sfx_timerAfter_0.setVolume(sfx_timerAfter_0.play(),0.5f+float_volumeIncrement);}
                bool_hiddenTimerSFX=true;
                //sfx_timer_0.setVolume(sfx_timer_0.play(),0.4f+float_volumeIncrement); !!!
                //id_sfx=sfx_timer_0.play();
                //sfx_timer_0.setLooping(id_sfx,true);
                //sfx_timer_0.play(0.4f+float_volumeIncrement);
                //sfx_timer_0.setVolume(id_sfx,0.4f+float_volumeIncrement*0.5f);
            }
            float_varForHiddenTimer=(hiddenBricksTimer-System.currentTimeMillis())*100.0f/hiddenBricksTimerIncrement;
            //if(a%30==0){sfx_timer_0.setPitch(0.6f,(a+10.0f)*0.01f,0.0f);}
            //sfx_timer_0.setPitch(id_sfx,(a)*0.01f);
            //spr_timerLight.setAlpha(a*0.01f);
            spr_timerLight.setScale(float_varForHiddenTimer*0.01f,1.0f);
            spr_timerLight.setPosition(viewport.getWorldWidth()-250,viewport.getWorldHeight()-110);
            spr_timerLight.draw(batch);
        }else{
            if(bool_hiddenTimerSFX){
                spr_timerLight.setScale(0.0f,1.0f);
                //spr_timerLight.setAlpha(0.0f);//sfx_timer_0.stop();
                bool_hiddenTimerSFX=false;}
        }

        //Очки 6 знаков
        spr_ScoreField.setPosition(spr_mainField.getX(),spr_mainField.getY()+665);
        spr_ScoreField.draw(batch);
        String str_curScore=Integer.valueOf(int_curScore).toString(),str_highScore = Integer.valueOf(int_highScore).toString();
        int scoreNumber;

        if(scoreDisplayTimer>System.currentTimeMillis()){//i==7 for plus char; 8<=i<=9 for multiplier
            String str_multiScore=Integer.valueOf(int_scoreAddedRendering).toString();
            int a=2-str_multiScore.length();
            for(int i=6,ic=0;i<10;i++){
                if(i==6){
                    spr_numbers[10].setColor(Color.GOLD);
                    spr_numbers[10].setPosition(spr_ScoreField.getX()+100+i*22,spr_ScoreField.getY()+20);
                    spr_numbers[10].draw(batch);}
                else{
                    if(i-8>=a){scoreNumber = Character.getNumericValue(str_multiScore.charAt(ic));ic++;}
                    else{scoreNumber=0;}
                    spr_numbers[scoreNumber].setColor(Color.CHARTREUSE);
                    spr_numbers[scoreNumber].setPosition(spr_ScoreField.getX()+100+i*22,spr_ScoreField.getY()+20);
                    spr_numbers[scoreNumber].draw(batch);
                }
            }
        }

        int a=6-str_curScore.length();
        int b=6-str_highScore.length();
        for(int i=0,ic=0,ih=0;i<6;i++){//i<6 for score;
            if(i>=a){scoreNumber = Character.getNumericValue(str_curScore.charAt(ic));ic++;}
            else{scoreNumber=0;}
            spr_numbers[scoreNumber].setColor(Color.WHITE);
            spr_numbers[scoreNumber].setPosition(spr_ScoreField.getX()+100+i*22,spr_ScoreField.getY()+20);
            spr_numbers[scoreNumber].draw(batch);

            if(i>=b){scoreNumber = Character.getNumericValue(str_highScore.charAt(ih));ih++;}
            else{scoreNumber=0;}
            spr_numbers[scoreNumber].setColor(Color.WHITE);
            spr_numbers[scoreNumber].setPosition(spr_ScoreField.getX()+450+i*22,spr_ScoreField.getY()+20);
            spr_numbers[scoreNumber].draw(batch);
        }

        //side borders
        if(spr_sideBorderLight[0].getColor().a!=0.0f){
            if(spr_sideBorderLight[0].getColor().a>0.5f){spr_sideBorderLight[0].setAlpha(spr_sideBorderLight[0].getColor().a-Gdx.graphics.getDeltaTime()*2.0f);}
            else{spr_sideBorderLight[0].setAlpha(spr_sideBorderLight[0].getColor().a-Gdx.graphics.getDeltaTime()*0.9f);}
            if(spr_sideBorderLight[0].getColor().a<0.05f){spr_sideBorderLight[0].setAlpha(0.0f);}
            spr_sideBorderLight[0].setPosition(spr_mainField.getX()-7,spr_mainField.getY()-5);
            spr_sideBorderLight[0].draw(batch);
        }
        if(spr_sideBorderLight[1].getColor().a!=0.0f){
                if(spr_sideBorderLight[1].getColor().a>0.5f){spr_sideBorderLight[1].setAlpha(spr_sideBorderLight[1].getColor().a-Gdx.graphics.getDeltaTime()*2.0f);}
                else{spr_sideBorderLight[1].setAlpha(spr_sideBorderLight[1].getColor().a-Gdx.graphics.getDeltaTime()*0.9f);}
                if(spr_sideBorderLight[1].getColor().a<0.05f){spr_sideBorderLight[1].setAlpha(0.0f);}
                spr_sideBorderLight[1].setPosition(spr_mainField.getX()+spr_mainField.getWidth()-spr_sideBorderLight[1].getWidth()+7,spr_mainField.getY()-5);
                spr_sideBorderLight[1].draw(batch);
        }

        //after explosion
        if(afterExplosionTimer>System.currentTimeMillis()){
            float a2=(afterExplosionTimer-System.currentTimeMillis())*100.0f/2000.0f;
            spr_afterExplosion.setAlpha(a2*0.01f);
            spr_afterExplosion.setPosition(spr_mainField.getX()+11,spr_mainField.getY()+11);
            spr_afterExplosion.draw(batch);
            if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-2){
                spr_afterExplosionLight.setAlpha(spr_afterExplosion.getColor().a);
                spr_afterExplosionLight.setPosition(spr_afterExplosion.getX()-70,spr_afterExplosion.getY()-70);
                spr_afterExplosionLight.draw(batch);
            }
        }

            //game over shader
            if((GAME_STATE==-2&&float_gameOverShaderAlpha<1.0f)||GAME_STATE==-1){
                batch.setShader(shaderProgramNoise_2);
                shaderProgramNoise_2.setUniformf("resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                shaderProgramNoise_2.setUniformf("time",float_time);
                shaderProgramNoise_2.setUniformf("alpha",float_gameOverShaderAlpha);
                if(spr_DisplayForShader_1.getColor()!=Color.WHITE||spr_DisplayForShader_1.getColor().a!=1.0f){//because after applying life
                    spr_DisplayForShader_1.setColor(Color.WHITE);
                    spr_DisplayForShader_1.setAlpha(1.0f);
                }
                spr_DisplayForShader_1.setPosition(spr_mainField.getX()+10,spr_mainField.getY()+11);
                spr_DisplayForShader_2.setPosition(spr_nextBrickField.getX()+13,spr_nextBrickField.getY()+13);
                spr_DisplayForShader_3.setPosition(spr_hiddenBricksField.getX(),spr_hiddenBricksField.getY()+2);
                spr_DisplayForShader_1.draw(batch);
                spr_DisplayForShader_2.draw(batch);
                spr_DisplayForShader_3.draw(batch);
                batch.setShader(shaderProgramDefault);
                if(float_gameOverShaderAlpha<1.0f){float_gameOverShaderAlpha+=Gdx.graphics.getDeltaTime()*0.5f;}
            }else {
                if(GAME_STATE==-2){
                    GAME_STATE=-1;
                    if(bool_soundEnable){sfx_gameOver_0.setVolume(sfx_gameOver_0.play(),1.5f+float_volumeIncrement);}
                }
            }

            //life applying
            if(GAME_STATE==7&&float_lifeBrickSaturation>=0.0f){

                if(spr_DisplayForShader_1.getColor().a<0.8f){spr_DisplayForShader_1.setAlpha(spr_DisplayForShader_1.getColor().a+0.01f);}
                spr_DisplayForShader_1.setPosition(spr_mainField.getX()+10,spr_mainField.getY()+11);
                spr_DisplayForShader_1.draw(batch);
                if(spr_DisplayForShader_1.getColor().a>=0.8f&&APPLYING_LIFE_STATE<4){
                    if(spr_Light0_0.getColor().a==1.0f){
                        if(bool_soundEnable){sfx_switch_0.setVolume(sfx_switch_0.play(),1.0f+float_volumeIncrement);}
                    }
                    if(spr_Light0_0.getColor().a>0.5f){
                        spr_Light0_0.setAlpha(spr_Light0_0.getColor().a-0.01f);
                    }
                    if(spr_Light0_0.getColor().a<=0.5f){//Big heart
                        if(APPLYING_LIFE_STATE==0){APPLYING_LIFE_STATE=1;
                            if(bool_soundEnable){sfx_holyChoir_0.setVolume(sfx_holyChoir_0.play(),1.0f+float_volumeIncrement);}
                        }
                        enableCropShader(0);
                        if(spr_BigHeart[0].getY()>spr_mainField.getY()+spr_mainField.getHeight()*0.5f-spr_BigHeart[0].getHeight()*0.5f){
                            spr_BigHeart[0].setPosition(spr_BigHeart[0].getX(), spr_BigHeart[0].getY()-Gdx.graphics.getDeltaTime()*400.0f);
                            spr_BigHeart[0].draw(batch);
                        }else{//when heat stopped
                            if(APPLYING_LIFE_STATE==1){lifeApplyingTimer=System.currentTimeMillis()+1000;APPLYING_LIFE_STATE=2;}//wait sometime
                            if(APPLYING_LIFE_STATE==2){spr_BigHeart[0].draw(batch);}
                            if(APPLYING_LIFE_STATE==2&&System.currentTimeMillis()>lifeApplyingTimer){
                                if(bool_soundEnable){sfx_smash_0.setVolume(sfx_smash_0.play(),1.0f+float_volumeIncrement);}
                                spr_BigHeart[1].setPosition(spr_BigHeart[0].getX(),spr_BigHeart[0].getY());
                                spr_BigHeart[2].setPosition(spr_BigHeart[0].getX(),spr_BigHeart[0].getY());
                                saveProgress();
                                for (int i=0;i<6;i++){
                                    for (int j=0;j<13;j++){
                                        if(j<3){int_bricksArray2D[i][j]=0;}
                                        if(int_bricksArray2D[i][j]==888){int_bricksArray2D[i][j]=0;}
                                        if(int_bricksArray2D[i][j]!=0){int_numOfAllBricksForLifeBonus++;}
                                    }}int_numOfAllBricksForLifeBonus=(int)(int_numOfAllBricksForLifeBonus*float_lifeApplyingPower);
                                APPLYING_LIFE_STATE=3;
                            }
                            if(APPLYING_LIFE_STATE==3){//repeated!

                               // if(spr_BigHeart[1].getX()>spr_mainField.getX()-spr_BigHeart[1].getWidth()*0.5f){
                                if(spr_BigHeart[1].getX()>spr_mainField.getX()-spr_BigHeart[1].getWidth()*4.0f){
                                spr_BigHeart[1].setPosition(spr_BigHeart[1].getX()-Gdx.graphics.getDeltaTime()*600.0f, spr_BigHeart[0].getY());}
                                else{APPLYING_LIFE_STATE=4;lifeApplyingTimer=System.currentTimeMillis()+1000;}
                                spr_BigHeart[1].draw(batch);
                                prc_bigHeartBlood_left.getEmitters().first().setPosition(spr_BigHeart[1].getX()+spr_BigHeart[1].getWidth()*0.4f,spr_BigHeart[1].getY());
                                prc_bigHeartBlood_left.draw(batch,Gdx.graphics.getDeltaTime());
                                //if(spr_BigHeart[2].getX()<spr_mainField.getX()+spr_BigHeart[2].getWidth()){
                                spr_BigHeart[2].setPosition(spr_BigHeart[2].getX()+Gdx.graphics.getDeltaTime()*600.0f, spr_BigHeart[0].getY());
                            //}
                                spr_BigHeart[2].draw(batch);
                                prc_bigHeartBlood_right.getEmitters().first().setPosition(spr_BigHeart[2].getX()+spr_BigHeart[2].getWidth()*0.5f,spr_BigHeart[2].getY());
                                prc_bigHeartBlood_right.draw(batch,Gdx.graphics.getDeltaTime());
                                if(int_numOfRemovedBricksByLifeBonus<int_numOfAllBricksForLifeBonus){
                                    int i = rand.nextInt(6);int j = rand.nextInt(13);
                                    if(int_bricksArray2D[i][j]!=0){
                                        int_bricksArray2D[i][j]=0;int_numOfRemovedBricksByLifeBonus++;
                                    }
                                }
                            }else{
                                if(!prc_bigHeartBlood_left.isComplete()){prc_bigHeartBlood_left.reset();}
                                if(!prc_bigHeartBlood_right.isComplete()){prc_bigHeartBlood_right.reset();}
                            }
                        }


                        batch.flush();
                        batch.setShader(shaderProgramDefault);
                    }

                    spr_Light0_0.setPosition(spr_mainField.getX()+10,spr_mainField.getY()+11);
                    spr_Light0_0.draw(batch);

                }else{
                    if(APPLYING_LIFE_STATE==4){
                        if(bool_soundEnable){sfx_switch_1.setVolume(sfx_switch_1.play(),1.0f+float_volumeIncrement);}
                        APPLYING_LIFE_STATE=5;GAME_STATE=2;
                        calculatePhysics();
                        saveProgress();
                        GAME_STATE=3;
                        music_theme.play();
                    }
                }
            }
            if(APPLYING_LIFE_STATE==5&&GAME_STATE==3&&spr_DisplayForShader_1.getColor().a>0.0f) {
                spr_DisplayForShader_1.setAlpha(spr_DisplayForShader_1.getColor().a - 0.01f);
                spr_DisplayForShader_1.setPosition(spr_mainField.getX() + 10, spr_mainField.getY() + 11);
                spr_DisplayForShader_1.draw(batch);
            }else{if(APPLYING_LIFE_STATE==5){APPLYING_LIFE_STATE=0;}}

        //menu 0
        if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-3){
            //лейблы
            spr_MenuBackground.setPosition(0,0);
            spr_MenuBackground.draw(batch);
            if(GAME_STATE==0&&MENU_STATE==0){spr_menuLabels[0].setPosition(spr_mainField.getX()+45,spr_mainField.getY()+540);
                spr_menuLabels[0].draw(batch);}//лейбл пауза
            if(GAME_STATE==-1&&MENU_STATE==0){spr_menuLabels[1].setPosition(spr_mainField.getX()+45,spr_mainField.getY()+540);
                spr_menuLabels[1].draw(batch);}//лейбл игра окончена
            if(MENU_STATE==2){spr_menuLabels[2].setPosition(spr_mainField.getX()+45,spr_mainField.getY()+540);
                spr_menuLabels[2].draw(batch);}//лейбл нов.игра
            if(MENU_STATE==1){spr_menuLabels[3].setPosition(spr_mainField.getX()+45,spr_mainField.getY()+540);
                spr_menuLabels[3].draw(batch);}//лейбл выйти из игры
            if(MENU_STATE==31||MENU_STATE==32){spr_menuLabels[4].setPosition(spr_mainField.getX()+45,spr_mainField.getY()+540);
                spr_menuLabels[4].draw(batch);}//лейбл настройки

            //кнопки
            if(GAME_STATE==0&&MENU_STATE==0){spr_menuButtons[0].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+415);
                spr_menuButtons[0].draw(batch);}//кнопка вернуться
            if(MENU_STATE==0){
                spr_menuButtons[1].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+290);
                spr_menuButtons[1].draw(batch);//кнопка нов.игра
                spr_menuButtons[2].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+165);
                spr_menuButtons[2].draw(batch);//кнопка настр.
                spr_menuButtons[3].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+40);
                spr_menuButtons[3].draw(batch);}//кнопка выход
            if(MENU_STATE==1||MENU_STATE==2){
                spr_menuButtons[4].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+320);
                spr_menuButtons[4].draw(batch);//кнопка да
                spr_menuButtons[5].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+195);
                spr_menuButtons[5].draw(batch);//кнопка нет
            }
            if(MENU_STATE==31){
                spr_menuButtons[10].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+40);
                spr_menuButtons[10].draw(batch);//кнопка next

                if(bool_soundEnable){spr_switchLight.setColor(Color.GREEN);
                    if(spr_switchLight.isFlipX()){spr_switchLight.setFlip(false,false);}
                }
                else{spr_switchLight.setColor(Color.RED);
                    if(!spr_switchLight.isFlipX()){spr_switchLight.setFlip(true,false);}
                }
                spr_switchLight.setPosition(spr_mainField.getX()+38,spr_mainField.getY()+425);
                spr_switchLight.draw(batch);
                spr_menuButtons[11].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+415);
                spr_menuButtons[11].draw(batch);//кнопка свитчера звука

                spr_ProfileNumbersAtlas[int_profileNumber-1].setPosition(spr_mainField.getX()+240,spr_mainField.getY()+310);
                spr_ProfileNumbersAtlas[int_profileNumber-1].draw(batch);
                spr_menuButtons[8].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+290);
                spr_menuButtons[8].draw(batch);//кнопка profile

                if(int_language==0){spr_FlagsAtlas[0].setPosition(spr_mainField.getX()+70,spr_mainField.getY()+182);//ENG
                    spr_FlagsAtlas[0].draw(batch);
                    spr_FlagsAtlas[1].setPosition(spr_FlagsAtlas[0].getX()+120,spr_FlagsAtlas[0].getY());
                    spr_FlagsAtlas[1].draw(batch);}
                else{spr_FlagsAtlas[2].setPosition(spr_mainField.getX()+130,spr_mainField.getY()+182);//RU
                    spr_FlagsAtlas[2].draw(batch);
                    }
                spr_menuButtons[9].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+165);
                spr_menuButtons[9].draw(batch);//кнопка language


            }
            if(MENU_STATE==32){
                spr_menuButtons[6].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+40);
                spr_menuButtons[6].draw(batch);//кнопка принять

                if(bool_ghostEnabled){spr_switchLight.setColor(Color.GREEN);
                    if(spr_switchLight.isFlipX()){spr_switchLight.setFlip(false,false);}
                }
                else{spr_switchLight.setColor(Color.RED);
                    if(!spr_switchLight.isFlipX()){spr_switchLight.setFlip(true,false);}
                }
                spr_switchLight.setPosition(spr_mainField.getX()+38,spr_mainField.getY()+425);
                spr_switchLight.draw(batch);
                spr_menuButtons[7].setPosition(spr_mainField.getX()+30,spr_mainField.getY()+415);
                spr_menuButtons[7].draw(batch);//кнопка свитчера призрака

            }
        }
            //HELP MENU
        if(MENU_STATE==100){
            spr_MenuBackground.setPosition(0,0);
            spr_MenuBackground.draw(batch);
            enableCropShader(0);
            spr_Help[0].draw(batch);//need draw optimization!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            spr_Help[1].draw(batch);//need draw optimization!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            spr_GameName.setRegion(animation_GameName.getKeyFrame(float_deltaTimer));
            spr_GameName.setPosition(spr_Help[0].getX(),spr_Help[0].getY()+spr_Help[0].getHeight()-spr_GameName.getHeight());
            spr_GameName.draw(batch);//need draw optimization!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            spr_bricks[14].setPosition(spr_Help[1].getX()+148,spr_Help[1].getY()+1506);
            spr_bricks[14].draw(batch);
            getSpecialSpriteAnimationAndDraw(100,14,true);

            spr_bricks[16].setPosition(spr_Help[1].getX()+148,spr_Help[1].getY()+1373);
            spr_bricks[16].draw(batch);
            getSpecialSpriteAnimationAndDraw(102,16,true);

            spr_bricks[14].setPosition(spr_Help[1].getX()+148,spr_Help[1].getY()+1231);
            spr_bricks[14].draw(batch);
            getSpecialSpriteAnimationAndDraw(103,14,true);

            spr_bricks[16].setPosition(spr_Help[1].getX()+148,spr_Help[1].getY()+1048);
            spr_bricks[16].draw(batch);
            getSpecialSpriteAnimationAndDraw(104,16,true);

            spr_bricks[15].setPosition(spr_Help[1].getX()+148,spr_Help[1].getY()+865);
            spr_bricks[15].draw(batch);
            getSpecialSpriteAnimationAndDraw(101,15,true);

            spr_bricks[17].setPosition(spr_Help[1].getX()+88,spr_Help[1].getY()+599);
            spr_bricks[17].draw(batch);
            getSpecialSpriteAnimationAndDraw(888,17,true);

            spr_bricks[17].setPosition(spr_Help[1].getX()+88,spr_Help[1].getY()+599);
            spr_bricks[17].draw(batch);
            getSpecialSpriteAnimationAndDraw(888,17,true);

            spr_bricks[18].setPosition(spr_Help[1].getX()+147,spr_Help[1].getY()+599);
            spr_bricks[18].draw(batch);
            getSpecialSpriteAnimationAndDraw(888,18,true);

            spr_bricks[19].setPosition(spr_Help[1].getX()+207,spr_Help[1].getY()+599);
            spr_bricks[19].draw(batch);
            getSpecialSpriteAnimationAndDraw(888,19,true);

            batch.setShader(shaderProgramDefault);
        }

        //noise shader !performance intensive!
            if(bool_DisplayNoiseShaderEnabled&&GAME_STATE!=-2){

                //auto graphics settings
                if(SYSTEM_STATE==1&&bool_DisplayNoiseShaderEnabled&&Gdx.graphics.getFramesPerSecond()<30&&lowFPSTimer==0&&Gdx.app.getType()==Android){
                    lowFPSTimer=System.currentTimeMillis()+3000;}
                if(SYSTEM_STATE==1&&lowFPSTimer!=0&&lowFPSTimer<System.currentTimeMillis()&&Gdx.graphics.getFramesPerSecond()<30&&Gdx.app.getType()==Android){
                    bool_DisplayNoiseShaderEnabled=false;}

                batch.setShader(shaderProgramNoise_1);
                shaderProgramNoise_1.setUniformf("u_resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-3){shaderProgramNoise_1.setUniformf("color",0.6f,0.6f,0.6f);}
                else{shaderProgramNoise_1.setUniformf("color",0.0f,0.0f,0.0f);}
                shaderProgramNoise_1.setUniformf("u_time",float_time);
                spr_DisplayForShader_1.setPosition(spr_mainField.getX()+13,spr_mainField.getY()+13);
                spr_DisplayForShader_2.setPosition(spr_nextBrickField.getX()+13,spr_nextBrickField.getY()+13);
                spr_DisplayForShader_3.setPosition(spr_hiddenBricksField.getX(),spr_hiddenBricksField.getY()+2);
                spr_DisplayForShader_1.draw(batch);
                spr_DisplayForShader_3.draw(batch);
                batch.setShader(shaderProgramNoise_1);
                shaderProgramNoise_1.setUniformf("u_time",float_time+float_time+30.0f);
                if(GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-3){shaderProgramNoise_1.setUniformf("u_color",0.6f,0.6f,0.6f);}
                else{shaderProgramNoise_1.setUniformf("u_color",0.0f,0.0f,0.0f);}
                spr_DisplayForShader_2.draw(batch);
                batch.setShader(shaderProgramDefault);
            }


          //  spr_ShatteredGlass[0].setPosition(spr_mainField.getX(),spr_mainField.getY());
          //  spr_ShatteredGlass[0].draw(batch);
        spr_MainMenuBackgroundDisplays.setPosition(0,0);
        spr_MainMenuBackgroundDisplays.draw(batch);


        //Blood background alpha indication

            if(int_time%2==0){
        if(spr_BloodBackground.getColor().a>float_BloodBackgroundAlphaFresh+0.01f){
           spr_BloodBackground.setAlpha(spr_BloodBackground.getColor().a-=Gdx.graphics.getDeltaTime()*0.5f);
            if(spr_BloodBackground.getColor().a<0.0f){spr_BloodBackground.setAlpha(0.0f);}
            }else{
                  if(spr_BloodBackground.getColor().a<float_BloodBackgroundAlphaFresh-0.01f){
                            spr_BloodBackground.setAlpha(spr_BloodBackground.getColor().a+=Gdx.graphics.getDeltaTime()*0.5f);
                    }
                    if(spr_BloodBackground.getColor().a>1.0f){spr_BloodBackground.setAlpha(1.0f);}
                }
            }
        spr_BloodBackground.draw(batch);


        if(bool_viktorBombExploded){
            enableCropShader(0);
            spr_ShatteredGlassAfterExplosion_1.draw(batch);
            float alpha=spr_ShatteredGlassAfterExplosion_1.getColor().a;
            if(alpha>0.0f&&int_time%25==0){spr_ShatteredGlassAfterExplosion_1.setAlpha(alpha-0.01f);}
            else{if(alpha<=0.0f){bool_viktorBombExploded=false;}
            }
            batch.setShader(shaderProgramDefault);
        }
            //test shader

/**
            if(GAME_STATE!=-2&&1<0){
                shaderProgramTest.setUniformf("resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                batch.setShader(shaderProgramTest);
                //spr_DisplayForShader_1.setPosition(spr_mainField.getX()+13,spr_mainField.getY()+13);
                spr_ShadersTest.setPosition(0,0);
                shaderProgramTest.setUniformf("color",1.0f,1.0f,1.0f);
                Vector3 vector3 = new Vector3(); vector3.x=spr_ShadersTest.getX();vector3.y=spr_ShadersTest.getY();vector3.z=0.0f;

                float proportion = 0.0f;
                float width = Gdx.graphics.getWidth();
                float height = Gdx.graphics.getHeight();
               // if(Gdx.graphics.getWidth()<camera.viewportWidth&&Gdx.graphics.getWidth()>Gdx.graphics.getHeight()){proportion=Gdx.graphics.getWidth()/720.0f;}
                //if(Gdx.graphics.getWidth()>=camera.viewportWidth){proportion=Gdx.graphics.getHeight()/1280.0f;}

                proportion = Math.min(width/720.0f,height/1280.0f);

                float mutatedSpriteWidth = spr_ShadersTest.getWidth()*proportion;
                float mutatedSpriteHeight = spr_ShadersTest.getHeight()*proportion;
                float freeScreenSpaceWidth = Gdx.graphics.getWidth()-mutatedSpriteWidth;
                float freeScreenSpaceHeight = Gdx.graphics.getHeight()-mutatedSpriteHeight;
                float screenSpriteCoord_X1 = freeScreenSpaceWidth*0.5f+71.0f*proportion;// 0-X1
                float screenSpriteCoord_X2 = freeScreenSpaceWidth*0.5f+mutatedSpriteWidth-290.0f*proportion;
                float screenSpriteCoord_Y1 = freeScreenSpaceHeight*0.5f+550.0f*proportion;// 0-Y1
                float screenSpriteCoord_Y2 = freeScreenSpaceHeight*0.5f+mutatedSpriteHeight-130.0f*proportion;
                Vector2 vector2_x = new Vector2(screenSpriteCoord_X1,screenSpriteCoord_X2);
                Vector2 vector2_y = new Vector2(screenSpriteCoord_Y1,screenSpriteCoord_Y2);

                shaderProgramTest.setUniformf("resolution",Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                shaderProgramTest.setUniformf("bounds_x",vector2_x);shaderProgramTest.setUniformf("bounds_y",vector2_y);
                shaderProgramTest.setUniformf("time",float_time);

                //spr_DisplayForShader_1.draw(batch);

                spr_ShadersTest.draw(batch);
                batch.setShader(shaderProgramTest);
                batch.setShader(shaderProgramDefault);
            }
**/
       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(spr_btnLeft.getX(), spr_btnLeft.getY(), spr_btnLeft.getWidth()*0.5f*spr_btnLeft.getScaleX());
        shapeRenderer.end();*/

    }else{
            if(bool_ShowLogo){
                //STARTING LOGO and Loading Progress

                loadSectionAssets();
                //  myAssetsLoader.assetManager.finishLoading();
                // Gdx.app.log("'","Loaded");

             //   if(Gdx.graphics.getFramesPerSecond()>1){//ждем загрузки ассетов
                float posY=spr_OnStartLogo0[0].getY();
                    batch.setShader(shaderProgramDefault);
                    if(float_brightness <1.0f&&int_ShowLogoStage==0&&section>=1){
                        float_brightness +=Gdx.graphics.getDeltaTime()*0.3f;
                        //Gdx.app.log("'","br="+float_brightness);
                        shaderProgramDefault.setUniformf("uniform_vec4_color",Color.WHITE);
                        shaderProgramDefault.setUniformf("uniform_float_brightness", float_brightness);
                    }
                    if(float_brightness >=1.0f&&int_ShowLogoStage==0&&section==4){int_ShowLogoStage=1;float_brightness=1.0f;}
                    if(float_brightness >0.0f&&int_ShowLogoStage==1){
                        float_brightness -=Gdx.graphics.getDeltaTime()*0.8f;
                        shaderProgramDefault.setUniformf("uniform_float_brightness", float_brightness);
                    }

                    if(float_brightness <=0.0f&&int_ShowLogoStage==1){int_ShowLogoStage=2;bool_Loading=false;
                    myAssetsLoader.assetManager.finishLoading();
                        if(bool_soundEnable){music_menu[int_music_menu_index].play();}
                    }

               if(bool_ShowVitekLogo){
                    spr_OnStartLogoVitek.setPosition(0,viewport.getWorldHeight()-spr_OnStartLogoVitek.getHeight()- spr_OnStartLogoVitek.getHeight()*0.35f);
                    spr_OnStartLogoVitek.draw(batch);}
               else{


                   if(section<4&&section>=1&&float_brightness>0.3f){
                       if(int_onStartSFX==0){sfx_onStart_0.setVolume(sfx_onStart_0.play(),1.0f+float_volumeIncrement);int_onStartSFX=1;}
                       showLogo0Timer=System.currentTimeMillis()+50;
                       if(posY<-100.0f){posY+=Gdx.graphics.getDeltaTime()*4000.0f;}
                       spr_OnStartLogo0[0].setPosition(0,posY);
                       spr_OnStartLogo0[0].draw(batch);
                   }
                   if(section==4){
                       if(int_onStartSFX==1){sfx_onStart_1.setVolume(sfx_onStart_1.play(),1.0f+float_volumeIncrement);int_onStartSFX=2;}
                       if(showLogo0Timer>System.currentTimeMillis()){
                           spr_OnStartLogo0[1].setPosition(0,posY);
                           spr_OnStartLogo0[1].draw(batch);
                       }
                       else{
                         //  posY=spr_OnStartLogo0[0].getY();
                           if(posY>-1330.0f){posY-=Gdx.graphics.getDeltaTime()*2000.0f;}
                           spr_OnStartLogo0[2].setPosition(0,posY);
                           spr_OnStartLogo0[2].draw(batch);
                       }

                   }
               }

            }
        }
      //  }
        batch.end();}

        void enableCropShader(int region){
            float width = Gdx.graphics.getWidth();
            float height = Gdx.graphics.getHeight();
            float proportion = Math.min(width/720.0f,height/1280.0f);
            float mutatedSpriteWidth = 720.0f*proportion;
            float mutatedSpriteHeight = 1280.0f*proportion;
            float freeScreenSpaceWidth = Gdx.graphics.getWidth()-mutatedSpriteWidth;
            float freeScreenSpaceHeight = Gdx.graphics.getHeight()-mutatedSpriteHeight;
            float screenSpriteCoord_X1,screenSpriteCoord_X2,screenSpriteCoord_Y1,screenSpriteCoord_Y2;
            Vector2 vector2_x = new Vector2(0,0);
            Vector2 vector2_y = new Vector2(0,0);

            if(region==0){//main field
                screenSpriteCoord_X1 = freeScreenSpaceWidth*0.5f+70.0f*proportion;// 0-X1
                screenSpriteCoord_X2 = screenSpriteCoord_X1+361.0f*proportion;//361-mainfield width
                screenSpriteCoord_Y1 = freeScreenSpaceHeight*0.5f+550.0f*proportion;// 0-Y1
                screenSpriteCoord_Y2 = screenSpriteCoord_Y1+600.0f*proportion;//600-mainfield height
                vector2_x = new Vector2(screenSpriteCoord_X1,screenSpriteCoord_X2);
                vector2_y = new Vector2(screenSpriteCoord_Y1,screenSpriteCoord_Y2);
            }

            if(region==1){//next brick field
                screenSpriteCoord_X1 = freeScreenSpaceWidth*0.5f+469.0f*proportion;// 0-X1
                screenSpriteCoord_X2 = screenSpriteCoord_X1+180.0f*proportion;
                screenSpriteCoord_Y1 = freeScreenSpaceHeight*0.5f+970.0f*proportion;// 0-Y1
                screenSpriteCoord_Y2 = screenSpriteCoord_Y1+180.0f*proportion;
                vector2_x = new Vector2(screenSpriteCoord_X1,screenSpriteCoord_X2);
                vector2_y = new Vector2(screenSpriteCoord_Y1,screenSpriteCoord_Y2);
            }
            batch.setShader(shaderProgramCrop);
            shaderProgramCrop.setUniformf("uniform_vec4_color",Color.WHITE);
            shaderProgramCrop.setUniformf("uniform_float_brightness",float_brightness);
            shaderProgramCrop.setUniformf("resolution",width,height);
            shaderProgramCrop.setUniformf("bounds_x",vector2_x);
            shaderProgramCrop.setUniformf("bounds_y",vector2_y);
    }

        void loadSectionAssets(){
            if(section==1){
                if(!myAssetsLoader.bool_loadingMusic){
                    myAssetsLoader.loadMusic();myAssetsLoader.bool_loadingMusic=true;}
                if(!myAssetsLoader.assetManager.update()){
                    myAssetsLoader.progress = myAssetsLoader.assetManager.getProgress();
                    Gdx.app.log("'","Loading Music... " + myAssetsLoader.progress * 100 + "%");
                }else{section+=1;myAssetsLoader.assetManager.finishLoading();}

            }
            if(section==2){
                if(!myAssetsLoader.bool_loadingSounds){
                    myAssetsLoader.loadSounds();myAssetsLoader.bool_loadingSounds=true;}
                if(!myAssetsLoader.assetManager.update()){
                    myAssetsLoader.progress = myAssetsLoader.assetManager.getProgress();
                    Gdx.app.log("'","Loading Sounds... " + myAssetsLoader.progress * 100 + "%");
                }else{section+=1;myAssetsLoader.assetManager.finishLoading();}
            }
            if(section==3){

                if(!myAssetsLoader.bool_loadingTextures){
                    myAssetsLoader.loadTextures();myAssetsLoader.bool_loadingTextures=true;}

                if(!myAssetsLoader.assetManager.update()){
                    myAssetsLoader.progress = myAssetsLoader.assetManager.getProgress();
                    Gdx.app.log("'","Loading Textures... " + myAssetsLoader.progress * 100 + "%");
                }else{section+=1;myAssetsLoader.assetManager.finishLoading();
                if(bool_ShowLogo){create();}
                }
            }
        }
    void resetSpecialStats(){
        velocityTimerIncrement=velocityTimerIncrementDefault;
        hiddenBricksTimerIncrement=hiddenBricksTimerIncrementDefault;
        bool_4XStones=false;
        int_numOfLives=0;
    }
    void calculateSpecialStats(int i, int j){
        if (int_bricksArray2D[i][j]>=100){
            switch (int_bricksArray2D[i][j]){
                case 100://pos falling speed down
                    velocityTimerIncrement+=50;
                    break;
                case 101://neu
                    bool_4XStones=true;
                    break;
                case 102://neg falling speed up
                    velocityTimerIncrement-=50;
                    break;
                case 103://pos timer speed down
                    hiddenBricksTimerIncrement+=500;
                    break;
                case 104://neg timer speed up
                    hiddenBricksTimerIncrement-=500;
                    break;
                case 888://mag plus life
                    int_numOfLives+=1;
                    break;
                default:Gdx.app.log("'","Something wrong in calculateSpecialStats!!!");break;
            }
        }//Gdx.app.log("'","velocityTimerIncrement="+velocityTimerIncrement);
    }

    public void getSpecialSpriteAnimationAndDraw(int in_type, int f, boolean isHelpMenu){
        if((GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-3)||MENU_STATE==100){
            switch (in_type){
                case 100://pos falling speed down
                    spr_positiveBrickFallingSpeedDown.setRegion(animation_positiveBrickFallingSpeedDown.getKeyFrame(float_deltaTimer));
                    spr_positiveBrickFallingSpeedDown.setAlpha(spr_bricks[f].getColor().a);
                    spr_positiveBrickFallingSpeedDown.setPosition(spr_bricks[f].getX(),spr_bricks[f].getY());
                    spr_positiveBrickFallingSpeedDown.draw(batch);
                    break;

                case 101://neu
                    spr_neutral3XStonesTo4XStones.setRegion(animation_neutral3XStonesTo4XStones.getKeyFrame(float_deltaTimer));
                    spr_neutral3XStonesTo4XStones.setAlpha(spr_bricks[f].getColor().a);
                    spr_neutral3XStonesTo4XStones.setPosition(spr_bricks[f].getX(),spr_bricks[f].getY());
                    spr_neutral3XStonesTo4XStones.draw(batch);
                    break;

                case 102://neg falling speed up
                    spr_negativeBrickFallingSpeedUp.setRegion(animation_negativeBrickFallingSpeedUp.getKeyFrame(float_deltaTimer));
                    spr_negativeBrickFallingSpeedUp.setAlpha(spr_bricks[f].getColor().a);
                    spr_negativeBrickFallingSpeedUp.setPosition(spr_bricks[f].getX(),spr_bricks[f].getY());
                    spr_negativeBrickFallingSpeedUp.draw(batch);
                    break;
                case 103://pos timer speed down
                    spr_positiveTimerSpeedDown.setRegion(animation_positiveTimerSpeedDown.getKeyFrame(float_deltaTimer));
                    spr_positiveTimerSpeedDown.setAlpha(spr_bricks[f].getColor().a);
                    spr_positiveTimerSpeedDown.setPosition(spr_bricks[f].getX(),spr_bricks[f].getY());
                    spr_positiveTimerSpeedDown.draw(batch);
                    break;
                case 104://neg timer speed up
                    spr_negativeTimerSpeedUp.setRegion(animation_negativeTimerSpeedUp.getKeyFrame(float_deltaTimer));
                    spr_negativeTimerSpeedUp.setAlpha(spr_bricks[f].getColor().a);
                    spr_negativeTimerSpeedUp.setPosition(spr_bricks[f].getX(),spr_bricks[f].getY());
                    spr_negativeTimerSpeedUp.draw(batch);
                    break;
                case 888://mag plus life
                    spr_magicPlusLife.setRegion(animation_magicPlusLife.getKeyFrame(float_deltaTimer));
                    spr_magicPlusLife.setAlpha(spr_bricks[f].getColor().a);
                    spr_magicPlusLife.setPosition(spr_bricks[f].getX(),spr_bricks[f].getY());
                    spr_magicPlusLife.draw(batch);
                    break;
                default:break;
            }
        }
    }
    public int generateSpecialBrick(int in_type){//type 0 positive, 1 neutral, 2 negative
        int out_type;
        if(in_type==3){
            in_type = rand.nextInt(100);
            if(in_type<30){in_type=0;}
            if(in_type>=30&&in_type<50){in_type=1;}
            if(in_type>=50){in_type=2;}
        }//random type

        switch (in_type){
            case 0://positive
                switch (rand.nextInt(2)){
                    case 0:out_type=100;break;//falling speed down
                    case 1:out_type=103;break;//timer speed down
                    default:out_type=100;break;//falling speed down
                }break;

            case 1://neutral
                switch (rand.nextInt(1)){
                    case 0:out_type=101;break;//3X Stones to 4x Stones
                    default:out_type=101;break;//3X Stones to 4x Stones
                }break;

            case 2://negative
                switch (rand.nextInt(2)){
                    case 0:out_type=102;break;//falling speed up
                    case 1:out_type=104;break;//timer speed up
                    default:out_type=102;break;//falling speed up
                }break;
            default:out_type=int_const_stoneBrickNumber+7;Gdx.app.log("'","SpecialBrickGenerate ERROR, int_type must be 0, 1 or 2");break;
        }


        return out_type;
    }


    public int decodeSpecialBrick(int in_type){//used for sprite detection from bonus type
        int out_type;
        switch (in_type){
            case 100://pos falling speed down
            case 103://pos timer speed down
                out_type=int_const_stoneBrickNumber+8;break;
            case 101:out_type=int_const_stoneBrickNumber+9;break;//neu 3x stone to 4x
            case 102://neg falling speed up
            case 104://neg timer speed up
                out_type=int_const_stoneBrickNumber+10;break;
            case 888://mag plus life
                out_type=int_const_stoneBrickNumber+11;break;
            default:out_type=int_const_stoneBrickNumber+7;Gdx.app.log("'","SpecialBrickDecode ERROR, int_type must be exist");break;
        }

        return out_type;
    }
    void lifeBonusCalculation(){
        //Next prize is special magic plus life?
        if(int_lifeScore>=3000&&int_nextBricksArray2D[1][1]!=888&&int_fallingBrickGPS[1][1][2]!=888){
            if(int_numOfLives==0){ bool_nextPrizeIsLife=true;int_lifeBrickDamage=0;int_lifeScore=0;}
            else{//if life scored but there is already one on the main filed then regen life's life=)
                if(int_lifeBrickDamage!=0){
                    int_lifeBrickDamage=0;int_lifeScore=0;
                    sfx_lifeRegen_0.setVolume(sfx_lifeRegen_0.play(),0.8f+float_volumeIncrement);
                    bool_lifeBrickRegen=true;
                    for(int i=0;i<6;i++){
                        for(int j=0;j<13;j++){
                            //particles
                            if(int_bricksArray2D[i][j]==888&&j>2){
                                ParticleEffectPool.PooledEffect pooledEffect_4 = particleEffectPool_4.obtain();
                                pooledEffect_4.setPosition(getSolidBrickPosX(i),getSolidBrickPosY(j));
                                array_prc_effects_4.add(pooledEffect_4);
                            }
                        }
                    }

                }else{int_lifeScore=0;}//if life score reached and life exists and no damage
            }
        }
    }
    void calculatePhysics(){//Функция "физики" V2
        countBonusChanceAndBloodBackAlpha();
        resetSpecialStats();
        int int_figureFallOnPieces=0;
        for (int i=0;i<6;i++){
            for (int j=12;j>=0;j--){
                calculateSpecialStats(i,j);
                if(j<12){
                    if (int_bricksArray2D[i][j]!=0){
                        for(int jn=j;jn<13;jn++){
                            if(jn+1<13&&int_bricksArray2D[i][jn+1]==0){
                                if(int_figureFallOnPieces==0){int_figureFallOnPieces=1;}
                                int_bricksArray2D[i][jn+1]=int_bricksArray2D[i][jn];
                                int_bricksArray2D[i][jn]=0;
                            }
                        }
                    }
                }
            }
        }
        if(int_brick2X2stage==5||int_brick3X3stage==5){//Gdx.app.log("'","int_brick3X3stage="+int_brick3X3stage+" int_brick2X2stage="+int_brick2X2stage);
            if(int_figureFallOnPieces==1){if(bool_soundEnable){sfx_landed_1.setVolume(sfx_landed_1.play(),0.5f+float_volumeIncrement);}}//sfx when big figure fall on pieces
            int_brick2X2stage=0;int_brick3X3stage=0;}//3x3 2x2
        lifeBonusCalculation();
    }
    void calculatePhysicsForGhost(){//Функция "физики" ghost
        for(int i=0;i<3;i++){//столбец
            for(int j=0;j<3;j++){//строка
                if(int_fallingBrickGPS[i][j][2]!=0){
                    int_fallingBrickGPSghost[i][j][2]=int_fallingBrickGPS[i][j][2];//k
                    int_fallingBrickGPSghost[i][j][1]=int_fallingBrickGPS[i][j][1];//x
                    int_fallingBrickGPSghost[i][j][0]=int_fallingBrickGPS[i][j][0];//y
                    for(int jn=0,c=2;jn<13;jn++){
                        if(jn+1<13&&int_bricksArray2D[int_fallingBrickGPS[i][j][1]][jn+1]==0){
                            for(int ig=i+1;ig<3;ig++){
                                if(int_fallingBrickGPS[ig][j][2]!=0){c++;}
                            }
                            int_fallingBrickGPSghost[i][j][0]=jn-c;c=2;
                        }
                    }
                }
            }
        }
    }

    int checkButtonPress(){//Функция, узнающая нажатую кнопку

        if(!bool_Loading){
        for(int i=0;i<5;i++){
            if(touches.get(i).touched){
                touchX=(int)touches.get(i).touchX;touchY=(int)touches.get(i).touchY;
                touches.get(i).touched=false;

                if(GAME_STATE!=0){
                //left arrow button
                if(touchX>spr_buttons[1].getX()
                        &&touchX<spr_buttons[1].getX()+spr_buttons[1].getWidth()*spr_buttons[1].getScaleX()
                        &&touchY>spr_buttons[1].getY()
                        &&touchY<spr_buttons[1].getY()+spr_buttons[1].getHeight()*spr_buttons[1].getScaleY()
                ){
                    bool_buttonPressedGlobal[1]=true;return 1;}
                //right arrow button
                if(touchX>spr_buttons[2].getX()
                        &&touchX<spr_buttons[2].getX()+spr_buttons[2].getWidth()*spr_buttons[2].getScaleX()
                        &&touchY>spr_buttons[2].getY()
                        &&touchY<spr_buttons[2].getY()+spr_buttons[2].getHeight()*spr_buttons[2].getScaleY()
                ){
                    bool_buttonPressedGlobal[2]=true;return 2;}
                //down arrow button
                if(touchX>spr_buttons[0].getX()
                        &&touchX<spr_buttons[0].getX()+spr_buttons[0].getWidth()*spr_buttons[0].getScaleX()
                        &&touchY>spr_buttons[0].getY()
                        &&touchY<spr_buttons[0].getY()+spr_buttons[0].getHeight()*spr_buttons[0].getScaleY()
                ){
                    bool_buttonPressedGlobal[0]=true;return 3;}
                //shuffle arrow button
                if(touchX>spr_buttons[3].getX()
                        &&touchX<spr_buttons[3].getX()+spr_buttons[3].getWidth()*spr_buttons[3].getScaleX()
                        &&touchY>spr_buttons[3].getY()
                        &&touchY<spr_buttons[3].getY()+spr_buttons[3].getHeight()*spr_buttons[3].getScaleY()
                ){
                    bool_buttonPressedGlobal[3]=true;return 4;}
                //pause button
                if(GAME_STATE!=0&&GAME_STATE!=-1&&GAME_STATE!=-3){
                if(touchX>spr_buttons[4].getX()
                        &&touchX<spr_buttons[4].getX()+spr_buttons[4].getWidth()*spr_buttons[4].getScaleX()
                        &&touchY>spr_buttons[4].getY()
                        &&touchY<spr_buttons[4].getY()+spr_buttons[4].getHeight()*spr_buttons[4].getScaleY()
                ){
                    bool_buttonPressedGlobal[4]=true;return 5;}
                }
                }
                if((GAME_STATE==0||GAME_STATE==-1||GAME_STATE==-2||GAME_STATE==-3)&&MENU_STATE!=100){
                //help button
                if(touchX>spr_buttons[17].getX()
                        &&touchX<spr_buttons[17].getX()+spr_buttons[17].getWidth()*spr_buttons[17].getScaleX()
                        &&touchY>spr_buttons[17].getY()
                        &&touchY<spr_buttons[17].getY()+spr_buttons[17].getHeight()*spr_buttons[17].getScaleY()
                ){
                    bool_buttonPressedGlobal[5]=true;return 18;}
                }
                if(MENU_STATE==100){
                    //back button
                    if(touchX>spr_buttons[18].getX()
                            &&touchX<spr_buttons[18].getX()+spr_buttons[18].getWidth()*spr_buttons[18].getScaleX()
                            &&touchY>spr_buttons[18].getY()
                            &&touchY<spr_buttons[18].getY()+spr_buttons[18].getHeight()*spr_buttons[18].getScaleY()
                    ){
                        bool_buttonPressedGlobal[6]=true;return 19;}

                    //help field
                    if(touchX>spr_mainField.getX()+11
                            &&touchX<spr_mainField.getX()+spr_mainField.getWidth()*spr_mainField.getScaleX()+11
                            &&touchY>spr_mainField.getY()+11
                            &&touchY<spr_mainField.getY()+spr_mainField.getHeight()*spr_mainField.getScaleY()+11
                    ){bool_helpFieldPressed=true;}
                }

                //Menu 0 buttons
                //кнопка return
                if(touchX>spr_menuButtons[0].getX()
                        &&touchX<spr_menuButtons[0].getX()+spr_menuButtons[0].getWidth()*spr_menuButtons[0].getScaleX()
                        &&touchY>spr_menuButtons[0].getY()
                        &&touchY<spr_menuButtons[0].getY()+spr_menuButtons[0].getHeight()*spr_menuButtons[0].getScaleY()
                        &&MENU_STATE==0
                ){return 6;}
                //кнопка new game
                if(touchX>spr_menuButtons[1].getX()
                        &&touchX<spr_menuButtons[1].getX()+spr_menuButtons[1].getWidth()*spr_menuButtons[1].getScaleX()
                        &&touchY>spr_menuButtons[1].getY()
                        &&touchY<spr_menuButtons[1].getY()+spr_menuButtons[1].getHeight()*spr_menuButtons[1].getScaleY()
                        &&MENU_STATE==0
                ){return 7;}
                //кнопка настройки
                if(touchX>spr_menuButtons[2].getX()
                        &&touchX<spr_menuButtons[2].getX()+spr_menuButtons[2].getWidth()*spr_menuButtons[2].getScaleX()
                        &&touchY>spr_menuButtons[2].getY()
                        &&touchY<spr_menuButtons[2].getY()+spr_menuButtons[2].getHeight()*spr_menuButtons[2].getScaleY()
                        &&MENU_STATE==0
                ){return 8;}
                //кнопка выход
                if(touchX>spr_menuButtons[3].getX()
                        &&touchX<spr_menuButtons[3].getX()+spr_menuButtons[3].getWidth()*spr_menuButtons[3].getScaleX()
                        &&touchY>spr_menuButtons[3].getY()
                        &&touchY<spr_menuButtons[3].getY()+spr_menuButtons[3].getHeight()*spr_menuButtons[3].getScaleY()
                        &&MENU_STATE==0
                ){return 9;}
                //кнопка да
                if(touchX>spr_menuButtons[4].getX()
                        &&touchX<spr_menuButtons[4].getX()+spr_menuButtons[4].getWidth()*spr_menuButtons[4].getScaleX()
                        &&touchY>spr_menuButtons[4].getY()
                        &&touchY<spr_menuButtons[4].getY()+spr_menuButtons[4].getHeight()*spr_menuButtons[4].getScaleY()
                        &&(MENU_STATE==1||MENU_STATE==2)
                ){return 10;}
                //кнопка нет
                if(touchX>spr_menuButtons[5].getX()
                        &&touchX<spr_menuButtons[5].getX()+spr_menuButtons[5].getWidth()*spr_menuButtons[5].getScaleX()
                        &&touchY>spr_menuButtons[5].getY()
                        &&touchY<spr_menuButtons[5].getY()+spr_menuButtons[5].getHeight()*spr_menuButtons[5].getScaleY()
                        &&(MENU_STATE==1||MENU_STATE==2)
                ){return 11;}
                //кнопка принять
                if(touchX>spr_menuButtons[6].getX()
                        &&touchX<spr_menuButtons[6].getX()+spr_menuButtons[6].getWidth()*spr_menuButtons[6].getScaleX()
                        &&touchY>spr_menuButtons[6].getY()
                        &&touchY<spr_menuButtons[6].getY()+spr_menuButtons[6].getHeight()*spr_menuButtons[6].getScaleY()
                        &&(MENU_STATE==32)
                ){return 12;}
                //кнопка вкл/выкл призрака падающей фигуры
                if(touchX>spr_menuButtons[7].getX()
                        &&touchX<spr_menuButtons[7].getX()+spr_menuButtons[7].getWidth()*spr_menuButtons[7].getScaleX()
                        &&touchY>spr_menuButtons[7].getY()
                        &&touchY<spr_menuButtons[7].getY()+spr_menuButtons[7].getHeight()*spr_menuButtons[7].getScaleY()
                        &&(MENU_STATE==32)
                ){return 13;}
                //кнопка выбора профиля
                if(touchX>spr_menuButtons[8].getX()
                        &&touchX<spr_menuButtons[8].getX()+spr_menuButtons[8].getWidth()*spr_menuButtons[8].getScaleX()
                        &&touchY>spr_menuButtons[8].getY()
                        &&touchY<spr_menuButtons[8].getY()+spr_menuButtons[8].getHeight()*spr_menuButtons[8].getScaleY()
                        &&(MENU_STATE==31)
                ){return 14;}
                //кнока выбора языка
                if(touchX>spr_menuButtons[9].getX()
                        &&touchX<spr_menuButtons[9].getX()+spr_menuButtons[9].getWidth()*spr_menuButtons[9].getScaleX()
                        &&touchY>spr_menuButtons[9].getY()
                        &&touchY<spr_menuButtons[9].getY()+spr_menuButtons[9].getHeight()*spr_menuButtons[9].getScaleY()
                        &&(MENU_STATE==31)
                ){return 15;}
                //кнопка дальше
                if(touchX>spr_menuButtons[10].getX()
                        &&touchX<spr_menuButtons[10].getX()+spr_menuButtons[10].getWidth()*spr_menuButtons[10].getScaleX()
                        &&touchY>spr_menuButtons[10].getY()
                        &&touchY<spr_menuButtons[10].getY()+spr_menuButtons[10].getHeight()*spr_menuButtons[10].getScaleY()
                        &&(MENU_STATE==31)
                ){return 16;}
                //кнопка вкл/выкл звука
                if(touchX>spr_menuButtons[11].getX()
                        &&touchX<spr_menuButtons[11].getX()+spr_menuButtons[11].getWidth()*spr_menuButtons[11].getScaleX()
                        &&touchY>spr_menuButtons[11].getY()
                        &&touchY<spr_menuButtons[11].getY()+spr_menuButtons[11].getHeight()*spr_menuButtons[11].getScaleY()
                        &&(MENU_STATE==31)
                ){return 17;}
            }
        }}
        return 0;

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if(!bool_Loading){
            if(pointer < 5){
                touchVecXYZ.set(x,y,0);
                viewport.unproject(touchVecXYZ);
                touches.get(pointer).touchX = touchVecXYZ.x;
                touches.get(pointer).touchY = touchVecXYZ.y;
                touches.get(pointer).touched = true;
                touches.get(pointer).touchedGlobal = true;
            }
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {//old method
       /* touchVecXYZ.set(x,y,0);
        viewport.unproject(touchVecXYZ);
        Gdx.app.log("'","Tap_count="+count);
        touchX=(int)touchVecXYZ.x;touchY=(int)touchVecXYZ.y;
        Gdx.app.log("'","touch_x="+touchX+" touch_y="+touchY);*/
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

       float_touch_accel=velocityY;
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
    }

    //Input Processor
    @Override
    public boolean keyDown(int keycode) {
        if(!bool_Loading) {
            if (keycode == Input.Keys.BACK) {
                if (GAME_STATE != 0 && GAME_STATE != -1 && GAME_STATE != -2) {
                    GAME_STATE_BF = GAME_STATE;
                    GAME_STATE = 0;
                    if (bool_soundEnable) {
                        music_theme.pause();
                        music_menu[int_music_menu_index].stop();
                        int_music_menu_index = rand.nextInt(music_menu.length);
                        music_menu[int_music_menu_index].play();
                    }
                }
            }
        /* if(keycode == Input.Keys.CONTROL_LEFT){
         int_curScore++;
         }**/
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(!bool_Loading){
        if(pointer < 5){
            if(touches.get(pointer).touchX>spr_buttons[1].getX()
                    &&touches.get(pointer).touchX<spr_buttons[1].getX()+spr_buttons[1].getWidth()*spr_buttons[1].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[1].getY()
                    &&touches.get(pointer).touchY<spr_buttons[1].getY()+spr_buttons[1].getHeight()*spr_buttons[1].getScaleY()
            ){
                bool_buttonPressedGlobal[1]=false;}
            //right arrow button
            if(touches.get(pointer).touchX>spr_buttons[2].getX()
                    &&touches.get(pointer).touchX<spr_buttons[2].getX()+spr_buttons[2].getWidth()*spr_buttons[2].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[2].getY()
                    &&touches.get(pointer).touchY<spr_buttons[2].getY()+spr_buttons[2].getHeight()*spr_buttons[2].getScaleY()
            ){
                bool_buttonPressedGlobal[2]=false;}
            //down arrow button
            if(touches.get(pointer).touchX>spr_buttons[0].getX()
                    &&touches.get(pointer).touchX<spr_buttons[0].getX()+spr_buttons[0].getWidth()*spr_buttons[0].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[0].getY()
                    &&touches.get(pointer).touchY<spr_buttons[0].getY()+spr_buttons[0].getHeight()*spr_buttons[0].getScaleY()
            ){
                bool_buttonPressedGlobal[0]=false;}
            //shuffle arrow button
            if(touches.get(pointer).touchX>spr_buttons[3].getX()
                    &&touches.get(pointer).touchX<spr_buttons[3].getX()+spr_buttons[3].getWidth()*spr_buttons[3].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[3].getY()
                    &&touches.get(pointer).touchY<spr_buttons[3].getY()+spr_buttons[3].getHeight()*spr_buttons[3].getScaleY()
            ){
                bool_buttonPressedGlobal[3]=false;}
            //pause button
            if(touches.get(pointer).touchX>spr_buttons[4].getX()
                    &&touches.get(pointer).touchX<spr_buttons[4].getX()+spr_buttons[4].getWidth()*spr_buttons[4].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[4].getY()
                    &&touches.get(pointer).touchY<spr_buttons[4].getY()+spr_buttons[4].getHeight()*spr_buttons[4].getScaleY()
            ){
                bool_buttonPressedGlobal[4]=false;}
            //help button
            if(touches.get(pointer).touchX>spr_buttons[17].getX()
                    &&touches.get(pointer).touchX<spr_buttons[17].getX()+spr_buttons[17].getWidth()*spr_buttons[17].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[17].getY()
                    &&touches.get(pointer).touchY<spr_buttons[17].getY()+spr_buttons[17].getHeight()*spr_buttons[17].getScaleY()
            ){
                bool_buttonPressedGlobal[5]=false;}
            //back button
            if(touches.get(pointer).touchX>spr_buttons[18].getX()
                    &&touches.get(pointer).touchX<spr_buttons[18].getX()+spr_buttons[18].getWidth()*spr_buttons[18].getScaleX()
                    &&touches.get(pointer).touchY>spr_buttons[18].getY()
                    &&touches.get(pointer).touchY<spr_buttons[18].getY()+spr_buttons[18].getHeight()*spr_buttons[18].getScaleY()
            ){
                bool_buttonPressedGlobal[6]=false;}

            if(MENU_STATE==100&&(bool_helpFieldPressed|| int_helpFieldTouchCounter !=0)){
                bool_helpFieldPressed=false;
                int_helpFieldTouchCounter =0;
            }

            touches.get(pointer).touchX = -1;
            touches.get(pointer).touchY = -1;
            touches.get(pointer).touchedGlobal = false;
        }}
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if(MENU_STATE==100&&bool_helpFieldPressed&&pointer<1){
            touchDraggedXY.x=screenX;touchDraggedXY.y=screenY;
            viewport.unproject(touchDraggedXY);
            switch (int_helpFieldTouchCounter){
                case 0:touchY_HelpField[0]=touchDraggedXY.y;
                    int_helpFieldTouchCounter =1;
                    float_touch_accel=0.0f;
                    break;
                case 1:touchY_HelpField[1]=touchDraggedXY.y;
                    int_helpFieldTouchCounter =0;
                    int a=1;
                    if((touchY_HelpField[0]<touchY_HelpField[1])){a=1;}else {a=-1;}
                    float posY = spr_Help[0].getY()+(Math.abs(touchY_HelpField[0]-touchY_HelpField[1]))*a;
                    if(posY<spr_mainField.getY()+11-spr_Help[0].getHeight()+602){posY=spr_mainField.getY()+11-spr_Help[0].getHeight()+602;}
                    if(posY>spr_mainField.getY()+11+spr_Help[1].getHeight()){ posY=spr_mainField.getY()+11+spr_Help[1].getHeight();}
                    spr_Help[0].setPosition(spr_mainField.getX()+11,posY);
                    spr_Help[1].setPosition(spr_Help[0].getX(),spr_Help[0].getY()-spr_Help[1].getHeight());
                    break;
                default:break;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose () {
        myAssetsLoader.dispose();
        batch.dispose();
        shaderProgramDefault.dispose();
        shaderProgramInvert.dispose();
        shaderProgramNoise_1.dispose();
        shaderProgramNoise_2.dispose();
        shaderProgramRainbow.dispose();
        shaderProgramCrop.dispose();
        shaderProgramRGB2HSV.dispose();
        shaderProgramTest.dispose();
    }


}
