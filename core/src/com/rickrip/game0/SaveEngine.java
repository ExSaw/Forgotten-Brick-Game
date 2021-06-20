package com.rickrip.game0;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import static com.badlogic.gdx.Application.ApplicationType.Android;

public class SaveEngine {
    Json json;
    SaveData saveData;
    String str_localRoot = "";
    boolean bool_isLocAvailable = false;
    boolean bool_saveFileExists = false;
    FileHandle fileHandle;
    public static String pathPrefix;

    public SaveEngine(){
        if (Gdx.app.getType() == Application.ApplicationType.Desktop){pathPrefix="android/assets/";}//remove this path, when compiling final desktop version
        if (Gdx.app.getType() == Android){pathPrefix="";}
        bool_isLocAvailable = Gdx.files.isLocalStorageAvailable();
        Gdx.app.log("'","local storage is available = "+bool_isLocAvailable);
        if(bool_isLocAvailable){
            str_localRoot = Gdx.files.getLocalStoragePath();
            Gdx.app.log("'","str_localRoot="+str_localRoot);
            if(Gdx.app.getType() == Application.ApplicationType.Desktop){
                fileHandle = Gdx.files.classpath("C:"+"\\"+"Users"+"\\"+"FREQUENCY"+"\\"+"myGameSaveData_1.json");
                bool_saveFileExists = Gdx.files.classpath("C:"+"\\"+"Users"+"\\"+"FREQUENCY"+"\\"+"myGameSaveData_1.json").exists();
                Gdx.app.log("'","local save file exists = " + bool_saveFileExists);
                if(!bool_saveFileExists){
                    fileHandle = Gdx.files.classpath("C:"+"\\"+"Users"+"\\"+"FREQUENCY"+"\\"+"myGameSaveData_1.json");
                    fileHandle.write(false);
                }
            }
            if(Gdx.app.getType() == Application.ApplicationType.Android){
                bool_saveFileExists = Gdx.files.internal(str_localRoot+"myGameSaveData_1.json").exists();
                Gdx.app.log("'","local save file exists = " + bool_saveFileExists);
                fileHandle = Gdx.files.classpath(str_localRoot);
            }
        }else{/*Have to say what to do if local storage isn't available!!!*/}
        json = new Json();
        saveData = new SaveData();
    }

    public void Save(int int_profileNumber, int int_highScore, int int_currentScore, int int_comboTimesMultiplier,
                     int int_GAME_STATE, int int_GAME_STATE_BF, int int_GAME_STATE_BONUSTYPE,
                     int int_GAME_STATE_COLLIDE, int int_GAME_STATE_PDF,boolean bool_highScoreBeaten,
                     boolean bool_ghostEnabled, String str_nextBrickState, String str_fallingBrickState,
                     String str_mainFieldState){
        saveData.int_profileNumber = int_profileNumber;
        saveData.int_highScore = int_highScore;
        saveData.int_currentScore = int_currentScore;
        saveData.int_comboTimesMultiplier = int_comboTimesMultiplier;
        saveData.GAME_STATE = int_GAME_STATE;
        saveData.GAME_STATE_BF = int_GAME_STATE_BF;
        saveData.GAME_STATE_BONUSTYPE = int_GAME_STATE_BONUSTYPE;
        saveData.GAME_STATE_COLLIDE = int_GAME_STATE_COLLIDE;
        saveData.GAME_STATE_PDF = int_GAME_STATE_PDF;
        saveData.bool_highScoreBeaten = bool_highScoreBeaten;
        saveData.bool_ghostEnabled = bool_ghostEnabled;
        saveData.nextBrickState = str_nextBrickState;
        saveData.fallingBrickState = str_fallingBrickState;
        saveData.mainFieldState = str_mainFieldState;

        json.toJson(saveData);
        Gdx.app.log("'",json.prettyPrint(saveData));
    }

}
