package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.util.TextureFactory;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class BulletView extends EntityView<BulletModel> {
    public BulletView(Texture texture, TextureRegion graphics, Rectangle bounds) {
        super(texture, graphics, bounds);
    }

    public static BulletView create() {
        Texture texture = TextureFactory.getBulletTexture();
        TextureRegion graphics = new TextureRegion(texture);
        Rectangle bounds = createBoundingRectangle(graphics);
        return new BulletView(texture, graphics, bounds);
    }
}
