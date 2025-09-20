package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class EntityFactory {
    private final TiledMap map;

    public EntityFactory(TiledMap map) {
        this.map = map;
    }

    public Obstacle createObstacle(String texturePath, int x, int y) {
        EntityGraphics g = createGraphics(texturePath, x, y);
        return new Obstacle(g.texture, g.region, g.rectangle, g.position);
    }

    public Player createPlayer(String texturePath, int x, int y, float speed) {
        EntityGraphics g = createGraphics(texturePath, x, y);
        TileMovement movement = new TileMovement(getSingleLayer(map), Interpolation.smooth);
        return new Player(g.texture, g.region, g.rectangle, g.position, movement, speed);
    }

    private EntityGraphics createGraphics(String texturePath, int x, int y) {
        Texture texture = new Texture(texturePath);
        TextureRegion region = new TextureRegion(texture);
        Rectangle rectangle = createBoundingRectangle(region);
        GridPoint2 pos = new GridPoint2(x, y);
        moveRectangleAtTileCenter(getSingleLayer(map), rectangle, pos);
        return new EntityGraphics(texture, region, rectangle, pos);
    }

    private static class EntityGraphics {
        public final Texture texture;
        public final TextureRegion region;
        public final Rectangle rectangle;
        public final GridPoint2 position;

        public EntityGraphics(Texture texture, TextureRegion region, Rectangle rectangle, GridPoint2 position) {
            this.texture = texture;
            this.region = region;
            this.rectangle = rectangle;
            this.position = position;
        }
    }
}
