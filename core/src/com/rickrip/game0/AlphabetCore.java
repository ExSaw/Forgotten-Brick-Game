package com.rickrip.game0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AlphabetCore {

    Texture tex_ABC;
    TextureRegion[] texRegion_ABC;
    Sprite[] spr_ABC;
    int int_charW=21, int_charH=18, int_numOfCharsInABC=0, int_maxChars=8,int_maxLength=21,int_stringID=-1,
        int_curFirstChar=0, int_tick=0, int_language=0;
    float f_speed=1.1f;
    boolean bool_isProfileMode=false;
    String str_curString;
    Letter[] letter;

    public AlphabetCore(@org.jetbrains.annotations.NotNull AssetManager assetManager){

        tex_ABC = assetManager.get("AlphabetAtlas_ENG_RUS.png");
        tex_ABC.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        int_numOfCharsInABC = (tex_ABC.getWidth()*2)/int_charW;
        texRegion_ABC = new TextureRegion[int_numOfCharsInABC];
        spr_ABC = new Sprite[int_numOfCharsInABC];
        for(int j=0,n=0;j<tex_ABC.getHeight();j+=int_charH){
            for(int i=0;i<tex_ABC.getWidth();i+=int_charW){
                texRegion_ABC[n] = new TextureRegion(tex_ABC,i,j,int_charW,int_charH);
                spr_ABC[n] = new Sprite(texRegion_ABC[n],0,0, texRegion_ABC[n].getRegionWidth(), texRegion_ABC[n].getRegionHeight());
                spr_ABC[n].setOrigin(0, 0);//очень важно
                n++;}}
        int_maxLength = int_maxChars*int_charW;
        str_curString=setString(int_stringID,0);
        str_curString=str_curString.toUpperCase();
    }
    private String setString(int int_stringID, int int_language){
        String string;
        if(int_language==0){//eng
            switch (int_stringID){

                case -1:string="SOUNDS ARE MUTED!!!";break;
                case 0:string="MUSIC:«FROZEN STAR» BY -KEVIN MACLEOD-";break;
                case 1:string="MUSIC:FROM THE GAME «INSIDIA» BY -WOBLYWARE STUDIO-";break;
                case 2:string="MUSIC:FROM THE GAME «EPSILON» BY -EON- FROM THE NEWGROUNDS SITE";break;
                default:string="I WANT TO BE AN ANDROID DEVELOPER";break;
            }
        }
        else{
            switch (int_stringID){

                case 0:string="МУЗЫКА:«FROZEN STAR BY KEVIN MACLEOD";break;
                case 1:string="МУЗЫКА:ИЗ ИГРЫ «INSIDIA» РАЗРАБОТЧИК -WOBLYWARE STUDIO-";break;
                case 2:string="МУЗЫКА:ИЗ ИГРЫ «EPSILON» РАЗРАБОТЧИК -EON- С САЙТА NEWGROUNDS";break;
                default:string="Я ХОЧУ БЫТЬ ANDROID РАЗРАБОТЧИКОМ";break;
            }
        }
        return string;
    }
    private String setProfileString(int int_stringID,int int_language){
        String string;
        if(int_language==0){
            switch (int_stringID){

                case 0:string="PROFILE #1";break;
                case 1:string="PROFILE #2";break;
                case 2:string="PROFILE #3";break;
                default:string="HELLO WORLD!";break;
            }
        }
        else{
            switch (int_stringID){

                case -2:string="АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЧШЩЭЮЯ()!?':«» ABCDEFGHIJKLMNOPQRSTUVWXYZ-0123456789#";break;//TEST
                case 0:string="ПРОФИЛЬ #1";break;
                case 1:string="ПРОФИЛЬ #2";break;
                case 2:string="ПРОФИЛЬ #3";break;
                default:string="ПРИВЕТСТВУЮ!";break;
            }
        }
        return string;
    }
    private int getCharID(char curChar){
        int int_ID;
        switch (curChar){
            //eng
            case 'A':int_ID=1;break;
            case 'B':int_ID=2;break;
            case 'C':int_ID=3;break;
            case 'D':int_ID=4;break;
            case 'E':int_ID=5;break;
            case 'F':int_ID=6;break;
            case 'G':int_ID=7;break;
            case 'H':int_ID=8;break;
            case 'I':int_ID=9;break;
            case 'J':int_ID=10;break;
            case 'K':int_ID=11;break;
            case 'L':int_ID=12;break;
            case 'M':int_ID=13;break;
            case 'N':int_ID=14;break;
            case 'O':int_ID=15;break;
            case 'P':int_ID=16;break;
            case 'Q':int_ID=17;break;
            case 'R':int_ID=18;break;
            case 'S':int_ID=19;break;
            case 'T':int_ID=20;break;
            case 'V':int_ID=21;break;
            case 'U':int_ID=22;break;
            case 'W':int_ID=23;break;
            case 'X':int_ID=24;break;
            case 'Y':int_ID=25;break;
            case 'Z':int_ID=26;break;
            case '-':int_ID=27;break;
            case '0':int_ID=28;break;
            case '1':int_ID=29;break;
            case '2':int_ID=30;break;
            case '3':int_ID=31;break;
            case '4':int_ID=32;break;
            case '5':int_ID=33;break;
            case '6':int_ID=34;break;
            case '7':int_ID=35;break;
            case '8':int_ID=36;break;
            case '9':int_ID=37;break;
            case '#':int_ID=38;break;
            //rus
            case 'А':int_ID=39;break;
            case 'Б':int_ID=40;break;
            case 'В':int_ID=41;break;
            case 'Г':int_ID=42;break;
            case 'Д':int_ID=43;break;
            case 'Е':int_ID=44;break;
            case 'Ё':int_ID=44;break;
            case 'Ж':int_ID=45;break;
            case 'З':int_ID=46;break;
            case 'И':int_ID=47;break;
            case 'Й':int_ID=48;break;
            case 'К':int_ID=49;break;
            case 'Л':int_ID=50;break;
            case 'М':int_ID=51;break;
            case 'Н':int_ID=52;break;
            case 'О':int_ID=53;break;
            case 'П':int_ID=54;break;
            case 'Р':int_ID=55;break;
            case 'С':int_ID=56;break;
            case 'Т':int_ID=57;break;
            case 'У':int_ID=58;break;
            case 'Ф':int_ID=59;break;
            case 'Х':int_ID=60;break;
            case 'Ч':int_ID=61;break;
            case 'Ш':int_ID=62;break;
            case 'Щ':int_ID=63;break;
            case 'Ъ':int_ID=64;break;
            case 'Ы':int_ID=65;break;
            case 'Ь':int_ID=66;break;
            case 'Э':int_ID=67;break;
            case 'Ю':int_ID=68;break;
            case 'Я':int_ID=69;break;
            case '(':int_ID=70;break;
            case ')':int_ID=71;break;
            case '!':int_ID=72;break;
            case '?':int_ID=73;break;
            case '\'':int_ID=74;break;
            case ':':int_ID=75;break;
            case '«':int_ID=76;break;// Alt+0171
            case '»':int_ID=77;break;// Alt+0187
            default:int_ID=0;break;// SPACE
        }
        return int_ID;
    }
    private void convertStringToLettersObjects(int int_posX, int int_posY, float r, float g, float b){

        letter = new Letter[str_curString.length()];
        for(int i=0,id=0;i<str_curString.length();i++){
            id=getCharID(str_curString.charAt(i));
            letter[i] = new Letter();
            letter[i].setChar_letter(str_curString.charAt(i));
            letter[i].setSprite_letter(spr_ABC[id]);
            letter[i].setPosition(int_posX+=int_charW,int_posY);
            if(bool_isProfileMode&&(id==29||id==30||id==31||id==38)){letter[i].setColor(1.0f,1.0f,1.0f);}// #1 ,#2 or #3 of profile
            else{letter[i].setColor(r,g,b);}
        }
    }

    public void draw(SpriteBatch batch, int int_posX, int int_posY, int int_stringID,float float_alpha_timer,boolean bool_isProfileMode,int int_language){

        if(bool_isProfileMode){
            if (this.int_language!=int_language||this.int_stringID != int_stringID||this.bool_isProfileMode!=bool_isProfileMode) {
                this.bool_isProfileMode = bool_isProfileMode;
                this.int_stringID = int_stringID;
                this.int_language=int_language;
                str_curString = setProfileString(int_stringID,int_language);
                str_curString = str_curString.toUpperCase();
                convertStringToLettersObjects(int_posX, int_posY,0.0f,1.0f,0.7f);
                f_speed=2.0f;
            }
        }else
            {
                if (this.int_language!=int_language||this.int_stringID != int_stringID||this.bool_isProfileMode!=bool_isProfileMode) {
                    this.bool_isProfileMode = bool_isProfileMode;
                    this.int_stringID = int_stringID;
                    this.int_language=int_language;
                    str_curString = setString(int_stringID,int_language);
                    str_curString = str_curString.toUpperCase();
                    if(int_stringID!=-1){convertStringToLettersObjects(int_posX, int_posY,0.0f,1.0f,0.0f);}
                    else{convertStringToLettersObjects(int_posX, int_posY,1.0f,1.0f,1.0f);}
                    f_speed=1.1f;
            }
        }

            for(int i=0;i<str_curString.length();i++){
                if(letter[i].getPosX()>(int_posX-int_maxLength)){
                    letter[i].setOrRegenAlpha(float_alpha_timer,bool_isProfileMode);
                    if(float_alpha_timer<40||bool_isProfileMode){
                        letter[i].setPosition(letter[i].getPosX()-Gdx.graphics.getDeltaTime()-f_speed,letter[i].getPosY());
                        letter[i].draw(batch);
                    }
                }
                else{letter[i].setPosition((int_posX+str_curString.length()*int_charW)-((int_maxChars-3)*int_charW),int_posY);}
            }



    }
}
