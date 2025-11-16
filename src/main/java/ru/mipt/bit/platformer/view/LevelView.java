package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class LevelView {
    private final TiledMap tiledMap;
    private final MapRenderer mapRenderer;

    public LevelView(TiledMap tiledMap, MapRenderer renderer) {
        this.tiledMap = tiledMap;
        this.mapRenderer = renderer;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void render() {
        mapRenderer.render();
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
