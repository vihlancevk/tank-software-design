package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player extends Entity {
    private final GridPoint2 destination;
    private final TileMovement movement;

    private float rotation;
    private float progress = 1f;
    private final float speed;

    public Player(Texture texture, TextureRegion graphics, Rectangle bounds, GridPoint2 startPos, TileMovement movement, float speed) {
        super(texture, graphics, bounds, new GridPoint2(startPos));

        this.destination = new GridPoint2(startPos);
        this.movement = movement;
        this.speed = speed;
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, bounds, rotation);
    }

    public void move(Direction dir, Level level) {
        if (!MathUtils.isEqual(progress, 1f)) {
            return;
        }

        GridPoint2 newDest = new GridPoint2(position.x + dir.dx, position.y + dir.dy);
        if (level.isFree(newDest)) {
            destination.set(newDest);
            progress = 0f;
        }
        rotation = dir.rotation;
    }

    public void update(float delta) {
        movement.moveRectangleBetweenTileCenters(bounds, position, destination, progress);
        progress = continueProgress(progress, delta, speed);

        if (MathUtils.isEqual(progress, 1f)) {
            position.set(destination);
        }
    }
}
