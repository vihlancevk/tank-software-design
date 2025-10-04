package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class EntityModel {
    protected final Rectangle bounds;
    protected final GridPoint2 position;

    public EntityModel(Rectangle bounds, GridPoint2 position) {
        this.bounds = bounds;
        this.position = new GridPoint2(position);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public GridPoint2 getPosition() {
        return position;
    }
}
