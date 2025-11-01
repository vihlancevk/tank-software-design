package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class EntityModel {
    protected final Rectangle bounds;
    protected final GridPoint2 position;
    protected final GridPoint2 destination;
    protected float speed;

    public EntityModel(Rectangle bounds, GridPoint2 position, GridPoint2 destination, float speed) {
        this.bounds = bounds;
        this.position = new GridPoint2(position);
        this.destination = new GridPoint2(destination);
        this.speed = speed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public GridPoint2 getPosition() {
        return position;
    }

    public GridPoint2 getDestination() {
        return destination;
    }

    public float getSpeed() {
        return speed;
    }
}
