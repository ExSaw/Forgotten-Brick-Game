package com.rickrip.game0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Letter{

    private float posX=0.0f, posY=0.0f, alpha=1.0f;
    private char char_letter=' ';
    Sprite sprite;

    public Letter(){
    }
    public void setChar_letter(char char_letter){
        this.char_letter = char_letter;
    }
    public void setSprite_letter(Sprite sprite){
        this.sprite = sprite;
        this.sprite.setOrigin(0,0);
    }
    public void setPosition(float posX,float posY){this.posX=posX; this.posY=posY;}
    public float getPosX(){return posX;}
    public float getPosY(){return posY;}
    public void setColor(float r,float g, float b){this.sprite.setColor(r,g,b,this.sprite.getColor().a);}
    public void setOrRegenAlpha(float float_alpha_timer, boolean bool_isProfile){
        if(!bool_isProfile){
            if(this.alpha<1.0f&&float_alpha_timer<40.0f){
                this.alpha+=Gdx.graphics.getDeltaTime();
                if(this.alpha>1.0f){this.alpha=1.0f;}
            }
            else{
                if(float_alpha_timer>40.0f) {this.alpha=0.0f;}
            }
        }else{this.alpha=1.0f;}
        this.sprite.setAlpha(this.alpha);
    }
    public void draw(Batch batch){
        sprite.setPosition(posX,posY);
        sprite.draw(batch);}

}
