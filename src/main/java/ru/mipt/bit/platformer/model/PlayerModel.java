package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.concurrent.ThreadLocalRandom;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerModel extends EntityModel {
    private final TileMovement movement;

    private float rotation;
    private float progress;
    private int health;

    public PlayerModel(
            Rectangle bounds,
            GridPoint2 position,
            float speed,
            TileMovement movement
    ) {
        super(bounds, position, position, speed);
        this.movement = movement;
        this.rotation = 0.0f;
        this.progress = 1.0f;
        this.health = ThreadLocalRandom.current().nextInt(80, 100 + 1);
    }

    public float getRotation() {
        return rotation;
    }

    public float getProgress() {
        return progress;
    }

    public int getHealth() {
        return health;
    }

    public void move(GameWorld gameWorld, Direction direction) {
        if (!MathUtils.isEqual(progress, 1f)) {
            return;
        }

        GridPoint2 newDest = new GridPoint2(
                position.x + direction.dx, position.y + direction.dy
        );
        if (gameWorld.isAvailable(newDest.x, newDest.y)) {
            destination.set(newDest);
            progress = 0.0f;
        }
        rotation = direction.rotation;
    }

    public void update(float delta) {
        movement.moveRectangleBetweenTileCenters(bounds, position, destination, progress);

        progress = continueProgress(progress, delta, speed);

        if (MathUtils.isEqual(progress, 1.0f)) {
            position.set(destination);
        }
    }
}
