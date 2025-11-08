package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.util.TextureFactory;

public class ViewableHealthDecorator extends ViewableBaseDecorator<PlayerModel> {
    private static final float PERCENTAGE_OF_HEIGHT = 0.05f;

    private final Texture whitePixel;

    public ViewableHealthDecorator(Viewable<PlayerModel> viewable) {
        super(viewable);
        this.whitePixel = TextureFactory.getWhitePixel();
    }

    @Override
    public void render(Batch batch, PlayerModel playerModel) {
        super.render(batch, playerModel);
        if (viewable.isHealthVisible()) {
            renderHealthBar(batch, playerModel);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        disposeHealthBar();
    }

    private void renderHealthBar(Batch batch, PlayerModel playerModel) {
        float ratio = 1.0f * playerModel.getCurHealth() / playerModel.getMaxHealth();

        Rectangle bounds = playerModel.getBounds();
        float barWidth = bounds.width;
        float barHeight = PERCENTAGE_OF_HEIGHT * bounds.height;
        float x = bounds.x;
        float y = bounds.y + bounds.height + barHeight;

        batch.setColor(Color.DARK_GRAY);
        batch.draw(whitePixel, x, y, barWidth, barHeight);

        if (ratio > 0) {
            batch.setColor(Color.GREEN);
            batch.draw(whitePixel, x, y, barWidth * ratio, barHeight);
        }

        batch.setColor(Color.WHITE);
    }

    private void disposeHealthBar() {
        whitePixel.dispose();
    }
}
