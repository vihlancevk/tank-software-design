package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerModel extends EntityModel {
    private final GridPoint2 destination;
    private final TileMovement movement;
    private final float speed;

    private float rotation;
    private float progress;

    public PlayerModel(
            Rectangle bounds,
            GridPoint2 position,
            TileMovement movement,
            float speed
    ) {
        super(bounds, position);
        this.destination = new GridPoint2(position);
        this.movement = movement;
        this.speed = speed;
        this.rotation = 0f;
        this.progress = 1f;
    }

    public GridPoint2 getDestination() {
        return destination;
    }

    public float getRotation() {
        return rotation;
    }

    public float getProgress() {
        return progress;
    }

    public void move(GameWorld gameWorld, Direction direction) {
        if (!MathUtils.isEqual(progress, 1f)) {
            return;
        }

        GridPoint2 newDest = new GridPoint2(
                position.x + direction.dx, position.y + direction.dy
        );
        if (gameWorld.isFree(newDest.x, newDest.y)) {
            destination.set(newDest);
            progress = 0f;
        }
        rotation = direction.rotation;
    }

    public void update(float delta) {
        movement.moveRectangleBetweenTileCenters(bounds, position, destination, progress);

        progress = continueProgress(progress, delta, speed);

        if (MathUtils.isEqual(progress, 1f)) {
            position.set(destination);
        }
    }
}
