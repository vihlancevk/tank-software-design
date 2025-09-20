package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle extends Entity {
    public Obstacle(Texture texture, TextureRegion graphics, Rectangle bounds, GridPoint2 position) {
        super(texture, graphics, bounds, position);
    }
}
