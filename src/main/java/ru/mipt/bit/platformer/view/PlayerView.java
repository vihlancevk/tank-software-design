package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.model.PlayerModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class PlayerView extends EntityView<PlayerModel> {
    public PlayerView(Texture texture, TextureRegion graphics) {
        super(texture, graphics);
    }

    @Override
    public void render(Batch batch, PlayerModel playerModel) {
        drawTextureRegionUnscaled(
                batch,
                graphics,
                playerModel.getBounds(),
                playerModel.getRotation()
        );
    }
}
