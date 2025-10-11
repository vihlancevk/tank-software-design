package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.api.EntityControllerFactory;
import ru.mipt.bit.platformer.controller.EntityController;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;
import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.ObstacleView;
import ru.mipt.bit.platformer.view.PlayerView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class TiledEntityControllerFactory implements EntityControllerFactory {
    private final TiledMap map;
    private final Map<String, Function<EntityParams, EntityController<?, ?>>> creators = new HashMap<>();

    public TiledEntityControllerFactory(TiledMap map) {
        this.map = map;
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

        return (T) creator.apply(
                EntityParams.getInstance(map, texturePath, x, y, speed)
        );
    }

    private void registerDefaultCreators() {
        creators.put("player", (entityControllerData) -> new PlayerController(
                new PlayerModel(
                        entityControllerData.bounds,
                        entityControllerData.position,
                        new TileMovement(getSingleLayer(map), Interpolation.smooth),
                        entityControllerData.speed
                ),
                new PlayerView(entityControllerData.texture, entityControllerData.graphics)
        ));

        creators.put("obstacle", (entityControllerData) -> new ObstacleController(
                new ObstacleModel(entityControllerData.bounds, entityControllerData.position),
                new ObstacleView(entityControllerData.texture, entityControllerData.graphics)
        ));
    }
}
