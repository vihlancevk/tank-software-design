package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class TextureFactory {
    public static Texture getWhitePixel() {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture whitePixel = new Texture(pixmap);
        pixmap.dispose();
        return whitePixel;
    }
}
