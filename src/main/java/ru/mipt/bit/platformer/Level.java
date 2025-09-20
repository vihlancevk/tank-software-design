package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final TiledMap map;
    private final MapRenderer renderer;
    private final List<Obstacle> obstacles = new ArrayList<>();

    public Level(TiledMap map, MapRenderer renderer) {
        this.map = map;
        this.renderer = renderer;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public boolean isFree(GridPoint2 pos) {
        return obstacles.stream().noneMatch(o -> o.getPosition().equals(pos));
    }

    public void render(Batch batch) {
        renderer.render();
        batch.begin();
        for (Obstacle o : obstacles) {
            o.render(batch);
        }
        batch.end();
    }

    public void dispose() {
        map.dispose();
        obstacles.forEach(Entity::dispose);
    }
}
