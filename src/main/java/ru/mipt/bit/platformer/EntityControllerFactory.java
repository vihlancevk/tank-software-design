package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;
import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.ObstacleView;
import ru.mipt.bit.platformer.view.PlayerView;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class EntityControllerFactory {
    private final TiledMap map;

    public EntityControllerFactory(TiledMap map) {
        this.map = map;
    }

    public ObstacleController createObstacleController(String texturePath, int x, int y) {
        EntityControllerData entityControllerData = EntityControllerData.getInstance(
                map, texturePath, x, y
        );

        ObstacleModel obstacleModel = new ObstacleModel(
                entityControllerData.bounds, entityControllerData.position
        );
        ObstacleView obstacleView = new ObstacleView(
                entityControllerData.texture, entityControllerData.graphics
        );

        return new ObstacleController(obstacleModel, obstacleView);
    }

    public PlayerController createPlayerController(
            String texturePath,
            int x,
            int y,
            float speed
    ) {
        EntityControllerData entityControllerData = EntityControllerData.getInstance(
                map, texturePath, x, y
        );

        PlayerModel playerModel = new PlayerModel(
                entityControllerData.bounds,
                entityControllerData.position,
                new TileMovement(getSingleLayer(map), Interpolation.smooth),
                speed
        );
        PlayerView playerView = new PlayerView(
                entityControllerData.texture, entityControllerData.graphics
        );

        return new PlayerController(playerModel, playerView);
    }

    private static class EntityControllerData {
        public final Texture texture;
        public final TextureRegion graphics;
        public final Rectangle bounds;
        public final GridPoint2 position;

        private EntityControllerData(Texture texture, TextureRegion graphics, Rectangle bounds, GridPoint2 position) {
            this.texture = texture;
            this.graphics = graphics;
            this.bounds = bounds;
            this.position = position;
        }

        public static EntityControllerData getInstance(
                TiledMap map,
                String texturePath,
                int x,
                int y
        ) {
            Texture texture = new Texture(texturePath);
            TextureRegion graphics = new TextureRegion(texture);
            Rectangle bounds = createBoundingRectangle(graphics);
            GridPoint2 position = new GridPoint2(x, y);
            moveRectangleAtTileCenter(getSingleLayer(map), bounds, position);
            return new EntityControllerData(texture, graphics, bounds, position);
        }
    }
}
