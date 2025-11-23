package ru.mipt.bit.platformer.api;

import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

public interface EntityControllerFactory {
    PlayerController createPlayerController(String texturePath, int x, int y, float speed, int maxHealth);

    ObstacleController createObstacleController(String texturePath, int x, int y);
}
