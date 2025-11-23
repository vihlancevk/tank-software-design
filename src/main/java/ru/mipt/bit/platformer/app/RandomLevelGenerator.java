package ru.mipt.bit.platformer.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomLevelGenerator implements LevelGenerator {
    private final LevelProperties levelProperties;
    private final int obstacleCount;
    private final int botCount;

    private EntityControllerFactory entityControllerFactory;

    private final List<ObstacleController> obstacles = new ArrayList<>();
    private final List<PlayerController> bots = new ArrayList<>();
    private final Set<String> occupiedPositions = new HashSet<>();

    @Autowired
    public RandomLevelGenerator(LevelProperties levelProperties) {
        this.levelProperties = levelProperties;
        this.obstacleCount = (int) (0.15 * levelProperties.getWidth() * levelProperties.getHeight());
        this.botCount = (int) (0.05 * levelProperties.getWidth() * levelProperties.getHeight());
    }

    @Override
    public void setEntityControllerFactory(EntityControllerFactory entityControllerFactory) {
        this.entityControllerFactory = entityControllerFactory;
    }

    @Override
    public PlayerController generatePlayerController(String texturePath) {
        GridPosition pos = generateUniquePosition();
        return entityControllerFactory.createPlayerController(
                texturePath, pos.x, pos.y, levelProperties.getPlayerMovementSpeed(), levelProperties.getPlayerMaxHealth()
        );
    }

    @Override
    public List<ObstacleController> generateObstacleControllers(String texturePath) {
        for (int i = 0; i < obstacleCount; i++) {
            GridPosition pos = generateUniquePosition();
            ObstacleController obstacle = entityControllerFactory.createObstacleController(
                    texturePath, pos.x, pos.y
            );
            obstacles.add(obstacle);
            occupiedPositions.add(pos.key());
        }
        return List.copyOf(obstacles);
    }

    @Override
    public List<PlayerController> generateBotControllers(String texturePath) {
        for (int i = 0; i < botCount; i++) {
            GridPosition pos = generateUniquePosition();
            PlayerController bot = entityControllerFactory.createPlayerController(
                    texturePath, pos.x, pos.y, levelProperties.getBotMovementSpeed(), levelProperties.getBotMaxHealth()
            );
            bots.add(bot);
            occupiedPositions.add(pos.key());
        }
        return List.copyOf(bots);
    }

    private GridPosition generateUniquePosition() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x, y;
        String key;
        do {
            x = random.nextInt(levelProperties.getWidth());
            y = random.nextInt(levelProperties.getHeight());
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
