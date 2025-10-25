package ru.mipt.bit.platformer.app;

import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLevelGenerator implements LevelGenerator {
    private final int width;
    private final int height;
    private final int obstacleCount;
    private final EntityControllerFactory entityControllerFactory;
    private final List<ObstacleController> obstacleControllers;

    public RandomLevelGenerator(int width, int height, EntityControllerFactory entityControllerFactory) {
        this.width = width;
        this.height = height;
        this.obstacleCount = (int) (0.3 * width * height);
        this.entityControllerFactory = entityControllerFactory;
        this.obstacleControllers = new ArrayList<>();
    }

    @Override
    public List<ObstacleController> generateObstacleControllers(String texturePath) {
        Set<String> occupiedPositions = new HashSet<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < obstacleCount; i++) {
            int x, y;
            do {
                x = random.nextInt(0, width);
                y = random.nextInt(0, height);
            } while (!occupiedPositions.add(x + "," + y));

            obstacleControllers.add(entityControllerFactory.createEntity("obstacle", texturePath, x, y, 0));
        }

        return new ArrayList<>(obstacleControllers);
    }

    @Override
    public PlayerController generatePlayerController(String texturePath, float speed) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        Set<String> occupied = new HashSet<>();
        for (ObstacleController obstacle : obstacleControllers) {
            int ox = obstacle.getEntityModel().getPosition().x;
            int oy = obstacle.getEntityModel().getPosition().y;
            occupied.add(ox + "," + oy);
        }

        int x, y;
        do {
            x = random.nextInt(0, width);
            y = random.nextInt(0, height);
        } while (occupied.contains(x + "," + y));

        return entityControllerFactory.createEntity("player", texturePath, x, y, speed);
    }
}
