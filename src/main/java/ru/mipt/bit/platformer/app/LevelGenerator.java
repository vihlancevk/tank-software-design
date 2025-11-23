package ru.mipt.bit.platformer.app;

import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.List;

public interface LevelGenerator {
    void setEntityControllerFactory(EntityControllerFactory entityControllerFactory);

    PlayerController generatePlayerController(String texturePath);

    List<ObstacleController> generateObstacleControllers(String texturePath);

    List<PlayerController> generateBotControllers(String texturePath);
}
