package ru.mipt.bit.platformer.app;

import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {
    private final String filePath;
    private final EntityControllerFactory entityControllerFactory;

    private List<String> cachedLines;

    public FileLevelGenerator(String filePath, EntityControllerFactory entityControllerFactory) {
        this.filePath = filePath;
        this.entityControllerFactory = entityControllerFactory;
    }

    @Override
    public List<ObstacleController> generateObstacleControllers(String texturePath) {
        List<ObstacleController> obstacles = new ArrayList<>();

        List<String> lines = getCachedLevelLines();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'T') {
                    obstacles.add(
                            entityControllerFactory.createEntity(
                                    "obstacle", texturePath, x, lines.size() - 1 - y, 0
                            )
                    );
                }
            }
        }

        return obstacles;
    }

    @Override
    public PlayerController generatePlayerController(String texturePath, float speed) {
        List<String> lines = getCachedLevelLines();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'X') {
                    return entityControllerFactory.createEntity(
                            "player", texturePath, x, lines.size() - 1 - y, speed
                    );
                }
            }
        }

        throw new IllegalStateException("Player starting position (X) not found in level file: " + filePath);
    }

    private List<String> getCachedLevelLines() {
        if (cachedLines == null) {
            cachedLines = readLevelFile();
        }
        return cachedLines;
    }

    private List<String> readLevelFile() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Level file not found in resources: " + filePath);
            }

            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isBlank()) {
                        lines.add(line);
                    }
                }
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read level file: " + filePath, e);
        }
    }
}
