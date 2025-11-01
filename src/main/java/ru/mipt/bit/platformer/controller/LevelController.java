package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.model.LevelModel;
import ru.mipt.bit.platformer.view.LevelView;

import java.util.List;

public class LevelController implements GameWorld {
    private final LevelModel levelModel;
    private final LevelView levelView;

    private LevelController(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
    }

    public static LevelController create(
            int width,
            int height,
            List<ObstacleController> obstacles,
            List<PlayerController> bots,
            PlayerController player,
            TiledMap map,
            MapRenderer renderer
    ) {
        LevelModel model = new LevelModel(width, height);

        obstacles.forEach(o -> model.addEntityModel(o.getEntityModel()));
        bots.forEach(b -> model.addEntityModel(b.getEntityModel()));
        model.addEntityModel(player.getEntityModel());

        LevelView view = new LevelView(map, renderer);
        return new LevelController(model, view);
    }

    @Override
    public boolean isAvailable(int x, int y) {
        return levelModel.isAvailable(new GridPoint2(x, y));
    }

    public void render() {
        levelView.render();
    }

    public void dispose() {
        levelView.dispose();
    }
}
