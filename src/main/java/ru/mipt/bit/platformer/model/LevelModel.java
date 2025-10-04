package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class LevelModel {
    private final List<ObstacleModel> obstacleModels;

    public LevelModel() {
        obstacleModels = new ArrayList<>();
    }

    public void addObstacleModel(ObstacleModel obstacleModel) {
        obstacleModels.add(obstacleModel);
    }

    public boolean isFree(GridPoint2 position) {
        return obstacleModels.stream()
                .noneMatch(
                        obstacleModel -> obstacleModel.getPosition().equals(position)
                );
    }
}
