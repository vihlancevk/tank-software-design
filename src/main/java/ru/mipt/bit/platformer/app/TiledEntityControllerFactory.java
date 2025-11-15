package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.EntityController;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;
import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TiledEntityControllerFactory implements EntityControllerFactory {
    private final TiledMap tiledMap;
    private final Map<String, Function<EntityParams, EntityController<?, ?>>> creators = new HashMap<>();

    public TiledEntityControllerFactory(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        registerDefaultCreators();
    }

    public void register(String type, Function<EntityParams, EntityController<?, ?>> creator) {
        creators.put(type, creator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends  EntityController<?, ?>> T createEntity(
            String type,
            String texturePath,
            int x,
            int y,
            float speed
    ) {
        Function<EntityParams, EntityController<?, ?>> creator = creators.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }

        return (T) creator.apply(new EntityParams(texturePath, x, y, speed));
    }

    private void registerDefaultCreators() {
        creators.put(
                "player",
                (entityParams) -> {
                    PlayerModel playerModel = new PlayerModel(entityParams.position, entityParams.speed);
                    Viewable<PlayerModel> viewable =
                            new PlayerView(entityParams.texture, entityParams.graphics, entityParams.bounds);
                    Viewable<PlayerModel> viewableHealthDecorator = new ViewableHealthDecorator(viewable);
                    return new PlayerController(
                            playerModel,
                            viewableHealthDecorator,
                            new TileMovement(GdxGameUtils.getSingleLayer(tiledMap), Interpolation.smooth)
                    );
                }
        );

        creators.put(
                "obstacle",
                (entityParams) -> {
                    ObstacleModel obstacleModel = new ObstacleModel(entityParams.position);
                    Viewable<ObstacleModel> viewable =
                            new ObstacleView(entityParams.texture, entityParams.graphics, entityParams.bounds);
                    Viewable<ObstacleModel> viewableBaseDecorator = new ViewableBaseDecorator<>(viewable);
                    return new ObstacleController(obstacleModel, viewableBaseDecorator);
                }
        );
    }
}
