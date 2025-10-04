package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.model.ObstacleModel;

public class ObstacleView extends EntityView<ObstacleModel> {
    public ObstacleView(Texture texture, TextureRegion graphics) {
        super(texture, graphics);
    }
}
