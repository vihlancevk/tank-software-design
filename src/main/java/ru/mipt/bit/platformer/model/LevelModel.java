package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LevelModel {
    private final int width;
    private final int height;

    private PlayerModel playerModel = null;
    private final Set<ObstacleModel> obstacleModels = new HashSet<>();
    private final Set<PlayerModel> botModels = new HashSet<>();
    private final Set<BulletModel> bulletModels = new HashSet<>();

    public LevelModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPlayerModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void unsetPlayerModel() {
        this.playerModel = null;
    }

    public void addObstacleModel(ObstacleModel obstacleModel) {
        obstacleModels.add(Objects.requireNonNull(obstacleModel, "Obstacle model must not be null"));
    }

    public void addBotModel(PlayerModel botModel) {
        botModels.add(Objects.requireNonNull(botModel, "Bot model must not be null"));
    }

    public void removeBotControllerModel(PlayerModel botModel) {
        botModels.remove(botModel);
    }

    public void addBulletModel(BulletModel bulletModel) {
        bulletModels.add(Objects.requireNonNull(bulletModel, "Bullet model must not be null"));
    }

    public void removeBulletModel(BulletModel bulletModel) {
        bulletModels.remove(bulletModel);
    }

    public boolean isAvailableForMove(GridPoint2 position) {
        return isInsideBounds(position)
                && (playerModel == null || !playerModel.getPosition().equals(position) && !playerModel.getDestination().equals(position))
                && (obstacleModels.stream().noneMatch(obstacleModel -> obstacleModel.getPosition().equals(position)))
                && (botModels.stream().noneMatch(botModel -> (botModel.getPosition().equals(position) || botModel.getDestination().equals(position))))
                ;
    }

    public boolean isInsideBounds(GridPoint2 position) {
        return position.x >= 0 && position.x < width
                && position.y >= 0 && position.y < height;
    }
}
