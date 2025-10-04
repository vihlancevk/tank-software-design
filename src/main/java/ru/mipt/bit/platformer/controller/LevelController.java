package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.model.LevelModel;
import ru.mipt.bit.platformer.view.LevelView;

import java.util.List;

public class LevelController {
    private final LevelModel levelModel;
    private final LevelView levelView;

    private LevelController(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
    }

    public static LevelController getInstance(
            List<ObstacleController> obstacleControllers,
            TiledMap map,
            MapRenderer renderer
    ) {
        LevelModel levelModel = new LevelModel();
        obstacleControllers.forEach(obstacleController ->
            levelModel.addObstacleModel(obstacleController.getEntityModel())
        );

        LevelView levelView = new LevelView(map, renderer);

        return new LevelController(levelModel, levelView);
    }

    public LevelModel getLevelModel() {
        return levelModel;
    }

    public void render() {
        levelView.render();
    }

    public void dispose() {
        levelView.dispose();
    }
}
