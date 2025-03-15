package com.rickrip.game0;
import com.badlogic.gdx.graphics.Color;

public class ColorFromInt {

    public Color rgb565ToColor(Color color, int value) {
        color.r = ((value & 0x0000F800) >>> 11) / 31f;
        color.g = ((value & 0x000007E0) >>> 5) / 63f;
        color.b = ((value & 0x0000001F) >>> 0) / 31f;
        return color;
    }

    public Color rgba4444ToColor(Color color, int value) {
        color.r = ((value & 0x0000f000) >>> 12) / 15f;
        color.g = ((value & 0x00000f00) >>> 8) / 15f;
        color.b = ((value & 0x000000f0) >>> 4) / 15f;
        color.a = ((value & 0x0000000f)) / 15f;
        return color;
    }

    public Color rgb888ToColor(Color color, int value) {
        color.r = ((value & 0x00ff0000) >>> 16) / 255f;
        color.g = ((value & 0x0000ff00) >>> 8) / 255f;
        color.b = ((value & 0x000000ff)) / 255f;
        return color;
    }

    public Color rgba8888ToColor(Color color, int value) {
        color.r = ((value & 0xff000000) >>> 24) / 255f;
        color.g = ((value & 0x00ff0000) >>> 16) / 255f;
        color.b = ((value & 0x0000ff00) >>> 8) / 255f;
        color.a = ((value & 0x000000ff)) / 255f;
        return color;
    }
    public Color rgba8888ToColor2(Color color, int value) {
        color.r = (value & 0xff000000) >>> 24;
        color.g = (value & 0x00ff0000) >>> 16;
        color.b = (value & 0x0000ff00) >>> 8;
        color.a = (value & 0x000000ff);
        return color;
    }
}
