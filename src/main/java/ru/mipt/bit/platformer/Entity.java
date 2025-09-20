package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class Entity {
    protected final Texture texture;

    protected final TextureRegion graphics;
    protected final Rectangle bounds;
    protected final GridPoint2 position;

    public Entity(Texture texture, TextureRegion graphics, Rectangle bounds, GridPoint2 position) {
        this.texture = texture;
        this.graphics = graphics;
        this.bounds = bounds;
        this.position = position;
    }

    public GridPoint2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, bounds, 0f);
    }

    public void dispose() {
        texture.dispose();
    }
}
