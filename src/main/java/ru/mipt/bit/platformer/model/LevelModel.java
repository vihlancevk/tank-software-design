package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LevelModel {
    private final int width;
    private final int height;
    private final List<EntityModel> entityModels = new ArrayList<>();

    public LevelModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addEntityModel(EntityModel entityModel) {
        entityModels.add(Objects.requireNonNull(entityModel, "Entity model must not be null"));
    }

    public boolean isAvailable(GridPoint2 position) {
        return isInsideBounds(position) && isTailFree(position);
    }

    private boolean isInsideBounds(GridPoint2 position) {
        return position.x >= 0 && position.x < width
                && position.y >= 0 && position.y < height;
    }

    private boolean isTailFree(GridPoint2 position) {
        return entityModels.stream()
                .noneMatch(
                        entity -> entity.getPosition().equals(position)
                                || entity.getDestination().equals(position)
                );
    }
}
