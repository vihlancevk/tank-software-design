package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;
import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.*;

public class TiledEntityControllerFactory implements EntityControllerFactory {
    private final TiledMap tiledMap;

    public TiledEntityControllerFactory(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    @Override
    public PlayerController createPlayerController(String texturePath, int x, int y, float speed, int maxHealth) {
        EntityParams entityParams = new EntityParams(texturePath, x, y);

        PlayerModel playerModel = new PlayerModel(entityParams.position, speed, maxHealth);
        Viewable<PlayerModel> viewable =
                new PlayerView(entityParams.texture, entityParams.graphics, entityParams.bounds);
        Viewable<PlayerModel> viewableHealthDecorator = new ViewableHealthDecorator(viewable);
        return new PlayerController(
                playerModel,
                viewableHealthDecorator,
                new TileMovement(GdxGameUtils.getSingleLayer(tiledMap), Interpolation.smooth)
        );
    }

    @Override
    public ObstacleController createObstacleController(String texturePath, int x, int y) {
        EntityParams entityParams = new EntityParams(texturePath, x, y);

        ObstacleModel obstacleModel = new ObstacleModel(entityParams.position);
        Viewable<ObstacleModel> viewable =
                new ObstacleView(entityParams.texture, entityParams.graphics, entityParams.bounds);
        Viewable<ObstacleModel> viewableBaseDecorator = new ViewableBaseDecorator<>(viewable);
        return new ObstacleController(obstacleModel, viewableBaseDecorator);
    }
}
