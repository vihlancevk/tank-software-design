package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.controller.LevelController;
import ru.mipt.bit.platformer.controller.LevelControllerFactory;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

@Component
public class GameDesktopLauncher implements ApplicationListener {
    private final LevelControllerFactory levelControllerFactory;
    private final LevelGenerator levelGenerator;

    private Batch batch;
    private LevelController levelController;

    @Autowired
    public GameDesktopLauncher(
            LevelControllerFactory levelControllerFactory,
            @Qualifier("randomLevelGenerator") LevelGenerator levelGenerator
    ) {
        this.levelControllerFactory = levelControllerFactory;
        this.levelGenerator = levelGenerator;
    }

    @Override
    public void create() {
        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        batch = new SpriteBatch();

        levelController = levelControllerFactory.create(tiledMap, batch);
        levelGenerator.setEntityControllerFactory(new TiledEntityControllerFactory(tiledMap));

        PlayerController playerController = levelGenerator.generatePlayerController("images/tank_blue.png");
        levelController.setPlayerController(playerController);

        List<ObstacleController> obstacleControllers =
                levelGenerator.generateObstacleControllers("images/green_tree.png");
        obstacleControllers.forEach(obstacleController -> levelController.addObstacleController(obstacleController));

        List<PlayerController> botControllers = levelGenerator.generateBotControllers("images/tank_blue.png");
        botControllers.forEach(botController -> levelController.addBotController(botController));
    }

    @Override
    public void render() {
        clearScreen();
        float delta = getTimePassedSinceLastRender();

        levelController.handleInput();
        levelController.update(delta);
        levelController.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    /**
     * Dispose of all the native resources
     * (classes which implement com.badlogic.gdx.utils.Disposable).
     */
    @Override
    public void dispose() {
        levelController.dispose();
        batch.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private float getTimePassedSinceLastRender() {
        return Gdx.graphics.getDeltaTime();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("ru.mipt.bit.platformer");

        GameDesktopLauncher gameDesktopLauncher = ctx.getBean(GameDesktopLauncher.class);
        LevelProperties levelProperties = ctx.getBean(LevelProperties.class);

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(
                levelProperties.getWidth() * levelProperties.getNPixelsInTile(),
                levelProperties.getHeight() * levelProperties.getNPixelsInTile()
        );

        new Lwjgl3Application(gameDesktopLauncher, config);
    }
}
