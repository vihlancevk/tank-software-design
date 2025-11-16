package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class EntityParams {
    public final Texture texture;
    public final TextureRegion graphics;
    public final Rectangle bounds;
    public final GridPoint2 position;
    public final float speed;

    public EntityParams(
            String texturePath,
            int x,
            int y,
            float speed
    ) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.bounds = GdxGameUtils.createBoundingRectangle(graphics);
        this.position = new GridPoint2(x, y);
        this.speed = speed;
    }
}
