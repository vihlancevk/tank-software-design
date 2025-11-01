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
    private final int botCount;
    private final EntityControllerFactory factory;

    private final List<ObstacleController> obstacles = new ArrayList<>();
    private final List<PlayerController> bots = new ArrayList<>();
    private final Set<String> occupiedPositions = new HashSet<>();

    public RandomLevelGenerator(int width, int height, EntityControllerFactory factory) {
        this.width = width;
        this.height = height;
        this.factory = factory;

        this.obstacleCount = (int) (0.3 * width * height);
        this.botCount = (int) (0.1 * width * height);
    }

    @Override
    public List<ObstacleController> generateObstacleControllers(String texturePath) {
        for (int i = 0; i < obstacleCount; i++) {
            GridPosition pos = generateUniquePosition();
            ObstacleController obstacle =
                    factory.createEntity("obstacle", texturePath, pos.x, pos.y, 0);
            obstacles.add(obstacle);
            occupiedPositions.add(pos.key());
        }
        return List.copyOf(obstacles);
    }

    @Override
    public List<PlayerController> generateBotControllers(String texturePath, float speed) {
        for (int i = 0; i < botCount; i++) {
            GridPosition pos = generateUniquePosition();
            PlayerController bot =
                    factory.createEntity("player", texturePath, pos.x, pos.y, speed);
            bots.add(bot);
            occupiedPositions.add(pos.key());
        }
        return List.copyOf(bots);
    }

    @Override
    public PlayerController generatePlayerController(String texturePath, float speed) {
        GridPosition pos = generateUniquePosition();
        return factory.createEntity("player", texturePath, pos.x, pos.y, speed);
    }

    private GridPosition generateUniquePosition() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x, y;
        String key;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
            key = x + "," + y;
        } while (!occupiedPositions.add(key));
        return new GridPosition(x, y);
    }

    private static class GridPosition {
        private final int x;
        private final int y;

        public GridPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String key() {
            return x + "," + y;
        }
    }
}
