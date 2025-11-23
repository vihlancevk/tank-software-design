package ru.mipt.bit.platformer.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LevelProperties {
    private final int width;
    private final int height;
    private final int nPixelsInTile;
    private final float playerMovementSpeed;
    private final float botMovementSpeed;
    private final int playerMaxHealth;
    private final int botMaxHealth;
    private final String filePath;

    @Autowired
    public LevelProperties(
            @Value("${level.width}") int width,
            @Value("${level.height}") int height,
            @Value("${level.n_pixels_in_tile}") int nPixelsInTile,
            @Value("${level.player_movement_speed}") float playerMovementSpeed,
            @Value("${level.bot_movement_speed}") float botMovementSpeed,
            @Value("${level.player_max_health}") int playerMaxHealth,
            @Value("${level.bot_max_health}") int botMaxHealth,
            @Value("${level.file_path:level.txt}") String filePath
    ) {
        this.width = width;
        this.height = height;
        this.nPixelsInTile = nPixelsInTile;
        this.playerMovementSpeed = playerMovementSpeed;
        this.botMovementSpeed = botMovementSpeed;
        this.playerMaxHealth = playerMaxHealth;
        this.botMaxHealth = botMaxHealth;
        this.filePath = filePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNPixelsInTile() {
        return nPixelsInTile;
    }

    public float getPlayerMovementSpeed() {
        return playerMovementSpeed;
    }

    public float getBotMovementSpeed() {
        return botMovementSpeed;
    }

    public int getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public int getBotMaxHealth() {
        return botMaxHealth;
    }

    public String getFilePath() {
        return filePath;
    }
}
