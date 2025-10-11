package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class EntityParams {
    public final Texture texture;
    public final TextureRegion graphics;
    public final Rectangle bounds;
    public final GridPoint2 position;
    public final float speed;

    private EntityParams(
            Texture texture,
            TextureRegion graphics,
            Rectangle bounds,
            GridPoint2 position,
            float speed
    ) {
        this.texture = texture;
        this.graphics = graphics;
        this.bounds = bounds;
        this.position = position;
        this.speed = speed;
    }

    public static EntityParams getInstance(
            TiledMap map,
            String texturePath,
            int x,
            int y,
            float speed
    ) {
        Texture texture = new Texture(texturePath);
        TextureRegion graphics = new TextureRegion(texture);
        Rectangle bounds = createBoundingRectangle(graphics);
        GridPoint2 position = new GridPoint2(x, y);
        moveRectangleAtTileCenter(getSingleLayer(map), bounds, position);
        return new EntityParams(texture, graphics, bounds, position, speed);
    }
}
