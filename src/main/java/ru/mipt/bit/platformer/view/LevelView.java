package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class LevelView {
    private final TiledMap map;
    private final MapRenderer renderer;

    public LevelView(TiledMap map, MapRenderer renderer) {
        this.map = map;
        this.renderer = renderer;
    }

    public void render() {
        renderer.render();
    }

    public void dispose() {
        map.dispose();
    }
}
