package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public abstract class EntityModel {
    protected final GridPoint2 position;

    public EntityModel(GridPoint2 position) {
        this.position = new GridPoint2(position);
    }

    public GridPoint2 getPosition() {
        return position;
    }
}
