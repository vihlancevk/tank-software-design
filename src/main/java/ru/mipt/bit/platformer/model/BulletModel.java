package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class BulletModel extends EntityModel {
    private final Direction direction;
    private final float speed;
    private final int damage;

    private GridPoint2 destination;
    private float movementProgress = 0.0f;
    private boolean skipProcessingOnStart = true;

    public BulletModel(GridPoint2 position, Direction direction, float speed, int damage) {
        super(position);

        this.direction = direction;
        this.speed = speed;
        this.damage = damage;

        this.destination = new GridPoint2(position.x + direction.dx, position.y + direction.dy);
    }

    public GridPoint2 getDestination() {
        return destination;
    }

    public int getDamage() {
        return damage;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public boolean isSkipProcessingOnStart() {
        return skipProcessingOnStart;
    }

    public void update(float delta) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, delta, speed);

        if (MathUtils.isEqual(movementProgress, 1.0f)) {
            position.set(destination);
            destination.set(position.x + direction.dx, position.y + direction.dy);
            movementProgress = 0.0f;

            skipProcessingOnStart = false;
        }
    }
}
