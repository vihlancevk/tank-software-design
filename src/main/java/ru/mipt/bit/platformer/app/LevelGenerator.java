package ru.mipt.bit.platformer.app;

import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.List;

public interface LevelGenerator {
    List<ObstacleController> generateObstacleControllers(String texturePath);

    PlayerController generatePlayerController(String texturePath, float speed);
}
