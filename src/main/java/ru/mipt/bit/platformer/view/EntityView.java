package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.model.EntityModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class EntityView<T extends EntityModel> implements Viewable<T> {
    protected final Texture texture;
    protected final TextureRegion graphics;

    public EntityView(Texture texture, TextureRegion graphics) {
        this.texture = texture;
        this.graphics = graphics;
    }

    @Override
    public void render(Batch batch, T entityModel) {
        drawTextureRegionUnscaled(batch, graphics, entityModel.getBounds(), 0f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
