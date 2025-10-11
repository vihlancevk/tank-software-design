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

    @Override
    public boolean isFree(int x, int y) {
        return levelModel.isFree(new GridPoint2(x, y));
    }

    public void render() {
        levelView.render();
    }

    public void dispose() {
        levelView.dispose();
    }
}
