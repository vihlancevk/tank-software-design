package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.app.InputHandler;
import ru.mipt.bit.platformer.model.LevelModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.view.LevelView;

@Component
public class LevelControllerFactory {
    private final ApplicationContext ctx;
    private final int width;
    private final int height;
    private final InputHandler inputHandler;

    @Autowired
    public LevelControllerFactory(
            ApplicationContext ctx,
            @Value("${level.width}") int width,
            @Value("${level.height}") int height,
            InputHandler inputHandler
    ) {
        this.ctx = ctx;
        this.width = width;
        this.height = height;
        this.inputHandler = inputHandler;
    }

    public LevelController create(TiledMap tiledMap, Batch batch) {
        LevelModel model = new LevelModel(width, height);
        LevelView view = new LevelView(tiledMap, GdxGameUtils.createSingleLayerMapRenderer(tiledMap, batch));

        return ctx.getBean(LevelController.class, batch, inputHandler, model, view);
    }
}
