package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.ObstacleModel;

public class ObstacleView extends EntityView<ObstacleModel> {
    public ObstacleView(Texture texture, TextureRegion graphics, Rectangle bounds) {
        super(texture, graphics, bounds);
    }
}
