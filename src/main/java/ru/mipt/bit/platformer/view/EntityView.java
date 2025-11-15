package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.EntityModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class EntityView<T extends EntityModel> implements Viewable<T> {
    protected final Texture texture;
    protected final TextureRegion graphics;
    protected final Rectangle bounds;

    public EntityView(Texture texture, TextureRegion graphics, Rectangle bounds) {
        this.texture = texture;
        this.graphics = graphics;
        this.bounds = bounds;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void render(Batch batch, T entityModel) {
        drawTextureRegionUnscaled(batch, graphics, bounds, 0);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
